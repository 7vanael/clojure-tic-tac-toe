(ns tic-tac-toe.persistence.postgresql
  (:require [tic-tac-toe.core :as core]
            [clojure.edn :as edn]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def db-spec
  {:dbtype "postgresql"
   :dbname "ttt"
   :host   "localhost"})

(def data-source (jdbc/get-datasource db-spec))

(defn initialize [& [source]]
  (let [ds (or source data-source)]
    (jdbc/execute! ds
                   ["CREATE TABLE IF NOT EXISTS tictactoe (
        game_id SERIAL PRIMARY KEY,
        board TEXT NOT NULL,
        active_player_index INTEGER NOT NULL,
        status VARCHAR(50) NOT NULL,
        x_type VARCHAR(50) NOT NULL,
        x_difficulty VARCHAR(50),
        o_type VARCHAR(50) NOT NULL,
        o_difficulty VARCHAR(50)
      )"])))

(defn parse-possible-nil [var]
  (if (= "nil" var)
    nil
    (keyword var)))

(defn parse-game [sql-state]
  {:board               (edn/read-string (:board sql-state))
   :active-player-index (:active_player_index sql-state)
   :status              (keyword (:status sql-state))
   :players             [{:character  "X" :play-type (parse-possible-nil (:x_type sql-state))
                          :difficulty (parse-possible-nil (:x_difficulty sql-state))}
                         {:character  "O" :play-type (parse-possible-nil (:o_type sql-state))
                          :difficulty (parse-possible-nil (:o_difficulty sql-state))}]
   :save                :sql
   :game-id             (:game_id sql-state)})

(defn update-save [ds sql-state game-id]
  (sql/update! ds :tictactoe sql-state {:game_id game-id})
  {:game_id game-id})

(defn first-save [ds sql-state]
  (sql/insert! ds :tictactoe sql-state
               {:return-keys true :builder-fn rs/as-unqualified-maps}))

(defn keyword-or-nil [setting]
  (if (keyword? setting) (name setting) setting))

(defmethod core/save-game :sql [{:keys [board active-player-index status players game-id] :as state} & [source]]
  (let [ds           (or source data-source)
        x-type       (or (get-in players [0 :play-type]) "nil")
        o-type       (or (get-in players [1 :play-type]) "nil")
        x-difficulty (or (get-in players [0 :difficulty]) "nil")
        o-difficulty (or (get-in players [1 :difficulty]) "nil")
        string-board (or (pr-str board) "nil")
        sql-state    {:board               (or string-board "nil")
                      :active_player_index active-player-index
                      :status              (keyword-or-nil status)
                      :x_type              (keyword-or-nil x-type)
                      :x_difficulty        (keyword-or-nil x-difficulty)
                      :o_type              (keyword-or-nil o-type)
                      :o_difficulty        (keyword-or-nil o-difficulty)}
        result       (if game-id
                       (update-save ds sql-state game-id)
                       (first-save ds sql-state))]
    (assoc state :game-id (:game_id result))))

(defn load-existing [state ds]
  (let [game-id (:game-id state)
        result  (sql/query ds ["SELECT * FROM tictactoe WHERE game_id = ?" game-id]
                           {:builder-fn rs/as-unqualified-maps})]
    (if-not (seq result) nil (parse-game (first result)))))

(defn load-last-in-progress [ds]
  (let [result (sql/query ds ["SELECT * FROM tictactoe WHERE (status = 'in-progress' OR status = 'found-save') ORDER BY game_id DESC LIMIT 1"]
                          {:builder-fn rs/as-unqualified-maps})]
    (if-not (seq result) nil (assoc (parse-game (first result)) :status :found-save))))

(defn new-saved-starting-state [{:keys [interface]} ds]
  (core/save-game (core/initial-state {:interface interface :save :sql}) ds))

(defmethod core/load-game :sql [state & [source]]
  (when-not source (initialize))
  (let [ds (or source data-source)]
    (if-let [loaded-game (if (:game-id state)
                           (load-existing state ds)
                           (load-last-in-progress ds))]
      (assoc loaded-game :interface (:interface state))
      (new-saved-starting-state state ds))))

(defmethod core/delete-save :sql [_ & [source]]
  (let [ds        (or source data-source)
        last-game (sql/query ds ["SELECT game_id FROM tictactoe ORDER BY game_id DESC LIMIT 1"]
                             {:builder-fn rs/as-unqualified-maps})
        game-id   (delay (:game_id (first last-game)))]
    (when (seq last-game)
      (sql/delete! ds :tictactoe {:game_id @game-id}))))

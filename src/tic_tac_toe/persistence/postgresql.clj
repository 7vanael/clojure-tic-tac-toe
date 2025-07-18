;(ns tic-tac-toe.persistence.postgresql
;  (:require [tic-tac-toe.core :as core]
;            [clojure.edn :as edn]
;            [next.jdbc :as jdbc]
;            [next.jdbc.result-set :as rs]
;            [next.jdbc.sql :as sql]))
;
;(def db-spec
;  {:dbtype "postgresql"
;   :dbname "ttt"
;   :host   "localhost"})
;
;(def data-source (jdbc/get-datasource db-spec))
;
;(defn initialize [& [source]]
;  (let [ds (or source data-source)]
;    (jdbc/execute! ds
;                   ["CREATE TABLE IF NOT EXISTS tictactoe (
;        game_id SERIAL PRIMARY KEY,
;        board TEXT NOT NULL,
;        active_player_index INTEGER NOT NULL,
;        status VARCHAR(50) NOT NULL,
;        x_type VARCHAR(50) NOT NULL,
;        x_difficulty VARCHAR(50),
;        o_type VARCHAR(50) NOT NULL,
;        o_difficulty VARCHAR(50),
;        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
;      )"])))
;
;(defn parse-difficulty [difficulty]
;  (if (= "nil" difficulty)
;    nil
;    (keyword difficulty)))
;
;(defn parse-game [sql-state]
;  {:board               (edn/read-string (:board sql-state))
;   :active-player-index (:active_player_index sql-state)
;   :status              (keyword (:status sql-state))
;   :players             [{:character  "X" :play-type (keyword (:x_type sql-state))
;                          :difficulty (parse-difficulty (:x_difficulty sql-state))}
;                         {:character  "O" :play-type (keyword (:o_type sql-state))
;                          :difficulty (parse-difficulty (:o_difficulty sql-state))}]
;   :save                :sql})
;
;(defn update-save [ds sql-state game-id]
;  (sql/update! ds :tictactoe sql-state {:game_id game-id})
;  {:game_id game-id})
;
;(defn first-save [ds sql-state]
;  (sql/insert! ds :tictactoe sql-state
;               {:return-keys true :builder-fn rs/as-unqualified-maps}))
;
;(defmethod core/save-game :sql [{:keys [board active-player-index status players game-id] :as state} & [source]]
;  (let [ds           (or source data-source)
;        x-type       (get-in players [0 :play-type])
;        o-type       (get-in players [1 :play-type])
;        x-difficulty (or (get-in players [0 :difficulty]) "nil")
;        o-difficulty (or (get-in players [1 :difficulty]) "nil")
;        string-board (pr-str board)
;        sql-state    {:board               string-board
;                      :active_player_index active-player-index
;                      :status              (name status)
;                      :x_type              (name x-type)
;                      :x_difficulty        (if (keyword? x-difficulty) (name x-difficulty) x-difficulty)
;                      :o_type              (name o-type)
;                      :o_difficulty        (if (keyword? o-difficulty) (name o-difficulty) o-difficulty)}
;        result       (if game-id
;                       (update-save ds sql-state game-id)
;                       (first-save ds sql-state))]
;    (assoc state :game-id (:game_id result))))
;
;(defmethod core/load-game :sql [state & [source]]
;  (initialize)
;  (let [ds         (or source data-source)
;        result     (sql/query ds ["SELECT * FROM tictactoe ORDER BY game_id DESC LIMIT 1"]
;                              {:builder-fn rs/as-unqualified-maps})
;        loaded-game (delay (parse-game (first result)))]
;    (if-not (seq result)
;      (core/initial-state state)
;      (assoc @loaded-game :status :found-save :interface (:interface state)))))
;
;(defmethod core/delete-save :sql [_ & [source]]
;  (let [ds        (or source data-source)
;        last-game (sql/query ds ["SELECT game_id FROM tictactoe ORDER BY game_id DESC LIMIT 1"]
;                             {:builder-fn rs/as-unqualified-maps})
;        game-id   (delay (:game_id (first last-game)))]
;    (when (seq last-game)
;      (sql/delete! ds :tictactoe {:game_id @game-id}))))
;
;#_(defn core/get-all-games [state]
;  #_(return a map of all saved games))
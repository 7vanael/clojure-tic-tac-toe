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

(defn parse-game [sql-state]
  {:board               (edn/read-string (:board sql-state))
   :active-player-index (:active_player_index sql-state)
   :status              (keyword (:status sql-state))
   :players             [{:character "X" :play-type (keyword (:x_type sql-state)) :difficulty (keyword (:x_difficulty sql-state))}
                         {:character "O" :play-type (keyword (:o_type sql-state)) :difficulty (keyword (:o_difficulty sql-state))}]
   :save                :sql})

(defmethod core/save-game :sql [{:keys [board active-player-index status players] :as state} & [source]]
  (let [ds (or source data-source)
        x-type (get-in players [0 :play-type])
        o-type (get-in players [1 :play-type])
        x-difficulty (or (get-in players [0 :difficulty]) "nil")
        o-difficulty (or (get-in players [1 :difficulty]) "nil")
        string-board (pr-str board)
        result (sql/insert! ds :state {:board               string-board
                                       :active_player_index active-player-index
                                       :status              (name status)
                                       :x_type              (name x-type)
                                       :x_difficulty        (if (keyword? x-difficulty) (name x-difficulty) x-difficulty)
                                       :o_type              (name o-type)
                                       :o_difficulty        (if (keyword? o-difficulty) (name o-difficulty) x-difficulty)}
                            {:return-keys true :builder-fn rs/as-unqualified-maps})]
    (assoc state :game-id (:game_id result))))



(defmethod core/load-game :sql [_ & [source]]
  (let [ds (or source data-source)
        result (sql/query ds ["SELECT * FROM state ORDER BY game_id DESC LIMIT 1"]
                          {:builder-fn rs/as-unqualified-maps})
        saved-game (delay (parse-game (first result)))]
    (when (seq result)
      @saved-game)))

(defmethod core/delete-save :sql [_ & [source]]
  (let [ds (or source data-source)
        last-game (sql/query ds ["SELECT game_id FROM state ORDER BY game_id DESC LIMIT 1"]
                             {:builder-fn rs/as-unqualified-maps})
        game-id (delay (:game_id (first last-game)))]
    (when (seq last-game)
      (sql/delete! ds :state {:game_id @game-id}))))
(ns tic-tac-toe.persistence.postgresql
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def db-spec
  {:dbtype "postgresql"
   :dbname "ttt"
   :host   "localhost"})

(def data-source (jdbc/get-datasource db-spec))

(defn save-game [{:keys [board active-player-index status players]} & [source]]
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
                            {:return-keys true})]
    result))

#_(defn load-game)

#_(defn delete-save [])                                     ;May not need to implement this one?
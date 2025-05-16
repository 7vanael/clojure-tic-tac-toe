(ns tic-tac-toe.persistence.postgresql
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(def db-spec
  {:dbtype "postgresql"
   :dbname "ttt"
   :host "localhost"})

(def datasource (jdbc/get-datasource db-spec))

#_(defn save-game [state])

#_(defn load-game)

#_(defn delete-save[]);May not need to implement this one?
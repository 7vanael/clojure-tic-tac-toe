(ns tic-tac-toe.persistence.postgresql-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.persistence.postgresql :as sut]
            [next.jdbc :as jdbc]))


(def ttt_test_spec
  {:dbtype "postgresql"
   :dbname "ttt_test"
   :host   "localhost"})

(def test-datasource (jdbc/get-datasource ttt_test_spec))

#_(defn test-connection []
    (jdbc/execute! datasource ["SELECT 1"]))

(describe "postgresql persistence"
  (with-stubs)
  (redefs-around [db/datasource test-datasource])
  #_(before-all setup_test_db)

  #_(after-all test-datasource ["DROP TABLE IF EXISTS state"])

  (it "saves a state to the database"
      )

  )
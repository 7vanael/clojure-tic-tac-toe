(ns tic-tac-toe.persistence.postgresql-spec
  (:require [next.jdbc.sql :as sql]
            [speclj.core :refer :all]
            [tic-tac-toe.persistence.postgresql :as sut]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [tic-tac-toe.core-spec :as test-core]))


(def ttt_test_spec
  {:dbtype "postgresql"
   :dbname "ttt_test"
   :host   "localhost"})

(def test-datasource (jdbc/get-datasource ttt_test_spec))

(defn set-up []
  (jdbc/execute! test-datasource ["DROP TABLE IF EXISTS state"])
  (jdbc/execute! test-datasource ["
  CREATE TABLE IF NOT EXISTS state (
  game_id             SERIAL PRIMARY KEY,
  board               varchar,
  active_player_index int,
  status              varchar,
  x_type              varchar,
  x_difficulty        varchar DEFAULT 'nil',
  o_type              varchar,
  o_difficulty        varchar DEFAULT 'nil'\n)
  "]))

(def state1-SQL {:board (pr-str test-core/center-x-board)
                 :active_player_index 1
                 :status "in-progress"
                 :x_type "human"
                 :x_difficulty nil
                 :o_type "computer"
                 :o_difficulty "hard"})

(def state2-SQL {:board (pr-str test-core/center-x-corner-xo-board)
                 :active_player_index 1
                 :status "in-progress"
                 :x_type "human"
                 :x_difficulty nil
                 :o_type "human"
                 :o_difficulty nil})

(def state2 (test-core/state-create {:board               test-core/first-X-4-board
                                     :interface           :gui
                                     :active-player-index 1 :status :in-progress
                                     :x-type              :human
                                     :o-type              :computer
                                     :o-difficulty        :hard}))

(describe "postgresql persistence"
  (with-stubs)
  (before (set-up))

  (it "saves a state to the database"
      (let [result (sut/save-game state2 test-datasource)]
        (should= 1 (:game_id result))))

  (it "loads the last state from the database"
      (let [state2 (test-core/state-create {:board test-core/center-x-corner-xo-board :active-player-index 1
                                            :status :in-progress :x-type :human :o-type :human})]

        (sql/insert! test-datasource :state state2-SQL)
        (should= state2 (sut/load-game test-datasource))))

  (it "deletes a saved game from the database"
      (sql/insert! test-datasource :state state2-SQL)
      (let [games-in-db (first (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps}))
            number-deleted (sut/delete-save test-datasource)
            remaining-games (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps})]
        (should= games-in-db (assoc state2-SQL :game_id 1))
        (should= 1 (:next.jdbc/update-count number-deleted))
        (should= nil (seq remaining-games))))
  )
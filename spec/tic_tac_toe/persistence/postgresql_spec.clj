(ns tic-tac-toe.persistence.postgresql-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.persistence.postgresql :as sut]
            [next.jdbc :as jdbc]
            [tic-tac-toe.core-spec :as test-core]))


(def ttt_test_spec
  {:dbtype "postgresql"
   :dbname "ttt_test"
   :host   "localhost"})

(def test-datasource (jdbc/get-datasource ttt_test_spec))

(defn set-up []
  (jdbc/execute! test-datasource ["DROP TABLE IF EXISTS state"])
  (jdbc/execute! test-datasource ["
  CREATE TABLE state (
  game_id             SERIAL PRIMARY KEY,
  board               varchar,
  active_player_index int,
  status              varchar,
  x_type              varchar,
  x_difficulty        varchar DEFAULT 'nil',
  o_type              varchar,
  o_difficulty        varchar DEFAULT 'nil'\n)
  "]))


(describe "postgresql persistence"
  (with-stubs)
  (before-all (set-up))

  (it "saves a state to the database"
      (let [result (sut/save-game (test-core/state-create {:board               test-core/first-X-4-board
                                                           :interface :gui
                                                           :active-player-index 1 :status :in-progress
                                                           :x-type              :human
                                                           :o-type              :computer
                                                           :o-difficulty        :hard})
                                  test-datasource)]
        (should= 1 (:state/game_id result))))

  )
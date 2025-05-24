(ns tic-tac-toe.persistence.postgresql-spec
  (:require [next.jdbc.sql :as sql]
            [speclj.core :refer :all]
            [tic-tac-toe.persistence.postgresql :as sut]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [tic-tac-toe.core :as core]
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

(def state2-SQL {:board               (pr-str test-core/center-x-corner-xo-board)
                 :active_player_index 1
                 :status              "in-progress"
                 :x_type              "human"
                 :x_difficulty        nil
                 :o_type              "human"
                 :o_difficulty        nil})

(def state2 (test-core/state-create {:board  test-core/center-x-corner-xo-board :active-player-index 1
                                     :status :in-progress :x-type :human :o-type :human}))

(def state-SQL {:board               (pr-str test-core/first-X-4-board)
                :active_player_index 1
                :status              "in-progress"
                :x_type              "human"
                :x_difficulty        nil
                :o_type              "computer"
                :o_difficulty        "hard"})

(def state (test-core/state-create {:board               test-core/first-X-4-board
                                    :interface           :gui
                                    :active-player-index 1
                                    :status              :in-progress
                                    :x-type              :human
                                    :o-type              :computer
                                    :o-difficulty        :hard}))

(def state-first-turn (test-core/state-create {:board  test-core/empty-board :interface :tui :active-player-index 0
                                               :x-type :human :o-type :computer :o-difficulty :easy}))

(describe "postgresql persistence"
  (with-stubs)
  (before (set-up))

  (it "returns nil if no save is found"
    (should= nil (core/load-game state test-datasource)))

  (it "creates the state table if it does not exist"
    (jdbc/execute! test-datasource ["DROP TABLE IF EXISTS state"])
    (should-throw (core/load-game state test-datasource))
    (sut/initialize test-datasource)
    (should-not-throw (core/load-game state test-datasource)))

  (it "saves a state to the database"
    (let [result (core/save-game state test-datasource)]
      (should= 1 (:game-id result))))

  (it "updates the database if the game is already saved in it"
    (let [starting-stored   (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps})
          first-saved-state (core/save-game state-first-turn test-datasource)
          game-id           (:game-id first-saved-state)
          next-turn-state   (assoc first-saved-state :board test-core/center-x-board :active-player-index 1)
          ending-state      (core/save-game next-turn-state test-datasource)
          ending-game-id    (:game-id ending-state)
          ending-stored     (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps})]
      (should= game-id ending-game-id)
      (should= 1 (- (count ending-stored) (count starting-stored)))
      (should= 0 (:active-player-index first-saved-state))
      (should= 1 (:active-player-index ending-state))))

  (it "deletes a saved game from the database, no errors if database is empty"
    (sql/insert! test-datasource :state state2-SQL)
    (let [games-in-db     (first (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps}))
          number-deleted  (core/delete-save state test-datasource)
          remaining-games (sql/query test-datasource ["SELECT * FROM state"] {:builder-fn rs/as-unqualified-maps})]
      (should= games-in-db (assoc state2-SQL :game_id 1))
      (should= 1 (:next.jdbc/update-count number-deleted))
      (should= nil (seq remaining-games))
      (should-not-throw (core/delete-save state test-datasource))))

  (it "loads the last state from the database"
    (sql/insert! test-datasource :state state-SQL)
    (sql/insert! test-datasource :state state2-SQL)
    (should= state2 (core/load-game state test-datasource)))

  (it "returns a saved state that matches the loaded state"
    (let [saved-state (core/save-game state2 test-datasource)
          loaded-state (core/load-game {:save :sql} test-datasource)]
      (should= (:active-player-index saved-state) (:active-player-index loaded-state))
      (should= (:board saved-state) (:board loaded-state))
      (should= (:players saved-state) (:players loaded-state))
      ))
  )
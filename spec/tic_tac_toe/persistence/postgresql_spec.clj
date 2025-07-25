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
  (jdbc/execute! test-datasource ["DROP TABLE IF EXISTS tictactoe"])
  (jdbc/execute! test-datasource ["
  CREATE TABLE IF NOT EXISTS tictactoe (
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
                                     :status :in-progress :x-type :human :o-type :human :save :sql}))

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
                                    :o-difficulty        :hard
                                    :save                :sql}))

(def state-first-turn (test-core/state-create {:board  test-core/empty-board :interface :tui :active-player-index 0
                                               :x-type :human :o-type :computer :o-difficulty :easy :save :sql}))

(describe "postgresql persistence"
  (with-stubs)
  (before (set-up))

  (it "creates the state table if it does not exist"
    (jdbc/execute! test-datasource ["DROP TABLE IF EXISTS tictactoe CASCADE"])
    (should-throw (core/load-game state test-datasource))
    (sut/initialize test-datasource)
    (should-not-throw (core/load-game state test-datasource)))

  (context "saving a game"
    (it "saves a state to the database"
      (let [result (core/save-game state test-datasource)]
        (should= 1 (:game-id result))))

    (it "updates the database if the game is already saved; one game has one row despite multiple saves"
      (let [starting-stored   (sql/query test-datasource ["SELECT * FROM tictactoe"] {:builder-fn rs/as-unqualified-maps})
            first-saved-state (core/save-game state-first-turn test-datasource)
            game-id           (:game-id first-saved-state)
            next-turn-state   (assoc first-saved-state :board test-core/center-x-board :active-player-index 1)
            ending-state      (core/save-game next-turn-state test-datasource)
            ending-game-id    (:game-id ending-state)
            ending-stored     (sql/query test-datasource ["SELECT * FROM tictactoe"] {:builder-fn rs/as-unqualified-maps})]
        (should= game-id ending-game-id)
        (should= 1 (- (count ending-stored) (count starting-stored)))
        (should= 0 (:active-player-index first-saved-state))
        (should= 1 (:active-player-index ending-state))))

    (it "returns initial state with a new game-id if no save is found"
      (let [result   (core/load-game state test-datasource)
            expected (core/initial-state {:interface (:interface state) :save :sql :game-id 1})]
        (should-not-be-nil (:game-id result))
        (should= (:board expected) (:board result))
        (should= (:active-player-index expected) (:active-player-index result))))
    )

  (context "delete"
    (it "deletes a saved game from the database, no errors if database is empty"
      (sql/insert! test-datasource :tictactoe state2-SQL)
      (let [games-in-db     (first (sql/query test-datasource ["SELECT * FROM tictactoe"] {:builder-fn rs/as-unqualified-maps}))
            number-deleted  (core/delete-save state test-datasource)
            remaining-games (sql/query test-datasource ["SELECT * FROM tictactoe"] {:builder-fn rs/as-unqualified-maps})]
        (should= games-in-db (assoc state2-SQL :game_id 1))
        (should= 1 (:next.jdbc/update-count number-deleted))
        (should= nil (seq remaining-games))
        (should-not-throw (core/delete-save state test-datasource))))
    )

  (context "Loading- no game-id and no save present"
    (it "creates new game when no in-progress games exist and no game-id provided"
      (core/save-game (assoc (core/fresh-start state) :status :config-o-type :x-type :human) test-datasource)
      (core/save-game (assoc state :status :game-over) test-datasource)
      (let [loaded-state (core/load-game state test-datasource)]
        (should-not-be-nil (:game-id loaded-state))
        (should= :welcome (:status loaded-state))))

    (it "returns fresh start when trying to load a non-existent game-id"
      (let [state-with-fake-id (assoc state :game-id 99999)
            loaded-state       (core/load-game state-with-fake-id test-datasource)]
        (should= (core/initial-state {:interface :gui :save :sql}) (dissoc loaded-state :game-id))))

    )

  (context "Loading- no game-id provided, but prior saves exist"
    (it "Returns a saved state that matches the loaded state"
      (let [saved-state  (core/save-game state2 test-datasource)
            loaded-state (core/load-game {:save :sql} test-datasource)]
        (should= (:active-player-index saved-state) (:active-player-index loaded-state))
        (should= (:board saved-state) (:board loaded-state))
        (should= (:players saved-state) (:players loaded-state))
        (should= (:game-id saved-state) (:game-id loaded-state))))

    (it "loads the last state from the database and sets status to :found-save, interface to starting-states interface"
      (sql/insert! test-datasource :tictactoe state-SQL)
      (sql/insert! test-datasource :tictactoe state2-SQL)
      (let [expected (assoc state2 :status :found-save :interface :gui :game-id 2)]
        (should= expected (core/load-game state test-datasource))))

    (it "loads the most recent in-progress game when no game-id is provided - returns found-save status"
      (let [first-saved-state       (core/save-game state2 test-datasource)
            saved-in-progress-state (core/save-game state test-datasource)
            passed-in-state         {:interface :gui :save :sql}
            loaded-state            (core/load-game passed-in-state test-datasource)]
        (should= :found-save (:status loaded-state))
        (should= (:game-id saved-in-progress-state) (:game-id loaded-state))
        (should= (:board state) (:board loaded-state))
        (should-not-be-same (:board first-saved-state) (:board loaded-state))))

    (it "ignores completed games when looking for last in-progress game "
      (core/save-game (assoc state2 :status :game-over) test-datasource)
      (let [latest-in-progress (core/save-game state test-datasource)
            loaded-state       (core/load-game {:interface :gui :save :sql} test-datasource)]
        (should= (:game-id latest-in-progress) (:game-id loaded-state))
        (should= (:board state) (:board loaded-state))
        (should= :found-save (:status loaded-state))))
    )

  (context "Loading- game-id provided"
    (it "loads a specific game by game-id when game-id is provided in state- maintains status of saved game"
      (let [saved-state  (core/save-game (assoc (core/fresh-start {:interface :tui :save :sql})
                                           :status :config-o-type :x-type :human) test-datasource)
            game-id      (:game-id saved-state)
            loaded-state (core/load-game {:save :sql :game-id game-id} test-datasource)]
        (should= (:status saved-state) (:status loaded-state))
        (should= game-id (:game-id loaded-state))))

    )
  )
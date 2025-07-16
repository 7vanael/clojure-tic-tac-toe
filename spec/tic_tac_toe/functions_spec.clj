(ns tic-tac-toe.functions-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.functions :as sut]))

(def state-winner (helper/state-create {:x-type :human :o-type :human :save :mock :status :winner
                                        :board [["X" "O" "X"]
                                                ["O" "X" "O"]
                                                ["O" "X" "X"]] :active-player-index 0}))

(def state-tie (helper/state-create {:x-type :human :o-type :human :save :mock :status :tie
                                     :board [["X" "O" "X"]
                                             ["X" "O" "O"]
                                             ["O" "X" "X"]] :active-player-index 0}))

(def state-in-progress (helper/state-create {:x-type :human :o-type :human :save :mock :status :in-progress
                                             :board [["X" "O" "X"]
                                                     ["O" "X" "O"]
                                                     ["O" "X" 9]] :active-player-index 1}))

(describe "functions"
  (with-stubs)
  (before (reset! spec-helper/mock-db nil))

  (it "Welcome; no loaded save goes to config-x"
    (let [state  {:interface :tui :save :mock :status :welcome}
          result (sut/maybe-load-save state)]
      (should= :config-x-type (:status result))))

  (it "Welcome; loaded save goes to found-save"
    (let [_      (core/save-game state-in-progress)
          state  {:interface :tui :save :mock :status :welcome}
          result (sut/maybe-load-save state)]
      (should= :found-save (:status result))
      (should= [["X" "O" "X"]
                ["O" "X" "O"]
                ["O" "X" 9]] (:board result))))

  (it "Found-save; if value is true, resume (in-progress)"
    (let [state  (assoc state-in-progress :status :found-save)
          result (sut/maybe-resume-save state true)]
      (should= :in-progress (:status result))
      (should= [["X" "O" "X"]
                ["O" "X" "O"]
                ["O" "X" 9]] (:board result))))

  (it "Found-save; if value is false, start-fresh"
    (let [state  (assoc state-in-progress :status :found-save)
          result (sut/maybe-resume-save state false)]
      (should= :config-x-type (:status result))
      (should= nil (:board result))))

  (it "Config-x-type; if play-type is human,  config-o-type is next"
    (let [starting-state (helper/state-create {:interface :tui :status :config-x-type :save :mock})
          expected-state (helper/state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
      (should= expected-state (sut/config-x-type starting-state :human))))

  (it "Config-x-type; if play-type is computer, config-x-difficulty"
    (let [starting-state (helper/state-create {:interface :tui :status :config-x-type :save :mock})
          expected-state (helper/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
      (should= expected-state (sut/config-x-type starting-state :computer))))

  (it "Config-x-difficulty; changes status to config-o-type once difficulty is selected"
    (let [starting-state (helper/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})
          expected-state (helper/state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
      (should= expected-state (sut/config-x-difficulty starting-state :hard))))

  (it "Config-o-type; changes status to config-o-difficulty if computer is selected"
    (let [starting-state (helper/state-create {:interface :tui :status :config-o-type :save :mock})
          expected-state (helper/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
      (should= expected-state (sut/config-o-type starting-state :computer))))

  (it "Config-o-type; changes status to select-board if human is selected"
    (let [starting-state (helper/state-create {:interface :tui :status :config-o-type :save :mock})
          expected-state (helper/state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
      (should= expected-state (sut/config-o-type starting-state :human))))


  (it "Config-o-difficulty; changes status to select-board once difficulty is selected"
    (let [starting-state (helper/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})
          expected-state (helper/state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :easy})]
      (should= expected-state (sut/config-o-difficulty starting-state :easy))))

  (it "Select-board; changes status to in-progress and populates a board once a board has been selected"
    (let [starting-state (helper/state-create {:interface :tui :status :select-board :save :mock})
          expected-state (helper/state-create {:interface :tui :status :in-progress :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
      (should= expected-state (sut/select-board starting-state 3))))


  (it "changes players"
    (let [starting-state (helper/state-create {:x-type :human :o-type :human :save :mock :board (assoc-in helper/empty-board [1 1] "X")})
          expected       (assoc starting-state :active-player-index 1)
          ending-state   (sut/change-player starting-state)]
      (should= expected ending-state)))

  (it "does not change players if the active player hasn't taken a turn"
    (let [starting-state (helper/state-create {:x-type :human :o-type :human :save :mock :board helper/empty-board})
          ending-state   (sut/change-player starting-state)]
      (should= (:active-player-index starting-state) (:active-player-index ending-state))))

  (it "can tell if the game is over"
    (should (sut/game-over? state-winner))
    (should (sut/game-over? state-tie))
    (should-not (sut/game-over? state-in-progress)))


  (it "can play again; status back to config x-type, state reset"
    (let [ending-state (sut/maybe-play-again state-tie true)]
      (should= :config-x-type (:status ending-state))
      (should= nil (:board ending-state))))

  (it "can not play again; status to game-over, state not reset"
    (let [ending-state (sut/maybe-play-again state-tie false)]
      (should= :game-over (:status ending-state))
      (should= (:board state-tie) (:board ending-state))))

  (it "has a player take a turn"
    (let [starting-state (helper/state-create {:x-type :human :o-type :human :save :mock :board helper/empty-board})
          expected       (helper/state-create {:x-type :human :o-type :human :save :mock :board (assoc-in helper/empty-board [1 1] "X")})
          ending-state   (sut/maybe-take-turn starting-state 5)]
      (should= expected ending-state)))

  (it "does not have a player take a turn if it isn't their turn"
    (let [starting-state (helper/state-create {:x-type :human :o-type :human :save :mock :board (assoc-in helper/empty-board [1 1] "X")})
          ending-state   (sut/maybe-take-turn starting-state 5)]
      (should= starting-state ending-state))))

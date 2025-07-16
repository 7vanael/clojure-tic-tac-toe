(ns tic-tac-toe.functions-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.functions :as sut]))

(describe "functions"
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
    (let [state-winner    (helper/state-create {:x-type :human :o-type :human :save :mock :status :winner
                                         :board  [["X" "O" "X"]
                                                  ["O" "X" "O"]
                                                  ["O" "X" "X"]] :active-player-index 0})
          state-tie (helper/state-create {:x-type :human :o-type :human :save :mock :status :tie
                                          :board  [["X" "O" "X"]
                                                   ["X" "O" "O"]
                                                   ["O" "X" "X"]] :active-player-index 0})
          state-in-progress (helper/state-create {:x-type :human :o-type :human :save :mock :status :in-progress
                                                  :board  [["X" "O" "X"]
                                                           ["O" "X" "O"]
                                                           ["O" "X" 9]] :active-player-index 1})]
      (should (sut/game-over? state-winner))
      (should (sut/game-over? state-tie))
      (should-not (sut/game-over? state-in-progress))))

  (it "can play again"
    )

  #_(it "has a player take a turn"
      (let [starting-state (helper/state-create {:x-type :human :o-type :human :save :mock :board helper/empty-board})
            expected       (helper/state-create {:x-type :human :o-type :human :save :mock :board (assoc-in helper/empty-board [1 1] "X")})
            ending-state   (sut/take-turn starting-state)]
        (should= expected ending-state)))

  (it "does not have a player take a turn if it isn't their turn")
  )

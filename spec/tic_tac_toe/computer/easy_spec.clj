(ns tic-tac-toe.computer.easy-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer.easy :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board_spec :as test-board]))


(def state-easy-empty-4
  {:interface :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :easy}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def state-easy-initial-4
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :easy}
                         {:character "O" :play-type :computer :difficulty :medium}]})

(describe "easy"
  (with-stubs)

  (it "Calls the easy computer turn method if the active player is easy"
    (with-redefs [easy (stub :computer-easy)]
      (core/take-turn state-easy-initial-4)
      (should-have-invoked :computer-easy)))

  (it "for easy, takes a random space"
    (let [result1 (core/take-turn state-easy-empty-4)
          result2 (core/take-turn state-easy-empty-4)
          result3 (core/take-turn state-easy-empty-4)]
      (should-not (= result1 result2 result3))))
  )
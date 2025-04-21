(ns tic-tac-toe.easy-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.computer-spec :as test-computer]))

(describe "easy"

  (it "for easy, takes a random space"
    (let [result1 (turn/take-turn test-computer/state-easy-empty-4)
          result2 (turn/take-turn test-computer/state-easy-empty-4)
          result3 (turn/take-turn test-computer/state-easy-empty-4)]
      (should-not (= result1 result2 result3))))
  )
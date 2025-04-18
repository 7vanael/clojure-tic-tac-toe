(ns tic-tac-toe.medium-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.computer-spec :as test-computer]
            [tic-tac-toe.easy :as easy]
            [tic-tac-toe.computer :as hard]))


(describe "medium"
  (with-stubs)

  (it "for medium, calls easy if random number is 0"
    (with-redefs [rand-int       (stub :rand-int {:return 0})
                  easy/easy      (stub :easy)
                  hard/hard (stub :hard)]
      (turn/take-turn test-computer/state-medium-initial-4)
      (should-have-invoked :easy)
      (should-not-have-invoked :hard)))

  (it "for medium, calls turn if random number is anything but 0"
    (with-redefs [rand-int       (stub :rand-int {:return 1})
                  easy/easy           (stub :easy)
                  hard/hard (stub :hard)]
      (turn/take-turn test-computer/state-medium-initial-4)
      (should-have-invoked :hard)
      (should-not-have-invoked :easy))))
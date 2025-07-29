(ns tic-tac-toe.computer.medium-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-computer]
            [tic-tac-toe.computer.easy :as easy]
            [tic-tac-toe.computer.hard :as hard]))


(describe "medium"
          (with-stubs)

          (it "for medium, calls easy if random number is 0"
              (with-redefs [rand-int       (stub :rand-int {:return 0})
                            easy/easy      (stub :easy)
                            hard/hard (stub :hard)]
                           (core/take-turn test-computer/state-medium-initial-4)
                           (should-have-invoked :easy)
                           (should-not-have-invoked :hard)))

          (it "for medium, calls turn if random number is anything but 0"
              (with-redefs [rand-int       (stub :rand-int {:return 1})
                            easy/easy           (stub :easy)
                            hard/hard (stub :hard)]
                           (core/take-turn test-computer/state-medium-initial-4)
                           (should-have-invoked :hard)
                           (should-not-have-invoked :easy))))
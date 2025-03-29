(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.game-spec :as test-game]
            [tic-tac-toe.console :as console]))

(describe "main"
    (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= test-game/state-initial (initialize-state :human :human)))

  (it "starts a new game"
    (with-redefs [game/start (stub :game/start)
                  console/welcome (stub :console/welcome)
                  console/display-options (stub :print-dup)]
      (with-in-str ":human\n:human\n" (-main))
      (should-have-invoked :console/welcome)
      (should-have-invoked :game/start))))
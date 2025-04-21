(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.game-spec :as test-game]
            [tic-tac-toe.console :as console]))



(describe "main"
    (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= test-game/state-initial (initialize-state :human :human 3)))

  (it "starts a new game"
    (with-redefs [game/start (stub :game/start)
                  console/welcome (stub :console/welcome)
                  console/display-options (stub :print-dup)
                  console/play-again-prompt (stub :play-again?)
                  console/board-size-prompt (stub :board-size-prompt {:return 3})]
      (with-in-str "human\nhuman\n3\nno\n" (-main))
      (should-have-invoked :console/welcome)
      (should-have-invoked :game/start)))

  (it "allows the user to choose to play again"
    (with-redefs [game/start (stub :game/start)
                  console/welcome (stub :console/welcome)
                  console/display-options (stub :print-dup)
                  console/play-again-prompt (stub :play-again?)
                  console/board-size-prompt (stub :board-size-prompt)]
      (with-in-str "computer\ncomputer\n3\ny\ncomputer\ncomputer\n4\nn\n" (-main))
      (should-have-invoked :console/welcome {:times 2})
      (should-have-invoked :print-dup {:times 4})
      (should-have-invoked :play-again? {:times 2})))


  )
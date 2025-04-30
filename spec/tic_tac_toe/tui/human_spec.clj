(ns tic-tac-toe.tui.human-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.tui.human :refer :all]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.tui.game-spec :as test-game]
            [tic-tac-toe.core :as core]))

(describe "human turn"
  (with-stubs)

  (it "The human turn method is called if the active player is human"
    (with-redefs [core/get-next-play (stub :human-turn {:return 1})
                  core/announce-player (stub :print-dup)]
      (should= test-game/state-center-x-corner-o (core/take-turn test-game/state-center-x-mid-turn))
      (should-have-invoked :human-turn)))

  (it "lets a player take a turn, repeatedly asks for input until valid play is selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  core/announce-player (stub :print-dup-announce)]
      (should= test-game/state-center-x
               (with-in-str "0\n45\njunk\n5\n" (core/take-turn (assoc test-game/state-initial :active-player-index 0))))
      (should-have-invoked :print-dup {:times 4})))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  core/announce-player (stub :print-dup-announce)]
      (should= test-game/state-4-first-x
               (with-in-str "0\n45\njunk\n6\n" (core/take-turn test-game/state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))
  )
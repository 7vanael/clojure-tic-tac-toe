(ns tic-tac-toe.human-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.game-spec :as test-game]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.next-play :as next-play]))

(describe "human turn"
  (with-stubs)

  (it "The human turn method is called if the active player is human"
    (with-redefs [next-play/get-next-play (stub :human-turn {:return 1})]
      (should= test-game/state-center-x-corner-o (turn/take-turn test-game/state-center-x-mid-turn))
      (should-have-invoked :human-turn)))

  (it "lets a player take a turn, repeatedly asks for input until valid play is selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (should= test-game/state-center-x
               (with-in-str "0\n45\njunk\n5\n" (turn/take-turn (assoc test-game/state-initial :active-player-index 0))))
      (should-have-invoked :print-dup {:times 4})))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (should= test-game/state-4-first-x
               (with-in-str "0\n45\njunk\n6\n" (turn/take-turn test-game/state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))
  )
(ns tic-tac-toe.turn-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.turn :refer :all]
            [tic-tac-toe.game-spec :as test-game]
            [tic-tac-toe.easy :as easy]
            [tic-tac-toe.medium :as medium]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.console :as console]))


(describe "turn"
  (with-stubs)

          (it "can tell what type of turn it is"
              (should= :human (get-turn-type test-game/state-center-x-mid-turn))
              (should= :hard (get-turn-type test-game/state-computer-2-4-empty)))

          (it "Calls the human turn method if the active player is human"
              (with-redefs [console/get-next-play (stub :human-turn {:return 1})]
                (should= test-game/state-center-x-corner-o (take-turn test-game/state-center-x-mid-turn))
                (should-have-invoked :human-turn)))

          (it "Calls the computer hard turn method if the active player is computer"
              (with-redefs [computer/hard (stub :computer-turn)]
                (take-turn test-game/state-computer-2-4-empty)
                (should-have-invoked :computer-turn)))

          (it "Calls the easy computer turn method if the active player is easy"
              (with-redefs [easy/easy (stub :computer-easy)]
                (take-turn test-game/state-easy-initial-4)
                (should-have-invoked :computer-easy)))

          (xit "Calls the medium computer method if the active player is medium"
              #_(with-redefs [medium/medium (stub :computer-medium)]
                (turn/take-turn test-game/state-medium-initial-4)
                (should-have-invoked :computer-medium))))
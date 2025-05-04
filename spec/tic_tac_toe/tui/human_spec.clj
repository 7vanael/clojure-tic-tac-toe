(ns tic-tac-toe.tui.human-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.tui.human :refer :all]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.tui.game-spec :as test-game]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence :as persistence]))

(describe "human turn"
  (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= test-game/state-initial (initialize-state {:type-x       :human :type-o :human :difficulty-x nil
                                                        :difficulty-o nil :board-size 3 :interface :tui :turn-phase nil})))



  (it "assigns a difficulty of nil if player type is human"
    (with-redefs [console/display-play-type-options (stub :print-dup-play-type)
                  console/welcome-message           (stub :console/welcome)
                  console/get-selection             (stub :get-selection {:return :human})
                  console/get-board-size            (stub :board-size {:return 3})
                  core/update-state                 (stub :update-state)
                  console/play-again?               (stub :play-again {:return false})
                  persistence/save-game             (stub :save-dup)
                  persistence/load-game             (stub :load {:return nil})]
      (core/start-game {:interface :tui})
      (should-have-invoked :update-state {:with [test-game/state-initial]})))

  (it "obtains a difficulty level if the play-type chosen is computer"
    (with-redefs [console/welcome-message           (stub :console/welcome)
                  console/display-play-type-options (stub :print-dup-play-type)
                  console/get-player-type           (stub :get-player-type {:return :computer})
                  console/get-difficulty            (stub :get-difficulty {:return :hard})
                  console/get-board-size            (stub :board-size {:return 4})
                  console/play-again?               (stub :play-again {:return false})
                  core/update-state                 (stub :update-state)
                  persistence/save-game             (stub :save-dup)
                  persistence/load-game             (stub :load {:return nil})]
      (core/start-game {:interface :tui})
      (should-have-invoked :update-state {:with [test-game/state-computer-2-4-empty]})))


  (it "The human turn method is called if the active player is human"
    (with-redefs [core/take-human-turn (stub :human-turn)]
      (core/take-turn test-game/state-center-x-mid-turn)
      (should-have-invoked :human-turn)))

  (it "lets a player take a turn, repeatedly asks for input until valid play is selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  console/announce-player     (stub :print-dup-announce)]
      (should= test-game/state-center-x
               (with-in-str "0\n45\njunk\n5\n" (human-turn-tui (assoc test-game/state-initial :active-player-index 0))))
      (should-have-invoked :print-dup {:times 4})))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  console/announce-player     (stub :print-dup-announce)]
      (should= test-game/state-4-first-x
               (with-in-str "0\n45\njunk\n6\n" (human-turn-tui test-game/state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))
  )
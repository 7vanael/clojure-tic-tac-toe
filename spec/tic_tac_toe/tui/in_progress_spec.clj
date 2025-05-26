(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.tui.in-progress :refer :all]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db nil))

  (it "checks for a saved game, offers to load it if found"
      (with-redefs [println (stub :print-dup)
                    core/update-state (stub :update-state {:invoke (fn [state] state)})
                    game-loop (stub :game-loop {:return nil})
                    initialize-state (stub :initialize-new)
                    console/play-again? (stub :play-again {:return false})]
        (let [new-game-state (test-core/state-create {:status :config :interface :tui :save :mock})
              saved-game-state (core/save-game (test-core/state-create {:interface           :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                                               [4 "X" 6]
                                                                                                                               [7 8 "O"]]
                                                                        :active-player-index 1 :type-x :human :type-o :human :save :mock}))]

          (with-in-str "y\n" (core/start-game new-game-state))
          (should-have-invoked :update-state {:with [(assoc saved-game-state :status :found-save :interface :tui)]}))))

  (it "resumes play of a loaded game"
      (with-redefs [console/save-found-prompt (stub :save-found-prompt)
                    println (stub :print-dup)
                    core/update-state (stub :update-state {:invoke (fn [state] state)})
                    initialize-state (stub :initialize-new)
                    console/play-again? (stub :play-again {:return false})
                    game-loop (stub :game-loop {:return nil})
                    console/exit-message (stub :print-dup)]
        (let [saved-state (core/save-game (test-core/state-create {:interface           :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                          [4 "X" 6]
                                                                                                          [7 8 "O"]]
                                                   :active-player-index 1 :type-x :human :type-o :human :save :mock}))
              new-game-state (test-core/state-create {:status :config :interface :tui :save :mock})]

          (with-in-str "y\n" (core/start-game new-game-state))
          (should-have-invoked :update-state {:with [(assoc saved-state :status :found-save :interface :tui)]})
          (should-not-have-invoked :initialize-new))))

  (it "proceeds to configuration if no loaded game found"
      (with-redefs [println (stub :print-dup)
                    core/load-game (stub :load {:return nil})
                    core/update-state (stub :update-state)
                    initialize-state (stub :initialize-new)
                    console/play-again? (stub :play-again {:return false})
                    game-loop (stub :game-loop {:return nil})
                    console/exit-message (stub :print-dup)]
        (let [new-game-state (test-core/state-create {:status :config :interface :tui})]
          (core/start-game new-game-state)
          (should-have-invoked :initialize-new))))

  (it "assigns a difficulty of nil if player type is human"
      (with-redefs [console/display-play-type-options (stub :print-dup-play-type)
                    console/welcome-message (stub :console/welcome)
                    console/get-selection (stub :get-selection {:return :human})
                    console/get-board-size (stub :board-size {:return 3})
                    core/update-state (stub :update-state)
                    console/play-again? (stub :play-again {:return false})
                    core/save-game (stub :save-dup)
                    core/load-game (stub :load {:return nil})
                    game-loop (stub :game-loop {:return nil})
                    console/exit-message (stub :print-dup)]
        (core/start-game {:interface :tui :save :mock})
        (should-have-invoked :game-loop {:with [test-core/state-initial]})))

  (it "obtains a difficulty level if the play-type chosen is computer"
      (with-redefs [console/welcome-message (stub :console/welcome)
                    console/display-play-type-options (stub :print-dup-play-type)
                    console/get-player-type (stub :get-player-type {:return :computer})
                    console/get-difficulty (stub :get-difficulty {:return :hard})
                    console/get-board-size (stub :board-size {:return 4})
                    console/play-again? (stub :play-again {:return false})
                    core/update-state (stub :update-state)
                    core/save-game (stub :save-dup)
                    core/load-game (stub :load {:return nil})
                    game-loop (stub :game-loop {:return nil})
                    console/exit-message (stub :print-dup)]
        (core/start-game {:interface :tui :save :edn})
        (should-have-invoked :game-loop {:with [test-core/state-computer-2-4-empty]})))


  (it "The human turn method is called if the active player is human"
      (with-redefs [core/take-human-turn (stub :human-turn)]
        (core/take-turn test-core/state-center-x-mid-turn)
        (should-have-invoked :human-turn)))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
      (with-redefs [console/print-number-prompt (stub :print-dup)
                    console/announce-player (stub :print-dup-announce)]
        (should= test-core/state-4-first-x
                 (with-in-str "0\n45\njunk\n6\n" (core/take-human-turn test-core/state-4-initial)))
        (should-have-invoked :print-dup {:times 4})))
  )
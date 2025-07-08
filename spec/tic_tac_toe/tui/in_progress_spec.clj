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
    (with-redefs [println             (stub :print-dup)
                  core/update-state   (stub :update-state {:invoke (fn [state] state)})
                  game-loop           (stub :game-loop {:return nil})
                  initialize-state    (stub :initialize-new)
                  console/play-again? (stub :play-again {:return false})]
      (let [new-game-state   (test-core/state-create {:status :config :interface :tui :save :mock})
            saved-game-state (core/save-game (test-core/state-create {:interface           :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                                             [4 "X" 6]
                                                                                                                             [7 8 "O"]]
                                                                      :active-player-index 1 :type-x :human :type-o :human :save :mock}))]

        (with-in-str "y\n" (core/start-game new-game-state))
        (should-have-invoked :update-state {:with [(assoc saved-game-state :status :found-save :interface :tui)]}))))

  (it "resumes play of a loaded game"
    (with-redefs [println              (stub :print-dup)
                  core/update-state    (stub :update-state {:invoke (fn [state] state)})
                  console/play-again?  (stub :play-again {:return false})
                  game-loop            (stub :game-loop {:return nil})
                  console/exit-message (stub :print-dup)]
      (let [saved-state    (core/save-game (test-core/state-create {:interface           :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                                           [4 "X" 6]
                                                                                                                           [7 8 "O"]]
                                                                    :active-player-index 1 :type-x :human :type-o :human :save :mock}))
            new-game-state (test-core/state-create {:status :config :interface :tui :save :mock})]

        (with-in-str "y\n" (core/start-game new-game-state))
        (should-have-invoked :update-state {:with [(assoc saved-state :status :found-save)]})
        (should-not-have-invoked :initialize-new))))

  (it "proceeds to configuration if no loaded game found"
    (with-redefs [println              (stub :print-dup)
                  core/load-game       (stub :load {:return nil})
                  core/update-state    (stub :update-state)
                  initialize-state     (stub :initialize-new)
                  console/play-again?  (stub :play-again {:return false})
                  game-loop            (stub :game-loop {:return nil})
                  console/exit-message (stub :print-dup)]
      (let [new-game-state (test-core/state-create {:status :config :interface :tui :save :mock})]
        (core/start-game new-game-state)
        (should-have-invoked :initialize-new {:with [new-game-state]}))))

  (it "changes status from config-x-type to config-x-difficulty if computer is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock})
            expected-state (test-core/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
        (should= expected-state (with-in-str "computer\n" (core/update-state starting-state))))))

  (it "changes status from config-x-type to config-o-type if human is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock})
            expected-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
        (should= expected-state (with-in-str "human\n" (core/update-state starting-state))))))

  (it "changes status from config-x-difficulty to config-o-type once difficulty is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})
            expected-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
        (should= expected-state (with-in-str "hard\n" (core/update-state starting-state))))))

  (it "changes status from config-o-type to config-o-difficulty if computer is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock})
            expected-state (test-core/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
        (should= expected-state (with-in-str "computer\n" (core/update-state starting-state))))))

  (it "changes status from config-o-type to select-board if human is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock})
            expected-state (test-core/state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
        (should= expected-state (with-in-str "human\n" (core/update-state starting-state))))))


  (it "changes status from config-o-difficulty to select-board once difficulty is selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})
            expected-state (test-core/state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :medium})]
        (should= expected-state (with-in-str "medium\n" (core/update-state starting-state))))))

  (it "changes status from select-board to ready once a board has been selected"
    (with-redefs [println (stub :print-dup)]
      (let [starting-state (test-core/state-create {:interface :tui :status :select-board :save :mock})
            expected-state (test-core/state-create {:interface :tui :status :ready :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
        (should= expected-state (with-in-str "1\n" (core/update-state starting-state))))))

  (it "The human turn method is called if the active player is human"
    (with-redefs [core/take-human-turn (stub :human-turn)]
      (core/take-turn test-core/state-center-x-mid-turn)
      (should-have-invoked :human-turn)))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  console/announce-player     (stub :print-dup-announce)]
      (should= test-core/state-4-first-x
               (with-in-str "0\n45\njunk\n6\n" (core/take-human-turn test-core/state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))
  )
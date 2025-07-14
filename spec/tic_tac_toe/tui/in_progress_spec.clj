(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.tui.in-progress :refer :all]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)
                  core/draw-state (stub :draw-state)])
  (before (reset! spec-helper/mock-db nil))

  (context "updates the state"
    (it "Welcome; checks for a saved game, offers to load it if found"
      (let [new-game         (test-core/state-create {:status :welcome :interface :tui :save :mock})
            saved-game-state (core/save-game (test-core/state-create {:interface           :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                                             [4 "X" 6]
                                                                                                                             [7 8 "O"]]
                                                                      :active-player-index 1 :type-x :human :type-o :human :save :mock}))
            result           (with-in-str "y\n" (core/update-state new-game))]

        (should= (assoc saved-game-state :status :found-save) result)
        (should-have-invoked :draw-state {:with [new-game]})))

    (it "Welcome; proceeds to x-type configuration if no loaded game found"
      (let [new-game (test-core/state-create {:status :welcome :interface :tui :save :mock})
            result   (with-in-str "n\n" (core/update-state new-game))]
        (should= (assoc new-game :status :config-x-type) result)
        (should-have-invoked :draw-state {:with [new-game]})))

    (it "Found-save; resumes play of a loaded game if player selects yes"
      (with-redefs [core/get-selection (stub :selection {:return true})]
        (let [state     (test-core/state-create {:interface :tui :status :found-save :active-player-index 1
                                                 :type-x    :human :type-o :human :save :mock :board [["X" "O" "X"]
                                                                                                      [4 "X" 6]
                                                                                                      [7 8 "O"]]})
              new-state (core/update-state state)]
          (should= (assoc state :status :in-progress) new-state))))

    (it "Found-save; moves to config-x-type if player selects not to load save"
      (with-redefs [core/get-selection (stub :selection {:return false})]
        (let [state     (test-core/state-create {:interface :tui :status :found-save :active-player-index 1
                                                 :type-x    :human :type-o :human :save :mock :board [["X" "O" "X"]
                                                                                                      [4 "X" 6]
                                                                                                      [7 8 "O"]]})
              clean-state (core/initial-state {:interface :tui :save :mock})
              new-state (core/update-state state)]
          (should= (assoc clean-state :status :config-x-type) new-state))))

    (it "Config-x-type; to config-x-difficulty if computer is selected"
      (with-redefs [core/get-selection (stub :selection {:return :computer})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock})
              expected-state (test-core/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Config-x-type;to config-o-type if human is selected"
      (with-redefs [core/get-selection (stub :selection {:return :human})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock})
              expected-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Config-x-difficulty; changes status to config-o-type once difficulty is selected"
      (with-redefs [core/get-selection (stub :selection {:return :hard})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})
              expected-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Config-o-type; changes status to config-o-difficulty if computer is selected"
      (with-redefs [core/get-selection (stub :selection {:return :computer})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock})
              expected-state (test-core/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Config-o-type; changes status to select-board if human is selected"
      (with-redefs [core/get-selection (stub :selection {:return :human})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-o-type :save :mock})
              expected-state (test-core/state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
          (should= expected-state (core/update-state starting-state)))))


    (it "Config-o-difficulty; changes status to select-board once difficulty is selected"
      (with-redefs [core/get-selection (stub :selection {:return :easy})]
        (let [starting-state (test-core/state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})
              expected-state (test-core/state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :easy})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Select-board; changes status to in-progress and populates a board once a board has been selected"
      (with-redefs [core/get-selection (stub :selection {:return 3})]
        (let [starting-state (test-core/state-create {:interface :tui :status :select-board :save :mock})
              expected-state (test-core/state-create {:interface :tui :status :in-progress :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Tie; changes status to config-x-type if get-selection (play again) returns true"
      (with-redefs [core/get-selection (stub :selection {:return true})]
        (let [starting-state (test-core/state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock })]
          (should= expected-state (core/update-state starting-state)))))

    (it "Tie; changes status to game-over if get-selection (play again) returns false"
      (with-redefs [core/get-selection (stub :selection {:return false})]
        (let [starting-state (test-core/state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (test-core/state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
          (should= expected-state (core/update-state starting-state)))))

    (it "Winner; changes status to config-x-type if get-selection (play again) returns true"
      (with-redefs [core/get-selection (stub :selection {:return true})]
        (let [starting-state (test-core/state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (test-core/state-create {:interface :tui :status :config-x-type :save :mock })]
          (should= expected-state (core/update-state starting-state)))))

    (it "Winner; changes status to game-over if get-selection (play again) returns false"
      (with-redefs [core/get-selection (stub :selection {:return false})]
        (let [starting-state (test-core/state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (test-core/state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
          (should= expected-state (core/update-state starting-state)))))
    )

  (context "get-selection"
    (it "found-save; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :found-save})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :found-save}))))

    (it "config-x-type; gets player type selection from console"
      (should= :human (with-in-str "human" (core/get-selection {:interface :tui :status :config-x-type})))
      (should= :computer (with-in-str "computer" (core/get-selection {:interface :tui :status :config-x-type}))))

    (it "config-o-type; gets player type selection from console"
      (should= :human (with-in-str "human" (core/get-selection {:interface :tui :status :config-o-type})))
      (should= :computer (with-in-str "computer" (core/get-selection {:interface :tui :status :config-o-type}))))

    (it "Config-x-difficulty; gets the computer difficulty selection from console"
      (should= :easy (with-in-str "easy" (core/get-selection {:interface :tui :status :config-x-difficulty})))
      (should= :medium (with-in-str "medium" (core/get-selection {:interface :tui :status :config-x-difficulty})))
      (should= :hard (with-in-str "hard" (core/get-selection {:interface :tui :status :config-x-difficulty}))))

    (it "Config-o-difficulty; gets the computer difficulty selection from console"
      (should= :easy (with-in-str "easy" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      (should= :medium (with-in-str "medium" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      (should= :hard (with-in-str "hard" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      )

    (it "Select-board; gets the board size selection (3, 4, [3 3 3]) from console"
      (should= 3 (with-in-str "1" (core/get-selection {:interface :tui :status :select-board})))
      (should= 4 (with-in-str "2" (core/get-selection {:interface :tui :status :select-board})))
      (should= [3 3 3] (with-in-str "3" (core/get-selection {:interface :tui :status :select-board})))
      )
    (it "tie; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :tie})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :tie}))))

    (it "winner; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :winner})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :winner}))))
    )

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
(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.functions :as functions]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.tui.in-progress :as sut]
            [tic-tac-toe.core :as core]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)
                  core/load-game (stub :load-game)
                  core/draw-state (fn [state] state)])
  (before (reset! spec-helper/mock-db nil))

  (context "get-selection"
    (it "found-save; gets a true/false and attaches to state"
      (let [state          {:interface :tui :status :found-save}
            expected-true  (assoc state :response true)
            expected-false (assoc state :response false)]
        (should= expected-true (with-in-str "y\n" (core/get-selection state)))
        (should= expected-false (with-in-str "n\n" (core/get-selection state)))))

    (it "config-x-type; gets player type selection from console"
      (let [state             {:interface :tui :status :config-x-type}
            expected-human    (assoc state :response :human)
            expected-computer (assoc state :response :computer)]
        (should= expected-human (with-in-str "human" (core/get-selection state)))
        (should= expected-computer (with-in-str "computer" (core/get-selection state)))))

    (it "config-o-type; gets player type selection from console"
      (let [state             {:interface :tui :status :config-o-type}
            expected-human    (assoc state :response :human)
            expected-computer (assoc state :response :computer)]
        (should= expected-human (with-in-str "human" (core/get-selection state)))
        (should= expected-computer (with-in-str "computer" (core/get-selection state)))))

    (it "Config-x-difficulty; gets the computer difficulty selection from console"
      (let [state           {:interface :tui :status :config-x-difficulty}
            expected-easy   (assoc state :response :easy)
            expected-medium (assoc state :response :medium)
            expected-hard   (assoc state :response :hard)]
        (should= expected-easy (with-in-str "easy" (core/get-selection state)))
        (should= expected-medium (with-in-str "medium" (core/get-selection state)))
        (should= expected-hard (with-in-str "hard" (core/get-selection state)))))

    (it "Config-o-difficulty; gets the computer difficulty selection from console"
      (let [state           {:interface :tui :status :config-o-difficulty}
            expected-easy   (assoc state :response :easy)
            expected-medium (assoc state :response :medium)
            expected-hard   (assoc state :response :hard)]
        (should= expected-easy (with-in-str "easy" (core/get-selection state)))
        (should= expected-medium (with-in-str "medium" (core/get-selection state)))
        (should= expected-hard (with-in-str "hard" (core/get-selection state)))))

    (it "Select-board; gets the board size selection (3, 4, [3 3 3]) from console"
      (let [state        {:interface :tui :status :select-board}
            expected-3   (assoc state :response 3)
            expected-4   (assoc state :response 4)
            expected-333 (assoc state :response [3 3 3])]
        (should= expected-3 (with-in-str "1" (core/get-selection state)))
        (should= expected-4 (with-in-str "2" (core/get-selection state)))
        (should= expected-333 (with-in-str "3" (core/get-selection state)))))

    (it "In-progress; gets the number of the space the player wants to play"
      (with-redefs [console/announce-player     (stub :announce-player)
                    console/print-number-prompt (stub :print-prompt)]
        (let [state    {:interface :tui :status :in-progress :x-type :human
                        :o-type    :human :active-player-index 0 :board helper/empty-board}
              expected (assoc state :response 3)]
          (should= expected (with-in-str "3\n" (core/get-selection state)))
          (should= expected (with-in-str "14\njunk\n3\n" (core/get-selection state))))))

    (it "tie; gets a true/false"
      (let [state          {:interface :tui :status :tie}
            expected-true  (assoc state :response true)
            expected-false (assoc state :response false)]
        (should= expected-true (with-in-str "y\n" (core/get-selection state)))
        (should= expected-false (with-in-str "n\n" (core/get-selection state)))))

    (it "winner; gets a true/false"
      (let [state          {:interface :tui :status :winner}
            expected-true  (assoc state :response true)
            expected-false (assoc state :response false)]
        (should= expected-true (with-in-str "y\n" (core/get-selection state)))
        (should= expected-false (with-in-str "n\n" (core/get-selection state)))))
    )

  (context "setup"
    (it "doesn't set up if status isn't config-x-type"
      (let [state        {:interface :tui :save :mock :status :in-progress}
            ending-state (sut/maybe-setup-state state)]
        (should= state ending-state)))

    (it "configures x-difficulty if x-type is computer"
      (with-redefs [core/get-selection (fn [state] (assoc state :response :medium))]
        (let [starting-state (helper/state-create {:status :config-x-difficulty :x-type :computer})
              expected       (helper/state-create {:status :config-o-type :x-type :computer :x-difficulty :medium})
              ending-state   (sut/maybe-config-x-difficulty starting-state)]
          (should= expected ending-state))))

    (it "does not configure x-difficulty if x-type was set to human and status is not config-x-difficulty"
      (with-redefs [core/get-selection (fn [state] (assoc state :response :medium))]
        (let [starting-state (helper/state-create {:status :config-o-type :x-type :human})
              ending-state   (sut/maybe-config-x-difficulty starting-state)]
          (should= starting-state ending-state))))

    (it "configures o-difficulty if o-type is computer"
      (with-redefs [core/get-selection (fn [state] (assoc state :response :hard))]
        (let [starting-state (helper/state-create {:status :config-o-difficulty :o-type :computer})
              expected       (helper/state-create {:status :select-board :o-type :computer :o-difficulty :hard})
              ending-state   (sut/maybe-config-o-difficulty starting-state)]
          (should= expected ending-state))))

    (it "does not configure o-difficulty if o-type was set to human and status is not config-o-difficulty"
      (with-redefs [core/get-selection (fn [state] (assoc state :response :medium))]
        (let [starting-state (helper/state-create {:status :config-o-type :x-type :human})
              ending-state   (sut/maybe-config-o-difficulty starting-state)]
          (should= starting-state ending-state))))

    (it "sets up a state, from config-x-type to in-progress"
      (let [initial-state       (helper/state-create {:interface :tui :save :mock :status :config-x-type})
            expected-state      (helper/state-create {:x-type    :human :o-type :computer :o-difficulty :hard
                                                      :status    :in-progress :board [[1 2 3] [4 5 6] [7 8 9]]
                                                      :interface :tui})
            get-selection-calls (atom 0)]
        (with-redefs [core/get-selection (fn [state]
                                           (swap! get-selection-calls inc)
                                           (cond (= @get-selection-calls 1) (assoc state :response :human)
                                                 (= @get-selection-calls 2) (assoc state :response :computer)
                                                 (= @get-selection-calls 3) (assoc state :response :hard)
                                                 :else (assoc state :response 3)))]
          (should= expected-state (sut/maybe-setup-state initial-state)))))
    )
  (context "starting the game"
    (it "prompts to resume a game if one was loaded, starts fresh if declined"
      (with-redefs [core/get-selection (fn [state] (assoc state :response false))]
        (let [loaded-game (helper/state-create {:interface :tui :save :mock :status :found-save :board [[1 "X" 3] [4 5 "O"] [7 8 9]]
                                                :x-type    :human :o-type :computer :o-difficulty :easy})
              expected    (core/fresh-start loaded-game)]
          (should= expected (sut/prompt-to-resume loaded-game)))))

    (it "prompts to resume a game if one was loaded, returns the loaded state in progress if agreed"
      (with-redefs [core/get-selection (fn [state] (assoc state :response true))]
        (let [loaded-game    (helper/state-create {:interface :tui :save :mock :status :found-save :board [[1 "X" 3] [4 5 "O"] [7 8 9]]
                                                   :x-type    :human :o-type :computer :o-difficulty :easy})
              expected       (assoc loaded-game :status :in-progress)]
          (should= expected (sut/prompt-to-resume loaded-game)))))

    (it "might resume a loaded game; with a loaded game and y, gets the loaded-game back in-progress"
      (with-redefs [core/get-selection (fn [state] (assoc state :response true))]
        (let [loaded-game    (helper/state-create {:interface :tui :save :mock :status :found-save :board [[1 "X" 3] [4 5 "O"] [7 8 9]]
                                                   :x-type    :human :o-type :computer :o-difficulty :easy})
              starting-state (helper/state-create {:status :config :interface :tui :save :mock})
              state          (assoc starting-state :loaded-game loaded-game)
              expected       (assoc loaded-game :status :in-progress)]
          (should= expected (sut/maybe-resume-game state)))))

    (it "might resume a loaded game; with a loaded game and n, returns a fresh state in config-x"
      (with-redefs [core/get-selection (fn [state] (assoc state :response false))]
        (let [loaded-game    (helper/state-create {:interface :tui :save :mock :status :found-save :board [[1 "X" 3] [4 5 "O"] [7 8 9]]
                                                   :x-type    :human :o-type :computer :o-difficulty :easy})
              starting-state (helper/state-create {:status :config :interface :tui :save :mock})
              state          (assoc starting-state :loaded-game loaded-game)
              expected       (core/fresh-start loaded-game)]
          (should= expected (sut/maybe-resume-game state)))))

    (it "might resume a loaded game; with no loaded game, returns a fresh state in config-x"
      (let [starting-state (helper/state-create {:status :config :interface :tui :save :mock})
            loaded-game    starting-state
            state          (assoc starting-state :loaded-game loaded-game)
            expected       (core/fresh-start loaded-game)]
        (should= expected (sut/maybe-resume-game state))))

    #_(it "starts the loop"
        )
    )

  (it "takes a human turn on the human's turn"
    (with-redefs [core/get-selection (fn [state] (assoc state :response 5))]
      (let [state    (helper/state-create {:status       :in-progress :interface :tui :x-type :human :o-type :computer
                                           :o-difficulty :hard :board helper/empty-board})
            expected (helper/state-create {:status       :in-progress :interface :tui :x-type :human :o-type :computer
                                           :o-difficulty :hard :board helper/center-x-board})]
        (should= expected (core/take-turn state)))))

  #_(it "should handle a complete game that ends in game-over"
      (let [initial-state    (core/initial-state {:interface :tui})
            configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
            final-state      (assoc configured-state :status :game-over)]

        (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                      sut/game-loop      (stub :game-loop {:return final-state})]

          (core/start-game initial-state)

          (should-have-invoked :configure-loop {:with [(assoc initial-state :status :welcome)]})
          (should-have-invoked :game-loop {:with [configured-state]}))))

  #_(it "should handle play-again cycle then game-over"
      (let [initial-state    (core/initial-state {:interface :tui})
            configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
            replay-state     (assoc configured-state :status :config-x-type)
            final-state      (assoc configured-state :status :game-over)
            game-loop-calls  (atom 0)]
        (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                      sut/game-loop      (fn [state]
                                           (swap! game-loop-calls inc)
                                           (if (= @game-loop-calls 1)
                                             replay-state
                                             final-state))]

          (core/start-game initial-state)
          (should-have-invoked :configure-loop {:times 2})
          (should-have-invoked :configure-loop {:with [(assoc initial-state :status :welcome)]})
          (should-have-invoked :configure-loop {:with [replay-state]})
          (should= 2 @game-loop-calls))))

  #_(it "should return nil when game ends (allowing program to exit)"
      (let [initial-state    (core/initial-state {:interface :tui})
            configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
            final-state      (assoc configured-state :status :game-over)]

        (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                      sut/game-loop      (stub :game-loop {:return final-state})]

          (should= nil (core/start-game initial-state)))))

  (context "play again phase"

    (it "quits"
      (with-redefs [functions/game-over? (fn [_] true)
                    sut/play-again? (fn [_] false)
                    console/exit-message (stub :exit-message)
                    sut/exit-game! (stub :exit-game!) ]
        (sut/play-game {:interface :tui :save ::mock :status :winner})
        ;(should-have-invoked :exit-message)
        (should-have-invoked :exit-game!)))
    )

  )
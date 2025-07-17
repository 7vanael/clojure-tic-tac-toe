(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.functions :as functions]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.tui.in-progress :as sut]
            [tic-tac-toe.core :as core]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)
                  core/load-game (stub :load-game)
                  core/draw-state (stub :draw-state)])
  (before (reset! spec-helper/mock-db nil))

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
    (should= :hard (with-in-str "hard" (core/get-selection {:interface :tui :status :config-o-difficulty}))))

  (it "Select-board; gets the board size selection (3, 4, [3 3 3]) from console"
    (should= 3 (with-in-str "1" (core/get-selection {:interface :tui :status :select-board})))
    (should= 4 (with-in-str "2" (core/get-selection {:interface :tui :status :select-board})))
    (should= [3 3 3] (with-in-str "3" (core/get-selection {:interface :tui :status :select-board}))))

  (it "tie; gets a true/false"
    (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :tie})))
    (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :tie}))))

  (it "winner; gets a true/false"
    (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :winner})))
    (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :winner}))))

  (context "setup"
    (it "doesn't set up if status isn't config-x-type"
      (let [state        {:interface :tui :save :mock :status :in-progress}
            ending-state (sut/maybe-setup-state state)]
        (should= state ending-state)))

    (it "configures x-type"                                 ;Well... How to test each step of the maybe-setup-state? This is just rewriting other functions!
      ; Maybe lines of the maybe-setup-state that are just already tested functions don't need to be tested like this, we'll test the
      ; entire function at the end?
      (with-redefs [core/get-selection (stub :get-selection {:return :human})]
        (let [starting-state (helper/state-create {:status :config-x-type})
              expected       (helper/state-create {:status :config-o-type :x-type :human})
              ending-state   (functions/config-x-type starting-state (core/get-selection starting-state))] ;no sut calls
          (should= expected ending-state))))

    (it "configures x-difficulty if x-type is computer"
      (with-redefs [core/get-selection (stub :get-selection {:return :medium})]
        (let [starting-state (helper/state-create {:status :config-x-difficulty :x-type :computer})
              expected       (helper/state-create {:status :config-o-type :x-type :computer :x-difficulty :medium})
              ending-state   (sut/maybe-config-x-difficulty starting-state)]
          (should= expected ending-state))))

    (it "does not configure x-difficulty if x-type was set to human and status is not config-x-difficulty"
      (with-redefs [core/get-selection (stub :get-selection {:return :medium})]
        (let [starting-state (helper/state-create {:status :config-o-type :x-type :human})
              ending-state   (sut/maybe-config-x-difficulty starting-state)]
          (should= starting-state ending-state))))

    (it "configures o-difficulty if o-type is computer"
      (with-redefs [core/get-selection (stub :get-selection {:return :hard})]
        (let [starting-state (helper/state-create {:status :config-o-difficulty :o-type :computer})
              expected       (helper/state-create {:status :select-board :o-type :computer :o-difficulty :hard})
              ending-state   (sut/maybe-config-o-difficulty starting-state)]
          (should= expected ending-state))))

    (it "does not configure o-difficulty if o-type was set to human and status is not config-o-difficulty"
      (with-redefs [core/get-selection (stub :get-selection {:return :medium})]
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
                                           (cond (= @get-selection-calls 1) :human
                                                 (= @get-selection-calls 2) :computer
                                                 (= @get-selection-calls 3) :hard
                                                 :else 3))]
          (should= expected-state (sut/maybe-setup-state initial-state)))))
    )

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
  )
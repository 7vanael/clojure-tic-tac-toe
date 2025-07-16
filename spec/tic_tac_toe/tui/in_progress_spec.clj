(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]
            [tic-tac-toe.tui.in-progress :as sut]
            [tic-tac-toe.core :as core]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
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

  (it "should handle a complete game that ends in game-over"
    (let [initial-state (core/initial-state {:interface :tui})
          configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
          final-state (assoc configured-state :status :game-over)]

      (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                    sut/game-loop (stub :game-loop {:return final-state})]

        (core/start-game initial-state)

        (should-have-invoked :configure-loop {:with [(assoc initial-state :status :welcome)]})
        (should-have-invoked :game-loop {:with [configured-state]}))))

  (it "should handle play-again cycle then game-over"
    (let [initial-state (core/initial-state {:interface :tui})
          configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
          replay-state (assoc configured-state :status :config-x-type)
          final-state (assoc configured-state :status :game-over)
          game-loop-calls (atom 0)]
      (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                    sut/game-loop (fn [state]
                                    (swap! game-loop-calls inc)
                                    (if (= @game-loop-calls 1)
                                      replay-state
                                      final-state))]

        (core/start-game initial-state)
        (should-have-invoked :configure-loop {:times 2})
        (should-have-invoked :configure-loop {:with [(assoc initial-state :status :welcome)]})
        (should-have-invoked :configure-loop {:with [replay-state]})
        (should= 2 @game-loop-calls))))

  (it "should return nil when game ends (allowing program to exit)"
    (let [initial-state (core/initial-state {:interface :tui})
          configured-state (assoc initial-state :status :in-progress :board [[1 2 3] [4 5 6] [7 8 9]])
          final-state (assoc configured-state :status :game-over)]

      (with-redefs [sut/configure-loop (stub :configure-loop {:return configured-state})
                    sut/game-loop (stub :game-loop {:return final-state})]

        (should= nil (core/start-game initial-state)))))
  )
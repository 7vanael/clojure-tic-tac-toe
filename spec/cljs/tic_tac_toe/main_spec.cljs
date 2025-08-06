(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should= should before context focus-context should-contain should-be-nil with-stubs stub should-have-invoked should-not-have-invoked]]
                   [c3kit.wire.spec-helperc :refer [should-select should-not-select]])
  (:require [reagent.core :as r]
            [c3kit.wire.spec-helper :as wire]
            [speclj.core]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.main :as sut]
            [tic-tac-toe.draw]))



(describe "main static"
  (with-stubs)
  (wire/with-root-dom)

  (before (wire/render [sut/game-component]))


  (context "computer turn"
    (it "when playing computer vs human, computer goes once"
      (with-redefs [js/setTimeout (stub :timeout)]
                   (reset! sut/state {:interface           :static :status :in-progress :save :ratom
                                      :active-player-index 1 :board [[1 2 3] [4 5 6] ["X" 8 9]]
                                      :players             [{:character "X" :play-type :human :difficulty nil}
                                                            {:character "O" :play-type :computer :difficulty :easy}]})
                   (wire/render [sut/game-component])
                   (sut/maybe-take-computer-turn)
                   (should= 0 (:active-player-index @sut/state))
                   (wire/render [sut/game-component])
                   (should= 7 (wire/count-all "td.empty"))
                   (should-not-have-invoked :timeout)))

    (it "when playing computer vs computer, computer calls computer's turn again"
      (with-redefs [js/setTimeout (stub :timeout)]
                   (reset! sut/state {:interface           :static :status :in-progress :save :ratom
                                      :active-player-index 1 :board [[1 2 3] [4 5 6] ["X" 8 9]]
                                      :players             [{:character "X" :play-type :computer :difficulty :hard}
                                                            {:character "O" :play-type :computer :difficulty :easy}]})
                   (wire/render [sut/game-component])
                   (sut/maybe-take-computer-turn)
                   (should-have-invoked :timeout)
                   (should= 0 (:active-player-index @sut/state))))
    )

  (context "human move"
        (it "human move triggers computer move if playing human v computer"
      (reset! sut/state {:status              :in-progress
                         :interface           :static
                         :save                :ratom
                         :board               [[1 2 3] [4 5 6] [7 8 9]]
                         :active-player-index 0
                         :players             [{:character "X" :play-type :human :difficulty nil}
                                               {:character "O" :play-type :computer :difficulty :easy}]})
      (reset! sut/status-cursor :in-progress)
      (wire/render [sut/game-component])
      (wire/click! "#cell-5")
      (should= "X" (get-in @sut/state [:board 1 1]))

      (let [board   (:board @sut/state)
            o-count (count (filter #{"O"} (flatten board)))]
        (should= 1 o-count))
      (should= 0 (:active-player-index @sut/state))
      (should= 7 (wire/count-all "td.empty"))
      (should= 2 (wire/count-all "td.occupied")))

    (it "human move only results in one turn being taken if playing human v human"
      (reset! sut/state {:status              :in-progress
                         :interface           :static
                         :save                :ratom
                         :board               [[1 2 3] [4 5 6] [7 8 9]]
                         :active-player-index 0
                         :players             [{:character "X" :play-type :human :difficulty nil}
                                               {:character "O" :play-type :human :difficulty nil}]})
      (reset! sut/status-cursor :in-progress)
      (wire/render [sut/game-component])
      (wire/click! "#cell-5")
      (should= "X" (get-in @sut/state [:board 1 1]))
      (should= 1 (:active-player-index @sut/state))
      (should= 8 (wire/count-all "td.empty")))
    )

  (context "core wrappers"
    (it "make-move calls core/play-turn! and gives computer a chance to play"
      (with-redefs [sut/maybe-take-computer-turn (stub :computer-chance)]
       (reset! sut/state {:status              :in-progress
               :interface           :static
               :save                :ratom
               :board               [[1 2 3] [4 5 6] [7 8 9]]
               :active-player-index 0
               :players             [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type :human :difficulty nil}]})
      (reset! sut/status-cursor :in-progress)
      (wire/render [sut/game-component])
      (sut/make-move 5)
      (should= "X" (get-in @sut/state [:board 1 1]))
      (should= 1 (:active-player-index @sut/state))
      (should-have-invoked :computer-chance)))

    (it "configure x-type can set x to human and progress state to config-o-type"
      (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                         :players   [{:character "X" :play-type nil :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-x-type :human)
      (should= :human (get-in @sut/state [:players 0 :play-type]))
      (should= :config-o-type (:status @sut/state)))

    (it "configure x-type can set x to computer and progress status to config x-difficulty"
      (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                         :players   [{:character "X" :play-type nil :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-x-type :computer)
      (should= :computer (get-in @sut/state [:players 0 :play-type]))
      (should= :config-x-difficulty (:status @sut/state)))

    (it "configure o-type can set o to human and progress status to select-board"
      (reset! sut/state {:interface :static :status :config-o-type :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-o-type :human)
      (should= :human (get-in @sut/state [:players 1 :play-type]))
      (should= :select-board (:status @sut/state)))

    (it "configure o-type can set o to computer and progress status to config-o-difficulty"
      (reset! sut/state {:interface :static :status :config-o-type :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-o-type :computer)
      (should= :computer (get-in @sut/state [:players 1 :play-type]))
      (should= :config-o-difficulty (:status @sut/state)))



    (it "configure-x-difficulty can set x to easy and progress state to config-o-type"
      (reset! sut/state {:interface :static :status :config-x-difficulty :save :ratom
                         :players   [{:character "X" :play-type :computer :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-x-difficulty :easy)
      (should= :easy (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state)))

    (it "configure-x-difficulty can set x to medium and progress state to config-o-type"
      (reset! sut/state {:interface :static :status :config-x-difficulty :save :ratom
                         :players   [{:character "X" :play-type :computer :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-x-difficulty :medium)
      (should= :medium (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state)))

    (it "configure-x-difficulty can set x to:hard and progress state to config-o-type"
      (reset! sut/state {:interface :static :status :config-x-difficulty :save :ratom
                         :players   [{:character "X" :play-type :computer :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (sut/configure-x-difficulty :hard)
      (should= :hard (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state)))


    (it "configure o-difficulty can set o to easy and progress status to select-board"
      (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type :computer :difficulty nil}]})
      (sut/configure-o-difficulty :easy)
      (should= :easy (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state)))

    (it "configure o-difficulty can set o to medium and progress status to select-board"
      (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type :computer :difficulty nil}]})
      (sut/configure-o-difficulty :medium)
      (should= :medium (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state)))

    (it "configure o-difficulty can set o to hard and progress status to select-board"
      (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type :computer :difficulty nil}]})
      (sut/configure-o-difficulty :hard)
      (should= :hard (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state)))

    (it "configure board size can set the board to 3x3 and progress status to in-progress (& option to computer turn)"
      (with-redefs [js/setTimeout (stub :timeout)]
       (reset! sut/state {:interface :static :status :select-board :save :ratom
             :players   [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :computer :difficulty nil}]})
      (sut/configure-board-size 3)
      (should= [[1 2 3] [4 5 6] [7 8 9]] (:board @sut/state))
      (should= :in-progress (:status @sut/state))
      (should-have-invoked :timeout)))

    (it "configure board size can set the board to 4x4 and progress status to in-progress (& option to computer turn)"
      (with-redefs [js/setTimeout (stub :timeout)]
       (reset! sut/state {:interface :static :status :select-board :save :ratom
             :players   [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :computer :difficulty nil}]})
      (sut/configure-board-size 4)
      (should= [[1 2 3 4] [5 6 7 8] [9 10 11 12] [13 14 15 16]] (:board @sut/state))
      (should= :in-progress (:status @sut/state))
      (should-have-invoked :timeout)))

    (it "play-again gets a fresh-start"
      (reset! sut/state {:interface           :static :status :tie :save :ratom
                         :active-player-index 0 :board [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]]
                         :players             [{:character "X" :play-type :human :difficulty nil}
                                               {:character "O" :play-type :computer :difficulty :hard}]})
      (sut/play-again)
      (should= @sut/state (core/fresh-start {:interface :static :save :ratom}))
      )
    )
  )
(ns tic-tac-toe.gui.in-progress-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.in-progress :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]
            [tic-tac-toe.board_spec :as test-board]
            [tic-tac-toe.game-spec :as test-game]))

(describe "in-progress"
  (with-stubs)

  (context "lines"
           (it "gets start & end points of a horizontal line for the grid"
             (should= [[0 0 10 0] [0 5 10 5] [0 10 10 10]] (get-horizontal-lines 0 0 10 5 2))
             (should= [[0 144 576 144] [0 336 576 336] [0 528 576 528] [0 720 576 720]]
                      (get-horizontal-lines 144 0 576 192 3))
             (should= [[0 144 576 144] [0 288 576 288] [0 432 576 432] [0 576 576 576] [0 720 576 720]]
                      (get-horizontal-lines 144 0 576 144 4)))
           (it "gets start & end points of vertical lines for the grid"
             (should= [[0 0 0 10] [5 0 5 10] [10 0 10 10]] (get-vertical-lines 0 10 5 2))
             (should= [[0 144 0 720] [192 144 192 720] [384 144 384 720] [576 144 576 720]]
                      (get-vertical-lines 144 720 192 3))
             (should= [[0 144 0 720] [144 144 144 720] [288 144 288 720] [432 144 432 720] [576 144 576 720]]
                      (get-vertical-lines 144 720 144 4))
             )
           )
  (context "cells"
           (it "generates maps for each cell containing the center point coordinates & value"
             (should= [{:x 5, :y 5, :value 1} {:x 15, :y 5, :value 2}
                       {:x 5, :y 15, :value 3} {:x 15, :y 15, :value 4}]
                      (generate-cells [[1 2] [3 4]] 10 [0 0]))
             (should= [{:x 96, :y 240, :value 1} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
                       {:x 96, :y 432, :value 4} {:x 288, :y 432, :value 5} {:x 480, :y 432, :value 6}
                       {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}]
                      (generate-cells test-board/empty-board 192 [0 144])))
           )
  (context "eval-board"

           (it "updates status to winner if a player has won"
             (should= test-game/state-win-x-row-evaluated (eval-board test-game/state-win-x-row)))

           (it "updates status to draw if a board is full and no player has won"
             (should= test-game/state-draw-evaluated (eval-board test-game/state-draw))))

  (context "update selects based on turn-phase"
           (it "if turn-phase is nil, initializes to awaiting-input and initializes a turn"
             (with-redefs [core/take-turn (stub :take-turn)
                           multis/update-in-turn (stub :update-in-turn)]
               (multis/update-state (test-gui/state-create {:status :in-progress}))
               (should-have-invoked :take-turn {:with [(test-gui/state-create {:status :in-progress :turn-phase :awaiting-input})]})))

           (it "if turn-phase is awaiting-input, no change is made to the state"
             (should= (test-gui/state-create {:status :in-progress :turn-phase :awaiting-input :board [[1 2 3]]})
                      (multis/update-state (test-gui/state-create {:status :in-progress :turn-phase :awaiting-input :board [[1 2 3]]}))))

           (it "if turn-phase is input-received, the board is evaluated and the turn is marked complete"
             (with-redefs [eval-board (stub :eval-board {:return (test-gui/state-create {:status :in-progress :turn-phase :input-received :board [[1 2 3]]})})]
               (should= (test-gui/state-create {:status :in-progress :turn-phase :turn-complete :board [[1 2 3]]})
                      (multis/update-state (test-gui/state-create {:status :in-progress :turn-phase :input-received :board [[1 2 3]]})))))

           (it "if turn-phase is turn-complete, it changes the active player, changes turn-phase to awaiting input and initiates the next turn"
             (with-redefs [core/take-turn (stub :take-turn)]
               (multis/update-state (test-gui/state-create {:status :in-progress :turn-phase :turn-complete :active-player-index 0 :x-type :human :o-type :human}))
               (should-have-invoked :take-turn {:with [(test-gui/state-create {:status :in-progress :turn-phase :awaiting-input :active-player-index 1 :x-type :human :o-type :human})]})))

           )

  )


(ns tic-tac-toe.gui.in-progress-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.in-progress :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.board_spec :as test-board]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))


;TODO use this mock for testing
(defmethod core/update-state :testing [state value]
  (assoc state :status :game-over))

(def cells-3d
  [{:x 30, :y 192, :z 0, :value 1} {:x 90, :y 192, :z 0, :value 2} {:x 150, :y 192, :z 0, :value 3}
   {:x 30, :y 252, :z 0, :value 4} {:x 90, :y 252, :z 0, :value 5} {:x 150, :y 252, :z 0, :value 6}
   {:x 30, :y 312, :z 0, :value 7} {:x 90, :y 312, :z 0, :value 8} {:x 150, :y 312, :z 0, :value 9}

   {:x 228, :y 192, :z 1, :value 10} {:x 288, :y 192, :z 1, :value 11} {:x 348, :y 192, :z 1, :value 12}
   {:x 228, :y 252, :z 1, :value 13} {:x 288, :y 252, :z 1, :value 14} {:x 348, :y 252, :z 1, :value 15}
   {:x 228, :y 312, :z 1, :value 16} {:x 288, :y 312, :z 1, :value 17} {:x 348, :y 312, :z 1, :value 18}

   {:x 426, :y 192, :z 2, :value 19} {:x 486, :y 192, :z 2, :value 20} {:x 546, :y 192, :z 2, :value 21}
   {:x 426, :y 252, :z 2, :value 22} {:x 486, :y 252, :z 2, :value 23} {:x 546, :y 252, :z 2, :value 24}
   {:x 426, :y 312, :z 2, :value 25} {:x 486, :y 312, :z 2, :value 26} {:x 546, :y 312, :z 2, :value 27}])

(def cells-center-x-corner-o
  [{:x 96, :y 240, :value "O"} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
   {:x 96, :y 432, :value 4} {:x 288, :y 432, :value "X"} {:x 480, :y 432, :value 6}
   {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}])

(def cells-center-x-corner-xo
  [{:x 96, :y 240, :value "O"} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
   {:x 96, :y 432, :value 4} {:x 288, :y 432, :value "X"} {:x 480, :y 432, :value 6}
   {:x 96, :y 624, :value "X"} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}])

(describe "gui in-progress"
  (with-stubs)
  (before (reset! spec-helper/mock-db nil))

  (it "can tell if the board is 3d or not"
    (should= true (board-3d? [[[1 2] [3 4]] [[5 6] [7 8]]]))
    (should= false (board-3d? [[1 2 3] [4 5 6] [7 8 9]])))

  (context "lines"
    (it "gets start & end points of a horizontal line for a grid"
      (should= [[0 0 10 0] [0 5 10 5] [0 10 10 10]] (get-horizontal-lines 0 0 10 5 2))
      (should= [[0 144 576 144] [0 336 576 336] [0 528 576 528] [0 720 576 720]]
               (get-horizontal-lines 144 0 576 192 3)))
    (it "gets start & end points of horizonal lines for a 4x grid"
      (should= [[0 144 576 144] [0 288 576 288] [0 432 576 432] [0 576 576 576] [0 720 576 720]]
               (get-horizontal-lines 144 0 576 144 4)))
    (it "gets start & end points of horizonal lines for a 3x3x3 grid"
      (should= [[0 144 576 144] [0 288 576 288] [0 432 576 432] [0 576 576 576] [0 720 576 720]]
               (get-horizontal-lines 144 0 576 144 4)))
    (it "gets start & end points of vertical lines for the grid"
      (should= [[0 0 0 10] [5 0 5 10] [10 0 10 10]] (get-vertical-lines 0 10 5 2 0))
      (should= [[0 144 0 720] [192 144 192 720] [384 144 384 720] [576 144 576 720]]
               (get-vertical-lines 144 720 192 3 0)))
    (it "gets start and end points of vertical lines for a 4x grid"
      (should= [[0 144 0 720] [144 144 144 720] [288 144 288 720] [432 144 432 720] [576 144 576 720]]
               (get-vertical-lines 144 720 144 4 0)))
    (it "gets start and end points of vertical lines for a 3x3x3 grid"
      (should= [[0 144 0 720] [144 144 144 720] [288 144 288 720] [432 144 432 720] [576 144 576 720]]
               (get-vertical-lines 144 720 144 4 0)))

    (it "gets 3 sets of horizontal and vertical lines for a 3x3x3 grid"
      (should= [[0 162 180 162] [0 222 180 222] [0 282 180 282] [0 342 180 342]
                [0 162 0 342] [60 162 60 342] [120 162 120 342] [180 162 180 342]

                [198 162 378 162] [198 222 378 222] [198 282 378 282] [198 342 378 342]
                [198 162 198 342] [258 162 258 342] [318 162 318 342] [378 162 378 342]

                [396 162 576 162] [396 222 576 222] [396 282 576 282] [396 342 576 342]
                [396 162 396 342] [456 162 456 342] [516 162 516 342] [576 162 576 342]]
               (get-lines-3d 3)))
    )

  (context "cells"
    (it "generates maps for each cell containing the center point coordinates & value in a 2d grid"
      (should= [{:x 5, :y 5, :value 1} {:x 15, :y 5, :value 2}
                {:x 5, :y 15, :value 3} {:x 15, :y 15, :value 4}]
               (generate-cells-2d [[1 2] [3 4]] 10 [0 0]))

      (should= [{:x 96, :y 240, :value 1} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
                {:x 96, :y 432, :value 4} {:x 288, :y 432, :value 5} {:x 480, :y 432, :value 6}
                {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}]
               (generate-cells-2d test-board/empty-board 192 [0 144]))

      (should= [{:x 96, :y 240, :value 1} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
                {:x 96, :y 432, :value 4} {:x 288, :y 432, :value 5} {:x 480, :y 432, :value 6}
                {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}]
               (generate-cells test-board/empty-board)))

    (it "generates maps for each cell containing the center point coordinates & value in a 3d grid"
      (should= [{:x 5, :y 5, :z 0, :value 1} {:x 15, :y 5, :z 0, :value 2}
                {:x 5, :y 15, :z 0, :value 3} {:x 15, :y 15, :z 0, :value 4}

                {:x 203, :y 5, :z 1, :value 5} {:x 213, :y 5, :z 1, :value 6}
                {:x 203, :y 15, :z 1, :value 7} {:x 213, :y 15, :z 1, :value 8}

                {:x 401, :y 5, :z 2, :value 9} {:x 411, :y 5, :z 2, :value 10}
                {:x 401, :y 15, :z 2, :value 11} {:x 411, :y 15, :z 2, :value 12}]
               ;dependent on the set screen size defined in gui-util, usable-screen =
               (generate-cells-3d [[[1 2] [3 4]] [[5 6] [7 8]] [[9 10] [11 12]]] 10 [0 0]))

      (should= cells-3d (generate-cells-3d test-board/empty-3d-board 60 [0 162])))

    (it "generates cells from the generate-cells method"
      (should= cells-3d (generate-cells test-board/empty-3d-board)))
    )

  (context "mouse-click"

    (it "identifies which square was clicked on, if any"
      (should= {:x 288, :y 252, :z 1, :value 14} (find-clicked-cell test-board/empty-3d-board 288 234))
      (should-not (find-clicked-cell test-board/empty-3d-board 288 670)))
    (it "returns state unchanged if invalid play clicked"
      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
                                                    :active-player-index 0
                                                    :interface           :gui
                                                    :status              :in-progress
                                                    :x-type              :human
                                                    :o-type              :computer
                                                    :cells               cells-center-x-corner-o
                                                    :save                :mock})
            event          {:x (+ grid-origin-x (/ usable-screen 2)) ;space [1 1]
                            :y (+ grid-origin-y-2d (/ usable-screen 2))}]
        (should= starting-state (core/mouse-clicked starting-state event)))
      )

    (it "updates the state with the board and calls do-update when a human player clicks a valid square"
      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
                                                    :active-player-index 0
                                                    :interface           :gui
                                                    :status              :in-progress
                                                    :x-type              :human
                                                    :o-type              :computer
                                                    :save                :mock})
            event          {:x 96
                            :y 624}
            expected (test-core/state-create {:board               test-board/center-x-corner-xo-board
                                              :active-player-index 1
                                              :interface           :gui
                                              :status              :in-progress
                                              :x-type              :human
                                              :o-type              :computer
                                              :save                :mock})]
        (should= expected (core/mouse-clicked starting-state event)))
      )

    (it "does not update if active player is not human"
        (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
                                                      :active-player-index 0
                                                      :status              :in-progress
                                                      :interface           :gui
                                                      :x-type              :computer
                                                      :x-difficulty        :hard
                                                      :o-type              :computer
                                                      :o-difficulty        :hard
                                                      :cells               cells-center-x-corner-o
                                                      :save                :mock})
              event          {:x (+ grid-origin-x (/ (* 0.5 usable-screen) 3)) ;space [2 0]
                              :y (+ grid-origin-y-2d (/ (* 2.5 usable-screen) 3))}]
          (should= (:board starting-state) (:board (core/mouse-clicked starting-state event))))
        )

    (it "does update the board, evaluate it and change players if valid play is selected & player is human"
        (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
                                                      :active-player-index 0
                                                      :status              :in-progress
                                                      :interface           :gui
                                                      :x-type              :human
                                                      :o-type              :computer
                                                      :cells               cells-center-x-corner-o
                                                      :save                :mock})
              event          {:x (+ grid-origin-x (/ (* 0.5 usable-screen) 3)) ;space [2 0]
                              :y (+ grid-origin-y-2d (/ (* 2.5 usable-screen) 3))}
              new-state      (test-core/state-create {:board               test-board/center-x-corner-xo-board
                                                      :active-player-index 1
                                                      :interface           :gui
                                                      :status              :in-progress
                                                      :x-type              :human
                                                      :o-type              :computer
                                                      :cells               cells-center-x-corner-o
                                                      :save                :mock})]
          (should= new-state (dissoc (core/mouse-clicked starting-state event) :game-id))))
    )
  )

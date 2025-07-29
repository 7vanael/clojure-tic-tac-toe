(ns tic-tac-toe.gui.cells-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board_spec :as test-board]
            [tic-tac-toe.gui.cells :as sut]))

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

(describe "cells"

  (it "can tell if the board is 3d or not"
    (should= true (sut/board-3d? [[[1 2] [3 4]] [[5 6] [7 8]]]))
    (should= false (sut/board-3d? [[1 2 3] [4 5 6] [7 8 9]])))

  (it "generates maps for each cell containing the center point coordinates & value in a 2d grid"
      (should= [{:x 5, :y 5, :value 1} {:x 15, :y 5, :value 2}
                {:x 5, :y 15, :value 3} {:x 15, :y 15, :value 4}]
               (sut/generate-cells-2d [[1 2] [3 4]] 10 [0 0]))

      (should= [{:x 96, :y 240, :value 1} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
                {:x 96, :y 432, :value 4} {:x 288, :y 432, :value 5} {:x 480, :y 432, :value 6}
                {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}]
               (sut/generate-cells-2d test-board/empty-board 192 [0 144]))

      (should= [{:x 96, :y 240, :value 1} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
                {:x 96, :y 432, :value 4} {:x 288, :y 432, :value 5} {:x 480, :y 432, :value 6}
                {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}]
               (sut/generate-cells test-board/empty-board)))

    (it "generates maps for each cell containing the center point coordinates & value in a 3d grid"
      (should= [{:x 5, :y 5, :z 0, :value 1} {:x 15, :y 5, :z 0, :value 2}
                {:x 5, :y 15, :z 0, :value 3} {:x 15, :y 15, :z 0, :value 4}

                {:x 203, :y 5, :z 1, :value 5} {:x 213, :y 5, :z 1, :value 6}
                {:x 203, :y 15, :z 1, :value 7} {:x 213, :y 15, :z 1, :value 8}

                {:x 401, :y 5, :z 2, :value 9} {:x 411, :y 5, :z 2, :value 10}
                {:x 401, :y 15, :z 2, :value 11} {:x 411, :y 15, :z 2, :value 12}]
               ;dependent on the set screen size defined in gui-util, usable-screen =
               (sut/generate-cells-3d [[[1 2] [3 4]] [[5 6] [7 8]] [[9 10] [11 12]]] 10 [0 0]))

      (should= cells-3d (sut/generate-cells-3d test-board/empty-3d-board 60 [0 162])))

    (it "generates cells from the generate-cells method"
      (should= cells-3d (sut/generate-cells test-board/empty-3d-board)))

  (it "identifies which square was clicked on, if any"
    (should= {:x 288, :y 252, :z 1, :value 14} (sut/find-clicked-cell test-board/empty-3d-board 288 234))
    (should-not (sut/find-clicked-cell test-board/empty-3d-board 288 670)))

  )
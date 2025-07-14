(ns tic-tac-toe.gui.draw-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.draw :as sut]))

(describe "draw"
  (context "lines"
    (it "gets start & end points of a horizontal line for a grid"
      (should= [[0 0 10 0] [0 5 10 5] [0 10 10 10]] (sut/get-horizontal-lines 0 0 10 5 2))
      (should= [[0 144 576 144] [0 336 576 336] [0 528 576 528] [0 720 576 720]]
               (sut/get-horizontal-lines 144 0 576 192 3)))
    (it "gets start & end points of horizonal lines for a 4x grid"
      (should= [[0 144 576 144] [0 288 576 288] [0 432 576 432] [0 576 576 576] [0 720 576 720]]
               (sut/get-horizontal-lines 144 0 576 144 4)))
    (it "gets start & end points of horizonal lines for a 3x3x3 grid"
      (should= [[0 144 576 144] [0 288 576 288] [0 432 576 432] [0 576 576 576] [0 720 576 720]]
               (sut/get-horizontal-lines 144 0 576 144 4)))
    (it "gets start & end points of vertical lines for the grid"
      (should= [[0 0 0 10] [5 0 5 10] [10 0 10 10]] (sut/get-vertical-lines 0 10 5 2 0))
      (should= [[0 144 0 720] [192 144 192 720] [384 144 384 720] [576 144 576 720]]
               (sut/get-vertical-lines 144 720 192 3 0)))
    (it "gets start and end points of vertical lines for a 4x grid"
      (should= [[0 144 0 720] [144 144 144 720] [288 144 288 720] [432 144 432 720] [576 144 576 720]]
               (sut/get-vertical-lines 144 720 144 4 0)))
    (it "gets start and end points of vertical lines for a 3x3x3 grid"
      (should= [[0 144 0 720] [144 144 144 720] [288 144 288 720] [432 144 432 720] [576 144 576 720]]
               (sut/get-vertical-lines 144 720 144 4 0)))

    (it "gets 3 sets of horizontal and vertical lines for a 3x3x3 grid"
      (should= [[0 162 180 162] [0 222 180 222] [0 282 180 282] [0 342 180 342]
                [0 162 0 342] [60 162 60 342] [120 162 120 342] [180 162 180 342]

                [198 162 378 162] [198 222 378 222] [198 282 378 282] [198 342 378 342]
                [198 162 198 342] [258 162 258 342] [318 162 318 342] [378 162 378 342]

                [396 162 576 162] [396 222 576 222] [396 282 576 282] [396 342 576 342]
                [396 162 396 342] [456 162 456 342] [516 162 516 342] [576 162 576 342]]
               (sut/get-lines-3d 3)))
    ))
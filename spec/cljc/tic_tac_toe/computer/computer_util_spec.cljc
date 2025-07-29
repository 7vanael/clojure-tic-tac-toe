(ns tic-tac-toe.computer.computer-util-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer.computer-utilc :as sut]))

(def board-o-could-win
  [["X" 2 "X"]
   ["O" "X" "X"]
   [7 "O" "O"]])

(def not-full-3d
  [[[1 "O" "O"]
    ["O" "X" 6]
    ["X" "O" "O"]]
   [[10 11 "X"]
    ["O" "X" "O"]
    ["O" "O" "X"]]
   [["X" "O" "O"]
    ["X" "O" "X"]
    [25 "X" "O"]]])

(describe "computer-util"

          (it "gets the possible moves"
              (should= [[0 1] [2 0]] (sut/get-possible-moves board-o-could-win)))
          (it "gets the possible moves of a 3x3x3"
              (should= [[0 0 0] [0 1 2] [1 0 0] [1 0 1] [2 2 0]]
                       (sut/get-possible-moves not-full-3d)))
          )

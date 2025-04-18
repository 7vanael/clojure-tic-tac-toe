(ns tic-tac-toe.computer-util-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer-util :as sut]))

(def board-o-could-win
  [["X" 2 "X"]
   ["O" "X" "X"]
   [7 "O" "O"]])

(describe "computer-util"

  (it "gets the possible moves"
    (should= [[0 1] [2 0]] (sut/get-possible-moves board-o-could-win)))
  )
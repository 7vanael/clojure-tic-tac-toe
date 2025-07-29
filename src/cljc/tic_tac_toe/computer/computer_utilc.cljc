(ns tic-tac-toe.computer.computer-utilc
  (:require [tic-tac-toe.board :as board]))

(defn get-possible-moves [board]
  (if (= 2 (board/get-board-complexity board))
    (for [x (range (count board))
          y (range (count board))
          :when (board/available? board [x y])]
      [x y])
    (for [z (range (count board))
          x (range (count board))
          y (range (count board))
          :when (board/available? board [z x y])]
      [z x y])))
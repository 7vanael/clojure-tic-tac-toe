(ns tic-tac-toe.computer.computer-util
  (:require [tic-tac-toe.board :as board]))

(defn get-possible-moves [board]
  (for [x (range (count board))
        y (range (count board))
        :when (board/available? board [x y])]
    [x y]))
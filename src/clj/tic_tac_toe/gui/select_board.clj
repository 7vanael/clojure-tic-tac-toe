(ns tic-tac-toe.gui.select-board
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn board3-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-3-rect))
(defn board4-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-3-rect))
(defn board333-clicked? [x y] (util/button-clicked? [x y] util/opt3-of-3-rect))

(defmethod core/mouse-clicked :select-board [state {:keys [x y]}]
  (cond (board3-clicked? x y) (core/select-board (assoc state :response 3))
        (board4-clicked? x y) (core/select-board (assoc state :response 4))
        (board333-clicked? x y) (core/select-board (assoc state :response [3 3 3]))
        :else state))
(ns tic-tac-toe.gui.select-board
  (:require [quil.core :as q]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-util :as util]))

(def type-labels ["3 X 3" "4 X 4"])

(defmethod multis/draw-state :select-board [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-size 2) util/title-offset-y)
  (util/draw-type-buttons type-labels))

(defmethod multis/update-state :select-board [state]
  state)

(defmethod multis/mouse-clicked :select-board [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc-in [:board-size] 3)
            (assoc :status :initialize-board))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc-in [:board-size] 4)
            (assoc :status :initialize-board))
        :else state))
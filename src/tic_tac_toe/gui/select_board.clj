(ns tic-tac-toe.gui.select-board
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["3 X 3" "4 X 4" "3 X 3 X 3"])

(defmethod core/draw-state [:gui :select-board] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-3-buttons type-labels))

(defmethod core/get-selection :select-board [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect) 1
        (util/button-clicked? [x y] util/opt2-of-3-rect) 2
        (util/button-clicked? [x y] util/opt3-of-3-rect) 3
        :else nil))
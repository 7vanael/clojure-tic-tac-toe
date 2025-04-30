(ns tic-tac-toe.gui.draw
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]))

(def type-labels ["Play Again" "Exit"])

(defmethod multis/draw-state :draw [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "It's a draw, good game!" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod multis/update-state :draw [state]
  state)

(defmethod multis/mouse-clicked :draw [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) (assoc-in util/initial-state [:status] :config-x-type)
        (util/button-clicked? [x y] util/opt2-of-2-rect) (q/exit)
        :else state))

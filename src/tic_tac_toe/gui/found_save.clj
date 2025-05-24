(ns tic-tac-toe.gui.found-save
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["Yes" "No"])

(defmethod multis/draw-state :found-save [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Would you like to resume your last game?" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :found-save] [state]
  state)

(defmethod multis/mouse-clicked :found-save [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) (assoc state :status :in-progress)
        (util/button-clicked? [x y] util/opt2-of-2-rect) (assoc (util/initial-state (:save state)) :status :config-x-type)
        :else state))
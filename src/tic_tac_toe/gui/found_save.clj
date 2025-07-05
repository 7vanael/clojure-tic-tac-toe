(ns tic-tac-toe.gui.found-save
  (:require [quil.core :as q]

            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["Yes" "No"])

(defmethod core/draw-state [:gui :found-save] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Would you like to resume your last game?" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :found-save] [state value]
  (cond (= 1 value) (assoc state :status :in-progress)
        (= 2 value) (assoc (core/initial-state (:interface state) (:save state)) :status :config-x-type :mouse-click true)
        :else state))

(defmethod core/mouse-clicked :found-save [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) (core/update-state state 1)
        (util/button-clicked? [x y] util/opt2-of-2-rect) (core/update-state state 2)
        :else nil))
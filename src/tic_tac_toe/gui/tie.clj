(ns tic-tac-toe.gui.tie
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core])
  (:import (java.io FileNotFoundException)))

(def type-labels ["Play Again" "Exit"])

(defmethod multis/draw-state :tie [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "It's a draw, good game!" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :tie] [state]
  (try
    (core/delete-save state)
    (catch FileNotFoundException _))
  state)

(defmethod multis/mouse-clicked :tie [{:keys [save] :as state} {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) (assoc (util/initial-state save) :status :config-x-type)
        (util/button-clicked? [x y] util/opt2-of-2-rect) (q/exit)
        :else state))

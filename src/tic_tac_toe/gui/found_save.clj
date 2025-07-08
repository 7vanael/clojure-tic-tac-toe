(ns tic-tac-toe.gui.found-save
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn yes-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn no-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))

(defmethod core/mouse-clicked :found-save [state {:keys [x y]}]
  (cond (yes-clicked? x y) (assoc state :status :in-progress)
        (no-clicked? x y) (assoc (core/initial-state (select-keys state [:interface :save])) :status :config-x-type)
        :else state))
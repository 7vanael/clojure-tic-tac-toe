(ns tic-tac-toe.gui.tie
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core])
  (:import (java.io FileNotFoundException)))


(defn yes-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn no-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))

(defmethod core/mouse-clicked :tie [state {:keys [x y]}]
  (try
    (core/delete-save state)
    (catch FileNotFoundException _))
  (cond (yes-clicked? x y) (assoc (core/initial-state {:save (:save state) :interface (:interface state)}) :status :config-x-type)
        (no-clicked? x y) (assoc state :status :exit)
        :else state))

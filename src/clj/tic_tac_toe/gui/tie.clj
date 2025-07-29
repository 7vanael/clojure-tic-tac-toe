(ns tic-tac-toe.gui.tie
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn yes-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn no-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))

(defmethod core/mouse-clicked :tie [state {:keys [x y]}]
  (cond (yes-clicked? x y) (core/maybe-play-again (assoc state :response true))
        (no-clicked? x y) (System/exit 0) ;(assoc state :status :exit)?
        :else state))

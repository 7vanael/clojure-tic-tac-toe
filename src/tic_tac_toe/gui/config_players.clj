(ns tic-tac-toe.gui.config-players
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn human-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn computer-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))
(defn easy-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-3-rect))
(defn medium-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-3-rect))
(defn hard-clicked? [x y] (util/button-clicked? [x y] util/opt3-of-3-rect))

(defmethod core/mouse-clicked :config-x-type [state {:keys [x y]}]
  (cond (human-clicked? x y) (core/config-x-type (assoc state :response :human))
        (computer-clicked? x y) (core/config-x-type (assoc state :response :computer))
        :else state))

(defmethod core/mouse-clicked :config-x-difficulty [state {:keys [x y]}]
  (cond (easy-clicked? x y) (core/config-x-difficulty (assoc state :response :easy))
        (medium-clicked? x y) (core/config-x-difficulty (assoc state :response :medium))
        (hard-clicked? x y) (core/config-x-difficulty (assoc state :response  :hard))
        :else state))

(defmethod core/mouse-clicked :config-o-type [state {:keys [x y]}]
  (cond (human-clicked? x y) (core/config-o-type (assoc state :response :human))
        (computer-clicked? x y) (core/config-o-type (assoc state :response :computer))
        :else state))

(defmethod core/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (cond (easy-clicked? x y) (core/config-o-difficulty (assoc state :response :easy))
        (medium-clicked? x y) (core/config-o-difficulty (assoc state :response :medium))
        (hard-clicked? x y) (core/config-o-difficulty (assoc state :response  :hard))
        :else state))
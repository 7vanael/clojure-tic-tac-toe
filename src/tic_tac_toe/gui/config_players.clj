(ns tic-tac-toe.gui.config-players
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn human-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn computer-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))
(defn easy-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-3-rect))
(defn medium-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-3-rect))
(defn hard-clicked? [x y] (util/button-clicked? [x y] util/opt3-of-3-rect))

(defn configure-type [state x y]
  (cond (human-clicked? x y) (core/update-state state :human)
        (computer-clicked? x y) (core/update-state state :computer)
        :else state))

(defn configure-difficulty [state x y]
  (cond (easy-clicked? x y) (core/update-state state :easy)
        (medium-clicked? x y) (core/update-state state :medium)
        (hard-clicked? x y) (core/update-state state :hard)
        :else state))

(defmethod core/mouse-clicked :config-x-type [state {:keys [x y]}]
  (configure-type state x y))

(defmethod core/mouse-clicked :config-x-difficulty [state {:keys [x y]}]
  (configure-difficulty state x y))

(defmethod core/mouse-clicked :config-o-type [state {:keys [x y]}]
  (configure-type state x y))

(defmethod core/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (configure-difficulty state x y))
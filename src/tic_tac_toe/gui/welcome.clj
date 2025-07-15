(ns tic-tac-toe.gui.welcome
  (:require [tic-tac-toe.core :as core]))

(defmethod core/mouse-clicked :welcome [state _]
  (core/update-state state 1))
(ns tic-tac-toe.gui.welcome
  (:require [tic-tac-toe.core :as core]))

(defmethod core/mouse-clicked :welcome [state _]
  (core/maybe-load-save state))
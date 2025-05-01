(ns tic-tac-toe.gui.gui_core
  (:require [quil.core :as q]))

(defmulti draw-state :status)

(defmulti update-state :status)

(defmulti mouse-clicked (fn [state & _] (:status state)))


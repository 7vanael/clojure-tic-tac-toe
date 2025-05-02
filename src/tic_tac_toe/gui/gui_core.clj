(ns tic-tac-toe.gui.gui_core)

(defmulti draw-state :status)
(defmulti mouse-clicked (fn [state & _] (:status state)))
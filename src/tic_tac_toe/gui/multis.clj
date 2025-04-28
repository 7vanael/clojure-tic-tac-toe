(ns tic-tac-toe.gui.multis)


;defmulti here? route to different draw functions
;based on status?
#_(defmulti draw-state :interface)

(defmulti draw-state :status)

(defmulti update-state :status)

(defmulti mouse-clicked (fn [state & _] (:status state)))
(ns tic-tac-toe.multis)


;defmulti here? route to different draw functions
;based on status?
(defmulti draw-state :interface)
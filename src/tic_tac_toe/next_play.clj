(ns tic-tac-toe.next-play)

(defmulti get-next-play (fn [state & _] (:interface state)))

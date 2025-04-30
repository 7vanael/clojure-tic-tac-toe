(ns tic-tac-toe.common
  (:require [tic-tac-toe.board :as board]))


(defn change-player [state]
  (assoc state :active-player-index
               (if (= (:active-player-index state) 0)
                 1 0)))
(ns tic-tac-toe.computer.easy
  (:require [tic-tac-toe.computer.computer-utilc :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn easy [{:keys [board active-player-index players] :as state}]
  (let [move     (first (shuffle (util/get-possible-moves board)))
        char      (get-in players [active-player-index :character])]
    (assoc state :board (board/take-square board move char))))

(defmethod core/take-computer-turn :easy [state]
  (easy state))
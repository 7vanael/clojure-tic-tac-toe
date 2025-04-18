(ns tic-tac-toe.easy
  (:require [tic-tac-toe.computer-util :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.turn :as turn]))

(defn easy [{:keys [board active-player-index players] :as state}]
  (let [move     (first (shuffle (util/get-possible-moves board)))
        char      (get-in players [active-player-index :character])]
    (assoc state :board (board/take-square board move char))))

(defmethod turn/take-turn :easy [state]
  (easy state))
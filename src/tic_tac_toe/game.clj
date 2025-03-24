(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.console :as console]))

(defn initialize-state []
  {:board  (board/new-board)
   :player "O"
   :status "in-progress"})

(defn change-player [state]
  (assoc state :player (if (= (get state :player) "X")
                         (console/announce-player "O")
                         (console/announce-player "X"))))

(defn evaluate-board [state]
  (cond (board/winner? (get state :board) (get state :player)) (assoc state :status "winner")
        (not (board/any-space-available? (get state :board))) (assoc state :status "draw")
        :else state))

(defn take-turn [state]
  (let [next-play (console/get-next-play)]
    (if (board/available? (get state :board) next-play)
      (assoc state :board (board/take-square (get state :board) next-play (get state :player)))
      (recur (console/occupied state)))))

(defn play [state]
  (console/display-board (get state :board))
  (cond (= (get state :status) "draw") (console/draw)
        (= (get state :status) "winner") (console/announce-winner (get state :player))
        :else (recur (evaluate-board (take-turn (change-player state))))))

(defn start []
  (console/welcome)
  (play (initialize-state)))
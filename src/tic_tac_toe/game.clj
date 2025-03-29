(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.computer :as computer]))



(defn change-player [state]
  (assoc state :active-player-index (if (= (:active-player-index state) 0)
                         (do (console/announce-player "O") 1)
                         (do (console/announce-player "X") 0))))

(defn evaluate-board [state]
  (cond (board/winner? (get state :board) (get-in state [:players (:active-player-index state) :character])) (assoc state :status "winner")
        (not (board/any-space-available? (:board state))) (assoc state :status "draw")
        :else state))

(defn human-turn [state]
  (let [next-play (console/get-next-play)]
    (if (board/available? (:board state) next-play)
      (assoc state :board (board/take-square (:board state) next-play (get-in state [:players (:active-player-index state) :character])))
      (do (console/occupied) (recur state)))))

(defn computer-turn [state]
  (computer/turn state))

(defn take-turn [state]
  (if (= :human (get-in state [:players (:active-player-index state) :play-type]))
    (human-turn state)
    (computer-turn state)))

(defn play [state]
  (console/display-board (get state :board))
  (cond (= (get state :status) "draw") (console/draw)
        (= (get state :status) "winner") (console/announce-winner (:active-player-index state))
        :else (recur (evaluate-board (take-turn (change-player state))))))

(defn start [state]
  (console/welcome)
  (play state))
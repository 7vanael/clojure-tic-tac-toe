(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.console :as console]))



(defn change-player [state]
  (assoc state :active-player-index (if (= (get state :active-player-index) 0)
                         (do (console/announce-player "O") 1)
                         (do (console/announce-player "X") 0))))

(defn evaluate-board [state]
  (cond (board/winner? (get state :board) (get-in state [:players (get state :active-player-index) :character])) (assoc state :status "winner")
        (not (board/any-space-available? (get state :board))) (assoc state :status "draw")
        :else state))

(defn human-turn [state]
  (let [next-play (console/get-next-play)]
    (if (board/available? (get state :board) next-play)
      (assoc state :board (board/take-square (get state :board) next-play (get-in state [:players (get state :active-player-index) :character])))
      (do (console/occupied) (recur state)))))

(defn computer-turn [state]
  state)

(defn take-turn [state]
  (if (= :human (get-in state [:players (get state :active-player-index) :play-type]))
    (human-turn state)
    (computer-turn state)))

(defn play [state]
  (console/display-board (get state :board))
  (cond (= (get state :status) "draw") (console/draw)
        (= (get state :status) "winner") (console/announce-winner (get state :active-player-index))
        :else (recur (evaluate-board (take-turn (change-player state))))))

(defn start [state]
  (console/welcome)
  (play state))
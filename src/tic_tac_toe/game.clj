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
  (let [play-options          (board/play-options (:board state))
        next-play             (console/get-next-play play-options)
        next-play-coordinates (board/space->coordinates next-play (:board state))]
    (assoc state :board (board/take-square (:board state) next-play-coordinates (get-in state [:players (:active-player-index state) :character])))))

(defn take-turn [state]
  (let [active-type (get-in state [:players (:active-player-index state) :play-type])]
    (if (= :human active-type)
      (human-turn state)
      (computer/turn state))))

(defn play [state]
  (console/display-board (get state :board))
  (cond (= (:status state) "draw") (console/draw)
        (= (:status state) "winner") (console/announce-winner (:active-player-index state))
        :else (recur (evaluate-board (take-turn (change-player state))))))

(defn start [state]
  (console/welcome)
  (play state))
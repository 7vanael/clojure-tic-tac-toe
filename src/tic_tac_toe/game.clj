(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.easy]
            [tic-tac-toe.medium]
            [tic-tac-toe.computer]
            [tic-tac-toe.human]))

(defn change-player [state]
  (assoc state :active-player-index (if (= (:active-player-index state) 0)
                                      (do (console/announce-player "O") 1)
                                      (do (console/announce-player "X") 0))))

(defn evaluate-board [{:keys [board active-player-index players] :as state}]
  (cond (board/winner? board (get-in players [active-player-index :character])) (assoc state :status "winner")
        (not (board/any-space-available? board)) (assoc state :status "draw")
        :else state))


(defn play [{:keys [board status active-player-index players] :as state}]
  (console/display-board board)
  (cond (= status "draw") (console/draw)
        (= status "winner") (console/announce-winner (get-in players [active-player-index :character]))
        :else (recur (evaluate-board (turn/take-turn (change-player state))))))

(defn start [state]
  (console/welcome)
  (play state))
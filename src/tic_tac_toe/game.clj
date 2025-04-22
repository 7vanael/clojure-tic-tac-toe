(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.user-prompt :as user-prompt]
            [tic-tac-toe.easy]
            [tic-tac-toe.medium]
            [tic-tac-toe.hard]
            [tic-tac-toe.human]))

(defn change-player [state]
  (assoc state :active-player-index (if (= (:active-player-index state) 0)
                                      (do (user-prompt/announce-player state "O") 1)
                                      (do (user-prompt/announce-player state "X") 0))))

(defn evaluate-board [{:keys [board active-player-index players] :as state}]
  (cond (board/winner? board (get-in players [active-player-index :character])) (assoc state :status "winner")
        (not (board/any-space-available? board)) (assoc state :status "draw")
        :else state))


(defn play [{:keys [board status active-player-index players] :as state}]
  (user-prompt/display-board state board)
  (cond (= status "draw") (user-prompt/announce-draw state)
        (= status "winner") (user-prompt/announce-winner state (get-in players [active-player-index :character]))
        :else (recur (evaluate-board (turn/take-turn (change-player state))))))

(defn start [state]
  (user-prompt/welcome-message state)
  (play state))
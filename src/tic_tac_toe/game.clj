(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.computer.hard]))


#_(defn play [{:keys [board status active-player-index players] :as state}]
  (core/display-board state board)
  (cond (= status :tie) (core/announce-draw state)
        (= status :winner) (core/announce-winner state (get-in players [active-player-index :character]))
        :else (recur (board/evaluate-board (core/take-turn (core/change-player state))))))

(defn play [state]
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player))


#_(defn start [state]
  (play state))
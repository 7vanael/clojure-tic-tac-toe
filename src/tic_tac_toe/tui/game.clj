(ns tic-tac-toe.tui.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.tui.human]
            [tic-tac-toe.common :as common]))


(defn play [{:keys [board status active-player-index players] :as state}]
  (core/display-board state board)
  (cond (= status :tie) (core/announce-draw state)
        (= status :winner) (core/announce-winner state (get-in players [active-player-index :character]))
        :else (recur (board/evaluate-board (core/take-turn (common/change-player state))))))


(defn start [state]
  (core/welcome-message state)
  (play state))
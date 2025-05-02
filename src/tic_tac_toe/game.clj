(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.tui.human]))


(defn play [state]
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player
      core/save-game))


#_(defn start [state]
  (play state))
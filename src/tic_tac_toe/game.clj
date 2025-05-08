(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.tui.in-progress]
            [tic-tac-toe.persistence :as persistence]))


(defmethod core/update-state :default [state]
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player
      persistence/save-game))


#_(defn start-game [state]
  (let [saved-game      (persistence/load-game)
        game (if saved-game (assoc saved-game :status :found-save) (core/initialize-state state))]
    (loop [game game]
      (recur (core/update-state game)))))
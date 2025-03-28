(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.board :as board]))

(defn initialize-state [type-x type-o]
  {:board               (board/new-board)
   :active-player-index 1
   :status              "in-progress"
   :players [{:character "X" :play-type type-x}
             {:character "O" :play-type type-o}]})


(defn -main []
  (game/start (initialize-state :human :human)))
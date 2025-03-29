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

(def player-options
  [:human :computer])

(defn -main []
  (console/welcome)
  (let [type-x (console/get-player-type "X" player-options)
        type-o (console/get-player-type "O" player-options)]
    (game/start (initialize-state type-x type-o))))
(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.board :as board]))

(defn initialize-state [type-x type-o difficulty-x difficulty-0 board-size]
  {:board               (board/new-board board-size)
   :active-player-index 1
   :status              "in-progress"
   :players [{:character "X" :play-type type-x :difficulty difficulty-x}
             {:character "O" :play-type type-o :difficulty difficulty-0}]})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(defn get-player-config [char]
  (let [play-type (console/get-player-type char player-options)
        difficulty (if (= play-type :computer) (console/get-difficulty char difficulty-options))]
    {:play-type play-type :difficulty difficulty}))

(defn -main []
  (console/welcome)
  (let [player-x (get-player-config "X")
        player-o (get-player-config "O")
        board-size (console/get-board-size [3 4])]
    (game/start (initialize-state (:play-type player-x) (:play-type player-o)(:difficulty player-x) (:difficulty player-o) board-size)))
  (if (console/play-again?) (-main)))
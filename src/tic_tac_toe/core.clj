(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.gui :as gui]))

(defn initialize-state [type-x type-o difficulty-x difficulty-0 board-size]
  {:board               (board/new-board board-size)
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                         {:character "O" :play-type type-o :difficulty difficulty-0}]})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(defn get-player-config [char]
  (let [play-type  (console/get-player-type char player-options)
        difficulty (if (= play-type :computer) (console/get-difficulty char difficulty-options))]
    {:play-type play-type :difficulty difficulty}))

(defn launch-cli []
  (console/welcome)
  (let [player-x     (get-player-config "X")
        type-x       (:play-type player-x)
        difficulty-x (:difficulty player-x)
        player-o     (get-player-config "O")
        type-o       (:play-type player-o)
        difficulty-o (:difficulty player-o)
        board-size   (console/get-board-size [3 4])]
    (game/start (initialize-state type-x type-o difficulty-x difficulty-o board-size)))
  (if (console/play-again?) (recur)))

(defn launch-quil []
  (gui/create-sketch))

(defn -main [& args]
  (let [interface-type (first args)]
    (cond (= interface-type "tui") (launch-cli)
          (= interface-type "gui") (launch-quil)
          :else (println "Please specify either 'tui' for a text interface, or 'gui' for graphical"))))
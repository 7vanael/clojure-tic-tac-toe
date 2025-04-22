(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.gui :as gui]))

(defn initialize-state [{:keys [type-x type-o difficulty-x difficulty-o board-size interface]}]
  {:interface interface
   :board               (board/new-board board-size)
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                         {:character "O" :play-type type-o :difficulty difficulty-o}]})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(defn get-player-config [char]
  (let [play-type  (console/get-player-type char player-options)
        difficulty (if (= play-type :computer) (console/get-difficulty char difficulty-options))]
    {:play-type play-type :difficulty difficulty}))

(defmulti start-game :interface)

(defmethod start-game :tui [_]
  (console/welcome)
  (let [player-x      (get-player-config "X")
        type-x        (:play-type player-x)
        difficulty-x  (:difficulty player-x)
        player-o      (get-player-config "O")
        type-o        (:play-type player-o)
        difficulty-o  (:difficulty player-o)
        board-size    (console/get-board-size [3 4])
        configuration {:type-x     type-x :type-o type-o :difficulty-x difficulty-x :difficulty-o difficulty-o
                       :board-size board-size :interface :tui}]
    (game/start (initialize-state configuration)))
  (if (console/play-again?) (recur :tui)))

(defmethod start-game :gui [_] (gui/create-sketch))

(defn -main [& args]
  (let [interface-type (first args)]
    (start-game {:interface (keyword interface-type)})))

(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.user-prompt :as user-prompt]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.gui.gui :as gui]
            [tic-tac-toe.gui.multis]))

(defn initialize-state [{:keys [type-x type-o difficulty-x difficulty-o board-size interface]}]
  {:interface           interface
   :board               (board/new-board board-size)
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                         {:character "O" :play-type type-o :difficulty difficulty-o}]})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(defmulti start-game :interface)

(defmethod start-game :tui [state]
  (user-prompt/welcome-message state)
  (let [player-x      (user-prompt/get-player-type state "X" player-options)
        difficulty-x  (when (= :computer player-x) (user-prompt/get-difficulty state "X" difficulty-options))
        player-o      (user-prompt/get-player-type state "O" player-options)
        difficulty-o  (when (= :computer player-o) (user-prompt/get-difficulty state "O" difficulty-options))
        board-size    (user-prompt/get-board-size state [3 4])
        configuration {:type-x     player-x :type-o player-o :difficulty-x difficulty-x :difficulty-o difficulty-o
                       :board-size board-size :interface :tui}]
    (game/start (initialize-state configuration))
  (when (user-prompt/play-again? state) (recur state))))

(defmethod start-game :gui [state] (gui/create-sketch state))

(defn -main [& args]
  (let [interface-type (keyword (first args))
        initial-state {:status :config :interface interface-type}
        default-initial-state {:status :config :interface :tui}]
    (if (contains? #{:gui :tui} interface-type)
      (start-game initial-state)
      (start-game default-initial-state))))
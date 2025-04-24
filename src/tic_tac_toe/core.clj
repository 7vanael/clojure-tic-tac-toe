(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.user-prompt :as user-prompt]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.gui :as gui]
            [tic-tac-toe.multis]))

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

(defn get-player-config [interface char]
  (let [play-type  (user-prompt/get-player-type interface char player-options)
        difficulty (when (= play-type :computer) (user-prompt/get-difficulty interface char difficulty-options))]
    {:play-type play-type :difficulty difficulty}))

(defmulti start-game :interface)

(defmethod start-game :tui [state]
  (println state)
  (user-prompt/welcome-message state)
  (let [interface-tag {:interface :tui}
        player-x      (user-prompt/get-player-type state "X" player-options)
        difficulty-x  (when (= :computer player-x) (user-prompt/get-difficulty state "X" difficulty-options))
        player-o      (get-player-config state "O")
        type-o        (:play-type player-o)
        difficulty-o  (:difficulty player-o)
        board-size    (user-prompt/get-board-size interface-tag [3 4])
        configuration {:type-x     player-x :type-o type-o :difficulty-x difficulty-x :difficulty-o difficulty-o
                       :board-size board-size :interface :tui}]
    (game/start (initialize-state (merge interface-tag configuration)))
  (when (user-prompt/play-again? interface-tag) (recur interface-tag))))

(defmethod start-game :gui [state] (gui/create-sketch state))

(defn -main [& args]
  (let [interface-type (keyword (first args))]
    (if (contains? #{:tui :gui} interface-type)
      (start-game {:status :config :interface interface-type})
      (println "Please use 'lein run tui' to use a text interface, or 'lein run gui' to use a graphical one"))))

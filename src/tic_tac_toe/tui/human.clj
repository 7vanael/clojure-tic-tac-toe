(ns tic-tac-toe.tui.human
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defn human-turn-tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char)))))

(defmethod core/take-human-turn :tui [{:keys [board] :as state}]
  (console/display-board board)
  (-> state
      console/announce-player
      human-turn-tui
      board/evaluate-board
      core/change-player
      core/save-game
      core/update-state))

(defmethod core/update-state [:tui :in-progress] [state]
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player
      core/save-game))

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

(defmethod core/start-game :tui [state]
  (console/welcome-message)
  (let [player-x      (console/get-player-type "X" player-options)
        difficulty-x  (when (= :computer player-x) (console/get-difficulty "X" difficulty-options))
        player-o      (console/get-player-type "O" player-options)
        difficulty-o  (when (= :computer player-o) (console/get-difficulty "O" difficulty-options))
        board-size    (console/get-board-size [3 4])
        configuration {:type-x     player-x :type-o player-o :difficulty-x difficulty-x :difficulty-o difficulty-o
                       :board-size board-size :interface :tui}]
    (core/update-state (initialize-state configuration))
    (when (console/play-again?) (recur state))))


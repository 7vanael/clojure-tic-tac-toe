(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.persistence :as persistence]))

(defn human-turn-tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char)))))

(defmethod core/take-human-turn :tui [{:keys [board] :as state}]
  (console/display-board board)
  (let [new-state (human-turn-tui state)]
    new-state))

(defmethod core/update-state [:tui :in-progress] [state]
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player
      persistence/save-game
      core/update-state))

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defn initialize-state []
  (let [type-x      (console/get-player-type "X" player-options)
        difficulty-x  (when (= :computer type-x) (console/get-difficulty "X" difficulty-options))
        type-o      (console/get-player-type "O" player-options)
        difficulty-o  (when (= :computer type-o) (console/get-difficulty "O" difficulty-options))
        board-size    (console/get-board-size board-options)]
  {:interface           :tui
   :board               (board/new-board board-size)
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                         {:character "O" :play-type type-o :difficulty difficulty-o}]}))

(defmethod core/update-state [:tui :found-save] [state]
      (if (console/resume?)
      (core/update-state (assoc state :status :in-progress))
      (core/update-state (initialize-state))))

(defmethod core/start-game :tui [_]
  (console/welcome-message)
  (let [saved-game      (persistence/load-game)]
    (if (nil? saved-game)
      (core/update-state (initialize-state))
      (core/update-state (assoc saved-game :status :found-save :interface :tui)))))

(defmethod core/update-state [:tui :game-over] [_]
  (persistence/delete-save)
  (if (console/play-again?)
    (core/update-state (initialize-state))
    (System/exit 0)))
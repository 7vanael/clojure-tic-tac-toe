(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/take-human-turn :tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char           (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char)))))


(defmethod core/update-state [:tui :in-progress] [state]
  (console/display-board (:board state))
  (-> state
      core/take-turn
      board/evaluate-board
      core/change-player
      core/save-game
      core/update-state))

(defn game-loop [state]
  (loop [current-state state]
    (when (:board current-state)
      (console/display-board (:board current-state)))
    (let [next-state (core/update-state current-state)]
      (if (= :game-over (:status next-state))
        (do (console/display-board (:board next-state))
            next-state)
        (recur next-state)))))

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defn initialize-state [save]
  (let [type-x       (console/get-player-type "X" player-options)
        difficulty-x (when (= :computer type-x) (console/get-difficulty "X" difficulty-options))
        type-o       (console/get-player-type "O" player-options)
        difficulty-o (when (= :computer type-o) (console/get-difficulty "O" difficulty-options))
        board-size   (console/get-board-size board-options)]
    {:interface           :tui
     :board               (board/new-board board-size)
     :active-player-index 0
     :status              :in-progress
     :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                           {:character "O" :play-type type-o :difficulty difficulty-o}]
     :save                save}))

(defmethod core/update-state [:tui :found-save] [state]
  (if (console/resume?)
    (assoc state :status :in-progress)
    (initialize-state (:save state))))

(defmethod core/start-game :tui [state]
  (console/welcome-message)
  (let [starting-state (if-let [saved-game (core/load-game state)]
                         (core/update-state (assoc saved-game :status :found-save :interface :tui))
                         (initialize-state (:save state)))
        ending-state   (game-loop starting-state)]
    (core/delete-save state)
    (when (console/play-again?)
      (core/start-game {:interface :tui :save (:save state)}))
    (console/exit-message)))

(defmethod core/update-state [:tui :game-over] [state]
  state)
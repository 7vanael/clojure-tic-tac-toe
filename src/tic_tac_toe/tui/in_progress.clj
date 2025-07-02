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
  (core/do-update! state))

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

(defmethod core/update-state [:tui :select-board] [state]
  (let [board-size  (console/get-board-size board-options)
        next-status :ready
        new-state   (assoc state :board (board/new-board board-size))]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-o-difficulty] [state]
  (let [difficulty-o (console/get-difficulty "O" difficulty-options)
        next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty-o)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-x-difficulty] [state]
  (let [difficulty-x (console/get-difficulty "X" difficulty-options)
        next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty-x)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-o-type] [state]
  (let [type-o      (console/get-player-type "X" player-options)
        next-status (if (= type-o :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] type-o)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-x-type] [state]
  (let [type-x      (console/get-player-type "X" player-options)
        next-status (if (= type-x :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] type-x)]
    (assoc new-state :status next-status)))

(defn build-state [state]
  (loop [state state]
    (if (= (:status state) :ready)
      (assoc state :status :in-progress)
      (recur (core/update-state state)))))

(defn initialize-state [save]
  (build-state (assoc (core/initial-state :tui save) :status :config-x-type)))

(defmethod core/update-state [:tui :found-save] [state]
  (if (console/resume?)
    (assoc state :status :in-progress)
    (initialize-state (:save state))))

(defmethod core/start-game :tui [state]
  (console/welcome-message)
  (let [starting-state (if-let [saved-game (core/load-game state)]
                         (core/update-state (assoc saved-game :status :found-save :interface :tui))
                         (initialize-state (:save state)))
        _              (game-loop starting-state)]
    (core/delete-save state)
    (when (console/play-again?)
      (core/start-game {:interface :tui :save (:save state)}))
    (console/exit-message)))

(defmethod core/update-state [:tui :game-over] [state]
  state)
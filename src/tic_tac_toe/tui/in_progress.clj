(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/get-selection [:tui :winner] [_]
  (console/yes-or-no?))
(defmethod core/get-selection [:tui :tie] [_]
  (console/yes-or-no?))
(defmethod core/get-selection [:tui :in-progress] [{:keys [board] :as state}]
  (let [play-options (board/play-options board)]
    (console/get-next-play state play-options)))
(defmethod core/get-selection [:tui :select-board] [_]
  (console/get-board-size core/board-options))
(defmethod core/get-selection [:tui :config-o-difficulty] [_]
  (let [selection (console/get-selection "O" core/difficulty-options)]
    (case selection
      :easy 1
      :medium 2
      :hard 3
      nil)))
(defmethod core/get-selection [:tui :config-x-difficulty] [_]
  (let [selection (console/get-selection "X" core/difficulty-options)]
    (case selection
      :easy 1
      :medium 2
      :hard 3
      nil)))
(defmethod core/get-selection [:tui :config-o-type] [_]
  (if (= :human (console/get-selection "O" core/player-options)) 1 2))
(defmethod core/get-selection [:tui :config-x-type] [_]
  (if (= :human (console/get-selection "X" core/player-options)) 1 2))
(defmethod core/get-selection [:tui :found-save] [_]
  (console/yes-or-no?))
(defmethod core/get-selection [:tui :welcome] [_] 1)

(defmethod core/take-human-turn :tui [{:keys [board] :as state}]
  (let [play-options (board/play-options board)
        next-play    (console/get-next-play state play-options)]
    (core/do-take-human-turn state next-play)))


(defmethod core/update-state [:tui :winner] [{:keys [active-player-index players] :as state}]
  (let [character (get-in players [active-player-index :character])]
    (core/delete-save state)
    (console/announce-winner character)
    (assoc state :status :game-over)))


(defmethod core/update-state [:tui :tie] [state]
  (core/delete-save state)
  (console/announce-draw)
  (assoc state :status :game-over))

(defmethod core/update-state [:tui :in-progress] [state]
  (console/display-board (:board state))
  (core/do-update! state))

(defmethod core/update-state [:tui :select-board] [state]
  (let [board-size  (console/get-board-size core/board-options)
        next-status :ready
        new-state   (assoc state :board (board/new-board board-size))]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-o-difficulty] [state]
  (let [difficulty-o (console/get-difficulty "O" core/difficulty-options)
        next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty-o)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-x-difficulty] [state]
  (let [difficulty-x (console/get-difficulty "X" core/difficulty-options)
        next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty-x)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-o-type] [state]
  (let [type-o      (console/get-player-type "O" core/player-options)
        next-status (if (= type-o :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] type-o)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :config-x-type] [state]
  (let [type-x      (console/get-player-type "X" core/player-options)
        next-status (if (= type-x :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] type-x)]
    (assoc new-state :status next-status)))

(defmethod core/update-state [:tui :found-save] [{:keys [interface save] :as state}]
  (if (console/yes-or-no?)
    (assoc state :status :in-progress)
    (assoc (core/initial-state {:interface interface :save save}) :status :config-x-type)))

(defmethod core/update-state [:tui :welcome] [state]
  (core/draw-state state)
  (let [saved-game (core/load-game state)]
    (if (= :found-save (:status saved-game))
      (assoc saved-game :interface :tui)
      (assoc (core/initial-state state) :status :config-x-type))))


(defn game-loop [state]
  (loop [current-state state]
    (core/draw-state state)
    (let [next-state (core/update-state current-state)]
      (if (= :game-over (:status next-state))
        next-state
        (recur next-state)))))

(defmethod core/start-game :tui [state]
  (assoc state :status :welcome)
  (game-loop state)
  #_(console/welcome-message)
  #_(let [starting-state (core/update-state state)
          _              (game-loop starting-state)]
      (core/delete-save state)
      (when (console/yes-or-no?)
        (core/start-game {:interface :tui :save (:save state)}))
      (console/exit-message)))

(defmethod core/update-state [:tui :game-over] [state]
  state)
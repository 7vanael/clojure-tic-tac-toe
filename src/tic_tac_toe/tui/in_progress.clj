(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/take-human-turn :tui [{:keys [board] :as state}]
  (let [play-options (board/play-options board)
        next-play    (console/get-next-play state play-options)]
    (core/do-take-human-turn state next-play)))

(defmethod core/get-selection [:tui :winner] [_]
  (= "y" (console/get-yes-no-response)))
(defmethod core/update-state [:tui :winner] [state]
  (core/delete-save state)
  (if (core/get-selection state)
    (assoc (core/initial-state {:interface (:interface state) :save (:save state)}) :status :config-x-type)
    (assoc state :status :game-over)))

(defmethod core/get-selection [:tui :tie] [_]
  (= "y" (console/get-yes-no-response)))
(defmethod core/update-state [:tui :tie] [state]
  (core/delete-save state)
  (if (core/get-selection state)
    (assoc (core/initial-state {:interface (:interface state) :save (:save state)}) :status :config-x-type)
    (assoc state :status :game-over)))

(defmethod core/update-state [:tui :in-progress] [state]
  (console/display-board (:board state))
  (core/do-update! state))

(defmethod core/get-selection [:tui :select-board] [_]
  (console/get-board-size core/board-options))
(defmethod core/update-state [:tui :select-board] [state]
  (let [board-size  (core/get-selection state)
        next-status :in-progress
        new-state   (assoc state :board (board/new-board board-size))]
    (assoc new-state :status next-status)))

(defmethod core/get-selection [:tui :config-o-difficulty] [_]
  (console/get-selection core/difficulty-options))
(defmethod core/update-state [:tui :config-o-difficulty] [state]
  (let [difficulty-o (core/get-selection state)
        next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty-o)]
    (assoc new-state :status next-status)))

(defmethod core/get-selection [:tui :config-x-difficulty] [_]
  (console/get-selection core/difficulty-options))
(defmethod core/update-state [:tui :config-x-difficulty] [state]
  (let [difficulty-x (core/get-selection state)
        next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty-x)]
    (assoc new-state :status next-status)))


(defmethod core/get-selection [:tui :config-o-type] [_]
  (console/get-selection core/player-options))
(defmethod core/update-state [:tui :config-o-type] [state]
  (let [type-o      (core/get-selection state)
        next-status (if (= type-o :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] type-o)]
    (assoc new-state :status next-status)))

(defmethod core/get-selection [:tui :config-x-type] [_]
  (console/get-selection core/player-options))
(defmethod core/update-state [:tui :config-x-type] [state]
  (let [type-x      (core/get-selection state)
        next-status (if (= type-x :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] type-x)]
    (assoc new-state :status next-status)))

(defmethod core/get-selection [:tui :found-save] [_]
  (= "y" (console/get-yes-no-response)))
(defmethod core/update-state [:tui :found-save] [{:keys [interface save] :as state}]
  (if (core/get-selection state)
    (assoc state :status :in-progress)
    (assoc (core/initial-state {:interface interface :save save}) :status :config-x-type)))

(defmethod core/update-state [:tui :welcome] [state]
  (let [saved-game (core/load-game state)]
    (if (= :found-save (:status saved-game))
      (assoc saved-game :interface :tui)
      (assoc (core/initial-state state) :status :config-x-type))))


(defn game-loop [state]
  (loop [current-state state]
    (core/draw-state current-state)
    (let [next-state (core/update-state current-state)]
      (if (= :game-over (:status next-state))
        next-state
        (recur next-state)))))

(defmethod core/start-game :tui [state]
  (-> state
      (assoc :status :welcome)
      game-loop))
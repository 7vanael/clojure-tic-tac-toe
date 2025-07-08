(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/draw-state [:tui :winner] [{:keys [active-player-index players]}]
  (let [character (get-in players [active-player-index :character])]
    (console/announce-winner character)
    (console/play-again-prompt)))
(defmethod core/get-selection [:tui :winner] [_]
  (console/yes-or-no?))

(defmethod core/draw-state [:tui :tie] [_]
  (console/announce-draw)
  (console/play-again-prompt))
(defmethod core/get-selection [:tui :tie] [_]
  (console/yes-or-no?))

(defmethod core/draw-state [:tui :in-progress] [{:keys [board] :as state}]
  (console/announce-player state)
  (console/display-board board))
(defmethod core/get-selection [:tui :in-progress] [{:keys [board] :as state}]
  (let [play-options (board/play-options board)]
    (console/get-next-play state play-options)))

(defmethod core/draw-state [:tui :select-board] [_]
  (console/board-size-prompt core/board-options))
(defmethod core/get-selection [:tui :select-board] [_]
  (console/get-board-size core/board-options))

(defmethod core/draw-state [:tui :config-o-difficulty] [_]
  (console/display-difficulty-options "O" core/difficulty-options))
(defmethod core/get-selection [:tui :config-o-difficulty] [_]
  (let [selection (console/get-selection "O" core/difficulty-options)]
    (case selection
      :easy 1
      :medium 2
      :hard 3
      nil)))

(defmethod core/draw-state [:tui :config-x-difficulty] [_]
  (console/display-difficulty-options "X" core/difficulty-options))
(defmethod core/get-selection [:tui :config-x-difficulty] [_]
  (let [selection (console/get-selection "X" core/difficulty-options)]
    (case selection
      :easy 1
      :medium 2
      :hard 3
      nil)))

(defmethod core/draw-state [:tui :config-o-type] [_]
  (console/display-play-type-options "O" core/player-options))
(defmethod core/get-selection [:tui :config-o-type] [_]
  (if (= :human (console/get-selection "O" core/player-options)) 1 2))

(defmethod core/draw-state [:tui :config-x-type] [_]
  (console/display-play-type-options "X" core/player-options))
(defmethod core/get-selection [:tui :config-x-type] [_]
  (if (= :human (console/get-selection "X" core/player-options)) 1 2))

(defmethod core/get-selection [:tui :found-save] [_]
  (console/yes-or-no?))
(defmethod core/draw-state [:tui :found-save] [_]
  (console/save-found-prompt))

(defmethod core/get-selection [:tui :welcome] [_] 1)
(defmethod core/draw-state [:tui :welcome] [_]
  (console/welcome-message))



;(defn build-state [state]
;  (loop [state state]
;    (if (= (:status state) :ready)
;      (assoc state :status :in-progress)
;      (recur (core/update-state state)))))

;(defmethod core/start-game :tui [state]
;  (console/welcome-message)
;  (let [starting-state (if-let [saved-game (core/load-game state)]
;                         (core/update-state (assoc saved-game :status :found-save :interface :tui))
;                         (initialize-state (:save state)))
;        _              (game-loop starting-state)]
;    (core/delete-save state)
;    (when (console/play-again?)
;      (core/start-game {:interface :tui :save (:save state)}))
;    (console/exit-message)))

;(defn game-loop [state]
;  (loop [current-state state]
;    (when (:board current-state)
;      (console/display-board (:board current-state)))
;    (let [next-state (core/update-state current-state)]
;      (if (= :game-over (:status next-state))
;        (do (console/display-board (:board next-state))
;            next-state)
;        (recur next-state)))))
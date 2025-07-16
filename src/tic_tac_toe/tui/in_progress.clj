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

(defmethod core/get-selection [:tui :tie] [_]
  (= "y" (console/get-yes-no-response)))

(defmethod core/update-state :in-progress [state]
  (core/do-update! state))

(defmethod core/get-selection [:tui :select-board] [_]
  (console/get-board-size core/board-options))

(defmethod core/get-selection [:tui :config-o-difficulty] [_]
  (console/get-selection core/difficulty-options))

(defmethod core/get-selection [:tui :config-x-difficulty] [_]
  (console/get-selection core/difficulty-options))

(defmethod core/get-selection [:tui :config-o-type] [_]
  (console/get-selection core/player-options))

(defmethod core/get-selection [:tui :config-x-type] [_]
  (console/get-selection core/player-options))

(defmethod core/get-selection [:tui :found-save] [_]
  (= "y" (console/get-yes-no-response)))

(defmethod core/get-selection [:tui :welcome] [_] 1)



(defn game-loop [state]
  (loop [current-state state]
    (core/draw-state current-state)
    (if (core/states-to-break-loop (:status current-state))
      (let [replay? (core/get-selection current-state)]
        (core/update-state current-state replay?))
      (let [next-state (core/do-update! current-state)]
        (if (or (= :config-x-type (:status next-state))
                (= :game-over (:status next-state)))
          next-state
          (recur next-state))))))

(defn configure-loop [state]
  (loop [current-state state]
    (core/draw-state current-state)
    (let [next-selection (core/get-selection current-state)
          next-state     (core/update-state current-state next-selection)]
      (if (= :in-progress (:status next-state))
        next-state
        (recur next-state)))))

(defmethod core/start-game :tui [state]
  (let [first-configured (configure-loop (assoc state :status :welcome))]
    (loop [current-state first-configured]
      (let [final-state (game-loop current-state)]
        (when (= (:status final-state) :config-x-type)
          (recur (configure-loop final-state)))))))
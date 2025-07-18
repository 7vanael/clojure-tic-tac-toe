(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.functions :as functions]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/take-human-turn :tui [state]
  ;(let [play-options (board/play-options board)
  ;      next-play    (console/get-next-play state play-options)]
    (core/do-take-human-turn state (core/get-selection state)))
;)

(defmethod core/get-selection [:tui :winner] [state]
  (assoc state :response (= "y" (console/get-yes-no-response))))

(defmethod core/get-selection [:tui :tie] [state]
  (assoc state :response (= "y" (console/get-yes-no-response))))

(defmethod core/get-selection [:tui :in-progress] [{:keys [board] :as state}]
  (let [play-options (board/play-options board)]
    (assoc state :response (console/get-next-play state play-options))))

(defmethod core/update-state :in-progress [state ]
  (core/play-turn! state))

(defmethod core/get-selection [:tui :select-board] [state]
  (assoc state :response (console/get-board-size core/board-options)))

(defmethod core/get-selection [:tui :config-o-difficulty] [state]
  (assoc state :response (console/get-selection core/difficulty-options)))

(defmethod core/get-selection [:tui :config-x-difficulty] [state]
  (assoc state :response (console/get-selection core/difficulty-options)))

(defmethod core/get-selection [:tui :config-o-type] [state]
  (assoc state :response (console/get-selection core/player-options)))

(defmethod core/get-selection [:tui :config-x-type] [state]
 (assoc state :response (console/get-selection core/player-options)))

(defmethod core/get-selection [:tui :found-save] [state]
  (assoc state :response (= "y" (console/get-yes-no-response))))

(defmethod core/get-selection [:tui :welcome] [state] state)

(defn prompt-to-resume [loaded-game]
  (core/draw-state loaded-game)
  (if (core/get-selection loaded-game)
    (assoc loaded-game :status :in-progress)
    (core/fresh-start loaded-game)))

(defn maybe-config-o-difficulty [{:keys [status] :as state}]
  (if (= :config-o-difficulty status)
    (do (core/draw-state state)
        (functions/config-o-difficulty state (core/get-selection state)))
    state))

(defn maybe-config-x-difficulty [{:keys [status] :as state}]
  (if (= :config-x-difficulty status)
    (do (core/draw-state state)
        (functions/config-x-difficulty state (core/get-selection state)))
    state))

(defn maybe-resume-game [{:keys [loaded-game] :as state}]
  (if (= :found-save (:status loaded-game))
    (prompt-to-resume loaded-game)
    (core/fresh-start state)))

(defn maybe-setup-state [state]
  (if (= :config-x-type (:status state))
    (-> state
        core/draw-state
        (functions/config-x-type (core/get-selection state))
        maybe-config-x-difficulty
        core/draw-state
        (functions/config-o-type (core/get-selection state))
        maybe-config-o-difficulty
        ;core/->inspect
        core/draw-state
        ;core/->inspect
        (functions/select-board (core/get-selection state)))
    state))

(defmethod core/start-game :tui [state]
  (let [loaded-game (core/load-game state)
        state       (maybe-resume-game (core/->inspect (assoc state :loaded-game loaded-game)))
        state       (maybe-setup-state state)
        ;_ (println "in start-game, maybe-set-up complete¬")
        ;_ (prn "state:" state)
        ]
    (loop [state state]
      ;(println "inside loop of start-game")
      (core/draw-state state)
      (if (functions/game-over? state)
        state
        (recur (core/play-turn! state))
        #_(recur (core/update-state state (core/get-selection state)))))))

;(defn game-loop [state]
;  (loop [current-state state]
;    (core/draw-state current-state)
;    (if (core/states-to-break-loop (:status current-state))
;      (let [replay? (core/get-selection current-state)]
;        (core/update-state current-state replay?))
;      (let [next-state (core/play-turn! current-state)]
;        (if (or (= :config-x-type (:status next-state))
;                (= :game-over (:status next-state)))
;          next-state
;          (recur next-state))))))
(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(defmethod core/take-human-turn :tui [state]
  (-> state
      core/get-selection
      core/do-take-human-turn))

(defmethod core/get-selection [:tui :winner] [state]
  (assoc state :response (= "y" (console/get-yes-no-response))))

(defmethod core/get-selection [:tui :tie] [state]
  (assoc state :response (= "y" (console/get-yes-no-response))))

(defmethod core/get-selection [:tui :in-progress] [{:keys [board] :as state}]
  (let [play-options (board/play-options board)]
    (assoc state :response (console/get-next-play state play-options))))

(defmethod core/update-state :in-progress [state]
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
  (if (:response (core/get-selection loaded-game))
    (assoc loaded-game :status :in-progress)
    (core/fresh-start loaded-game)))

(defn maybe-config-o-difficulty [{:keys [status] :as state}]
  (if (= :config-o-difficulty status)
    (-> state
        core/draw-state
        core/get-selection
        core/config-o-difficulty)
    state))

(defn maybe-config-x-difficulty [{:keys [status] :as state}]
  (if (= :config-x-difficulty status)
    (-> state
        core/draw-state
        core/get-selection
        core/config-x-difficulty)
    state))

(defn maybe-resume-game [{:keys [loaded-game] :as state}]
  (if (= :found-save (:status loaded-game))
    (prompt-to-resume loaded-game)
    (core/fresh-start state)))

(defn maybe-setup-state [state]
  (if (= :config-x-type (:status state))
    (-> state
        core/draw-state
        core/get-selection
        core/config-x-type
        maybe-config-x-difficulty
        core/draw-state
        core/get-selection
        core/config-o-type
        maybe-config-o-difficulty
        core/draw-state
        core/get-selection
        core/select-board)
    state))

(defn play-again? [state]
  (:response (core/get-selection state)))

(defn exit-game! [] (System/exit 0))

(defn play-game [state]
  (loop [state state]
    (let [state (maybe-setup-state state)]
      (core/draw-state state)
      (if (core/game-over? state)
        (if-not (play-again? state)
          (do (core/draw-state (assoc state :status :game-over))
              (exit-game!))
          (recur (core/fresh-start state)))
        (recur (core/play-turn! state))))))

(defmethod core/start-game :tui [state]
  (let [loaded-game (core/load-game state)
        state       (maybe-resume-game (assoc state :loaded-game loaded-game))]
    (play-game state)))

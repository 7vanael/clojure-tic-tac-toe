(ns tic-tac-toe.functions
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(def states-to-end
  #{:tie :winner})

(defn game-over? [{:keys [status]}]
  (states-to-end status))

(defn player-played? [{:keys [active-player-index board players]}]
  (let [current-char (get-in players [active-player-index :character])]
    (not (= current-char (board/next-player board)))))

(defn change-player [{:keys [active-player-index] :as state}]
  (if (player-played? state)
    (assoc state :active-player-index (if (= 0 active-player-index) 1 0))
    state))

(defn get-answer [state]
  #_(wish I had one))

(defn play-again? [state]
  (let [replay (get-answer state)]
    (if replay
      (assoc (core/initial-state {:interface (:interface state) :save (:save state)}) :status :config-x-type)
      (assoc state :status :game-over))))

(defn take-turn [{:keys [active-player-index board players] :as state}]
  (if (player-played? state)
    state
    ))
(ns tic-tac-toe.functions
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn maybe-load-save [state]
  (let [saved-game (core/load-game state)]
    (if (= :found-save (:status saved-game))
      (assoc saved-game :interface (:interface state))
      (assoc (core/initial-state state) :status :config-x-type))))

(defn maybe-resume-save [state resume]
  (if resume
    (core/go-in-progress state)
    (core/fresh-start state)))

(defn config-x-type [state play-type]
  (let [next-status (if (= play-type :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] play-type)]
    (assoc new-state :status next-status)))

(defn config-x-difficulty [state difficulty]
  (let [next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty)]
    (assoc new-state :status next-status)))

(defn config-o-type [state play-type]
  (let [next-status (if (= play-type :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] play-type)]
    (assoc new-state :status next-status)))

(defn config-o-difficulty [state difficulty]
  (let [next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty)]
    (assoc new-state :status next-status)))

(defn select-board [state board-size]
  (let [next-status :in-progress
        new-state   (assoc state :board (board/new-board board-size))]
    (assoc new-state :status next-status)))

(defn maybe-play-again [state answer]
  (if answer
    (assoc (core/initial-state {:interface (:interface state) :save (:save state)}) :status :config-x-type)
    (assoc state :status :game-over)))


(defn player-played? [{:keys [active-player-index board players]}]
  (let [current-char (get-in players [active-player-index :character])]
    (not (= current-char (board/next-player board)))))

(defn maybe-take-turn [state next-play]
  (if (player-played? state)
    state
    (core/do-take-human-turn state next-play)))

(def states-to-end
  #{:tie :winner})

(defn game-over? [{:keys [status]}]
  (states-to-end status))

(defn change-player [{:keys [active-player-index] :as state}]
  (if (player-played? state)
    (assoc state :active-player-index (if (= 0 active-player-index) 1 0))
    state))
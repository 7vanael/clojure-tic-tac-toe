(ns tic-tac-toe.core
  (:require [tic-tac-toe.board :as board])
  (:import (java.io FileNotFoundException)))

(defn initial-state [{:keys [interface save] :as state}]
  (println "In initial-state, passed in state: " state)
  {:interface           interface
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]
   :save                save})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))

(defmulti take-computer-turn get-computer-difficulty)

(defn currently-human? [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])]
    (= player-type :human)))

(defn next-player [board]
  (let [flat-board (flatten board)
        played     (count (filter string? flat-board))]
    (if (even? played) "X" "O")))

(def states-to-break-loop
  #{:tie :winner})

(defn change-player [{:keys [active-player-index players board] :as state}]
  (let [current-char               (get-in players [active-player-index :character])
        next-player-char           (next-player board)
        current-player-not-played? (= current-char next-player-char)
        game-over?                 (contains? states-to-break-loop (:status state))]
    (if (or game-over? current-player-not-played?)
      state
      (assoc state :active-player-index
                   (if (= (:active-player-index state) 0)
                     1 0)))))

(defmulti save-game :save)
(defmulti load-game :save)
(defmulti delete-save :save)

(defn state-draw-dispatch [state]
  (prn "DISPATCH DRAW state:" state)
  [(:interface state) (:status state)])

(defn state-selection-dispatch [state _]
  (prn "DISPATCH GET-SELECTION state:" state)
  [(:interface state) (:status state)])

(defmulti draw-state state-draw-dispatch)
(defmulti get-selection state-selection-dispatch)

;(defmethod draw-state :default [state] (println "BAD STATE!!! " state) )
;(defmethod get-selection :default [state] (println "GET-SELECTION BAD SATE: " state))

(defn state-status-dispatch [state _]
  (prn "UPDATING state:" state)
  (:status state))

(defmulti update-state state-status-dispatch)
;(defmethod update-state :default [state value] (println "default update-state"))

(defn take-turn [state]
  (if (currently-human? state)
    (get-selection state)
    (take-computer-turn state)))

(defn make-move [{:keys [board active-player-index players] :as state} value]
  (let [character   (get-in players [active-player-index :character])
        coordinates (board/space->coordinates value board)]
    (assoc state :board (board/take-square board coordinates character))))

(defmethod update-state :winner [state value]
  (try
    (delete-save state)
    (catch FileNotFoundException _))
  (cond (= 1 value) (assoc (initial-state state) :status :config-x-type)
        (= 2 value) (assoc state :status :exit)
        :else state)
  state)

(defmethod update-state :tie [state value]
  (try
    (delete-save state)
    (catch FileNotFoundException _))
  (cond (= 1 value) (assoc (initial-state state) :status :config-x-type)
        (= 2 value) (assoc state :status :exit)
        :else state)
  state)

(defmethod update-state :in-progress [state value]
  (-> state
      (make-move value)
      board/evaluate-board
      change-player
      save-game))

(defmethod update-state :select-board [state value]
  (cond (= 1 value) (-> state
                        (assoc :board (board/new-board 3))
                        (assoc :status :in-progress))
        (= 2 value) (-> state
                        (assoc :board (board/new-board 4))
                        (assoc :status :in-progress))
        (= 3 value) (-> state
                        (assoc :board (board/new-board [3 3 3]))
                        (assoc :status :in-progress))
        :else state))

(defmethod update-state :config-o-difficulty [state value]
  (cond (= 1 value) (-> state
                        (assoc-in [:players 1 :difficulty] :easy)
                        (assoc :status :select-board))
        (= 2 value) (-> state
                        (assoc-in [:players 1 :difficulty] :medium)
                        (assoc :status :select-board))
        (= 3 value) (-> state
                        (assoc-in [:players 1 :difficulty] :hard)
                        (assoc :status :select-board))
        :else state))

(defmethod update-state :config-x-difficulty [state value]
  (cond (= 1 value) (-> state
                        (assoc-in [:players 0 :difficulty] :easy)
                        (assoc :status :config-o-type))
        (= 2 value) (-> state
                        (assoc-in [:players 0 :difficulty] :medium)
                        (assoc :status :config-o-type))
        (= 3 value) (-> state
                        (assoc-in [:players 0 :difficulty] :hard)
                        (assoc :status :config-o-type))
        :else state))

(defmethod update-state :config-o-type [state value]
  (cond (= 1 value) (-> state
                        (assoc-in [:players 1 :play-type] :human)
                        (assoc :status :select-board))
        (= 2 value) (-> state
                        (assoc-in [:players 1 :play-type] :computer)
                        (assoc :status :config-o-difficulty))
        :else state))

(defmethod update-state :config-x-type [state value]
  (cond (= 1 value) (-> state
                        (assoc-in [:players 0 :play-type] :human)
                        (assoc :status :config-o-type))
        (= 2 value) (-> state
                        (assoc-in [:players 0 :play-type] :computer)
                        (assoc :status :config-x-difficulty))
        :else state))


(defmethod update-state :found-save [state value]
  (cond (= 1 value) (assoc state :status :in-progress)
        (= 2 value) (assoc (initial-state state) :status :config-x-type)
        :else state))

(defmethod update-state :welcome [state _]
  (println "WELCOME")
  (let [saved-game (load-game state)]
    (cond (nil? saved-game) (assoc state :status :config-x-type)
          :else (assoc saved-game :status :found-save :interface (:interface state)))))

(defn active-player-type [{:keys [active-player-index players]}]
  (get-in players [active-player-index :play-type]))

(defn get-input [state]
  (if (and (= :in-progress (:status state))
           (= :computer (active-player-type state)))
    (take-turn state)
    (get-selection state)))

(defn play-game [starting-state]
  (prn "PLAY-GAME starting-state:" starting-state)
  (loop [state starting-state]
    (if (= :exit (:status state))
      state
      (let [_             (draw-state state)
            input         (get-input state)
            updated-state (update-state state input)]
        (recur updated-state)))))




  ;
  ;(defn play-game [state]
  ;  (loop [state state]
  ;    (if (states-to-break-loop (:status state))
  ;      state
  ;      (let [_             (draw-state state)
  ;            input         (take-turn state)                 ;This is take-turn so the computer can have a chance!
  ;            ; take-turn will route to make-selection if it's the human's turn
  ;            moved-state   (make-move state input)
  ;            updated-state (-> moved-state
  ;                              board/evaluate-board
  ;                              change-player
  ;                              save-game
  ;                              draw-state                    ;Is this a good place for this??
  ;                              )]
  ;        (recur updated-state)))))

  ;(defn do-update! [state]
  ;  (-> state
  ;      take-turn
  ;      board/evaluate-board
  ;      change-player
  ;      save-game))

  ;(defn active-player-type [{:keys [players active-player-index]}]
  ;  (get-in players [active-player-index :play-type]))
  ;
  ;(defmulti select-box active-player-type)
  ;
  ;(defn do-take-human-turn [{:keys [board players active-player-index] :as state} next-play]
  ;  (assoc state :board (board/take-square board
  ;                                         (board/space->coordinates next-play board)
  ;                                         (get-in players [active-player-index :character]))))
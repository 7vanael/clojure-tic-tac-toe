(ns tic-tac-toe.core
  (:require [tic-tac-toe.board :as board]))


(defn ->inspect [x]
  (prn "x:" x)
  x)

(defn initial-state [state]
  (merge
    {:interface           :tui
     :board               nil
     :active-player-index 0
     :status              :welcome
     :players             [{:character "X" :play-type nil :difficulty nil}
                           {:character "O" :play-type nil :difficulty nil}]
     :save                :sql}
    state))

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defn dual-dispatch [state]
  [(:interface state) (:status state)])
(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))
(defn active-player-type [{:keys [players active-player-index]}]
  (get-in players [active-player-index :play-type]))

(defmulti start-game :interface)
(defmulti update-state (fn [state & _] (:status state)))
(defmulti take-computer-turn get-computer-difficulty)
(defmulti take-human-turn :interface)
(defmulti save-game :save)
(defmulti load-game :save)
(defmulti delete-save :save)
(defmulti draw-state dual-dispatch)
(defmulti mouse-clicked (fn [state & _] (:status state)))
(defmulti get-selection dual-dispatch)

(defn currently-human? [state]
  (= :human (active-player-type state)))

(defn take-turn [state]
  (if (currently-human? state)
    (take-human-turn state)
    (take-computer-turn state)))

(defn do-take-human-turn [{:keys [board players active-player-index] :as state} next-play]
  (assoc state :board (board/take-square board
                                         (board/space->coordinates next-play board)
                                         (get-in players [active-player-index :character]))))

(def states-to-break-loop
  #{:tie :winner})

(defn change-player [{:keys [active-player-index players board] :as state}]
  (let [current-char               (get-in players [active-player-index :character])
        current-player-not-played? (= current-char (board/next-player board))
        game-over?                 (states-to-break-loop (:status state))]
    (if (or current-player-not-played? game-over?)
      state
      (assoc state :active-player-index (if (= (:active-player-index state) 0) 1 0)))))

(defn play-turn! [state]
  (-> state
      take-turn
      board/evaluate-board
      change-player
      save-game))

(defn fresh-start [{:keys [interface save]}]
  (initial-state {:interface interface :save save :status :config-x-type}))

(defn go-in-progress [state]
  (assoc state :status :in-progress))

(defmethod update-state :winner [state replay]
  (delete-save state)
  (if replay
    (fresh-start state)
    (assoc state :status :game-over)))

(defmethod update-state :tie [state replay]
  (delete-save state)
  (if replay
    (fresh-start state)
    (assoc state :status :game-over)))

(defmethod update-state :select-board [state board-size]
  (let [next-status :in-progress
        new-state   (assoc state :board (board/new-board board-size))]
    (assoc new-state :status next-status)))

(defmethod update-state :config-o-difficulty [state difficulty]
  (let [next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty)]
    (assoc new-state :status next-status)))

(defmethod update-state :config-x-difficulty [state difficulty]
  (let [next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty)]
    (assoc new-state :status next-status)))

(defmethod update-state :config-o-type [state play-type]
  (let [next-status (if (= play-type :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] play-type)]
    (assoc new-state :status next-status)))

(defmethod update-state :config-o-type [state play-type]
  (let [next-status (if (= play-type :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] play-type)]
    (assoc new-state :status next-status)))

(defmethod update-state :config-x-type [state play-type]
  (let [next-status (if (= play-type :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] play-type)]
    (assoc new-state :status next-status)))

(defmethod update-state :found-save [state resume]
  (if resume
    (go-in-progress state)
    (fresh-start state)))

(defmethod update-state :welcome [state _]
  (let [saved-game (load-game state)]
    (if (= :found-save (:status saved-game))
      (assoc saved-game :interface (:interface state))
      (assoc (initial-state state) :status :config-x-type))))
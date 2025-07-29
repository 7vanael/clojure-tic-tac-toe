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
(defmulti take-computer-turn get-computer-difficulty)
(defmulti take-human-turn :interface)
(defmulti save-game :save)
(defmulti load-game :save)
(defmulti delete-save :save)
(defmulti draw-state dual-dispatch)
(defmulti mouse-clicked (fn [state & _] (:status state)))
(defmulti get-selection dual-dispatch)


(defn do-take-human-turn [{:keys [board players active-player-index] :as state}]
  (let [next-play (:response state)
        clean-state (dissoc state :response)]
    (assoc clean-state :board (board/take-square board
                                                 (board/space->coordinates next-play board)
                                                 (get-in players [active-player-index :character])))))

(def states-to-break-loop
  #{:tie :winner})

(defn game-over? [{:keys [status]}]
  (states-to-break-loop status))

(defn player-played? [{:keys [active-player-index board players]}]
  (let [current-char (get-in players [active-player-index :character])]
    (not (= current-char (board/next-player board)))))

(defn maybe-take-turn [state]
  (if (player-played? state)
    (dissoc state :response)
    (do-take-human-turn state)))

(defn currently-human? [state]
  (= :human (active-player-type state)))

(defn take-turn [state]
  (if (currently-human? state)
    (take-human-turn state) ;should this be maybe-take-turn?
    (take-computer-turn state)))


(defn change-player [{:keys [active-player-index] :as state}]
  (if (or (not (player-played? state))
          (game-over? state))
    state
    (assoc state :active-player-index (if (= active-player-index 0) 1 0))))

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

(defn maybe-load-save [state]
  (let [saved-game (load-game state)]
    (if (= :found-save (:status saved-game))
      (assoc saved-game :interface (:interface state))
      (assoc (initial-state state) :status :config-x-type))))

(defn maybe-resume-save [state resume]
  (if resume
    (go-in-progress state)
    (fresh-start state)))

(defn config-x-type [state]
  (let [play-type (:response state)
        next-status (if (= play-type :human) :config-o-type :config-x-difficulty)
        new-state   (assoc-in state [:players 0 :play-type] play-type)]
    (dissoc (assoc new-state :status next-status) :response)))

(defn config-x-difficulty [state]
  (let [difficulty (:response state)
        next-status  :config-o-type
        new-state    (assoc-in state [:players 0 :difficulty] difficulty)]
    (dissoc (assoc new-state :status next-status) :response)))

(defn config-o-type [state]
  (let [play-type (:response state)
        next-status (if (= play-type :human) :select-board :config-o-difficulty)
        new-state   (assoc-in state [:players 1 :play-type] play-type)]
    (dissoc (assoc new-state :status next-status) :response)))

(defn config-o-difficulty [state]
  (let [difficulty (:response state)
        next-status  :select-board
        new-state    (assoc-in state [:players 1 :difficulty] difficulty)]
    (dissoc (assoc new-state :status next-status) :response)))

(defn select-board [state]
  (let [board-size (:response state)
        next-status :in-progress
        new-state   (assoc state :board (board/new-board board-size))]
    (dissoc (assoc new-state :status next-status) :response)))

(defn maybe-play-again [state]
  (delete-save state)
  (if (:response state)
    (assoc (initial-state {:interface (:interface state) :save (:save state)}) :status :config-x-type)
    (dissoc (assoc state :status :game-over) :response)))
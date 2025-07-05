(ns tic-tac-toe.core
  (:require [tic-tac-toe.board :as board]))

(defn initial-state [interface & [save]]
  {:interface           interface
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]
   :save                (or save :sql)})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defmulti start-game :interface)

(defn state-dispatch [state _]
  [(:interface state) (:status state)])

(defmulti update-state state-dispatch)

(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))

(defmulti take-computer-turn get-computer-difficulty)

(defmulti take-human-turn :interface)
(defn currently-human? [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])]
    (= player-type :human)))

(defn active-player-type [{:keys [players active-player-index]}]
  (get-in players [active-player-index :play-type]))

(defmulti select-box active-player-type)

(defn next-player [board]
  (let [flat-board (flatten board)
        played     (count (filter string? flat-board))]
    (if (even? played) "X" "O")))

(defn take-turn [{:keys [active-player-index players board] :as state}]
  ;(let [current-char     (get-in players [active-player-index :character])
  ;      next-player-char (next-player board)
  ;      correct-player   (= current-char next-player-char)]
  (if (currently-human? state)
    (take-human-turn state)
    (take-computer-turn state)))
;)

(defn do-take-human-turn [{:keys [board players active-player-index] :as state} next-play]
  (assoc state :board (board/take-square board
                                         (board/space->coordinates next-play board)
                                         (get-in players [active-player-index :character]))))

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


(defn state-draw-dispatch [state ]
  [(:interface state) (:status state)])
;This is dependent on both state and interface right now, but will be dependent on
; only state when refactoring is finished
(defmulti draw-state state-draw-dispatch)

(defmulti mouse-clicked (fn [state & _] (:status state)))

(defn do-update! [state]
  (-> state
      take-turn
      board/evaluate-board
      change-player
      save-game))
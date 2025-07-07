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

(defn state-dispatch [state _]
  [(:interface state) (:status state)])

(defmulti update-state state-dispatch)

(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))

(defmulti take-computer-turn get-computer-difficulty)

(defmulti take-human-turn :interface)                       ;Refactor this to get-selection?

(defn currently-human? [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])]
    (= player-type :human)))

(defmulti get-selection :interface)

(defn next-player [board]
  (let [flat-board (flatten board)
        played     (count (filter string? flat-board))]
    (if (even? played) "X" "O")))


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


(defn state-draw-dispatch [state]
  [(:interface state) (:status state)])
;This is dependent on both state and interface right now, I'm not confident it can be
; implementation independent at this point.
(defmulti draw-state state-draw-dispatch)
;Each implementation will have a draw :welcome, draw :in-progress, draw :tie?

(defmulti mouse-clicked (fn [state & _] (:status state)))

(defmethod update-state :tie [state input]
  (cond (= 1 input) (assoc (initial-state (:interface state) (:save state))
                      :status :config-x-type)  ; Play again
        (= 2 input) (assoc state :status :exit)    ; Quit
        :else state))

(defn take-turn [state]
  (if (currently-human? state)
    (get-selection state)
    (take-computer-turn state)))

(defn make-move [{:keys [board active-player-index players] :as state} value]
  (let [character   (get-in players [active-player-index :character])
        coordinates (board/space->coordinates value board)]
    (assoc state :board (board/take-square board coordinates character))))

(defmethod update-state :in-progress [state value]
  (-> state
      (make-move value)
      board/evaluate-board
      change-player
      save-game))

(defmethod update-state :welcome [state value]
  (let [saved-game (load-game state)]
    (cond ;(nil? value) state ;I'm not sure I need this condition?
          (nil? saved-game) (assoc state :status :config-x-type)
          :else (assoc saved-game :status :found-save :interface (:interface state)))))

(defn get-input [state]
  (if (= :in-progress (:status state))
         (take-turn state)
         (get-selection state)))

(defn play-game [initial-state]
  (loop [state initial-state]
    (if (= :exit (:status state))
      state
      (let [_             (draw-state state)
            input         (get-input state)
            updated-state (update-state state input)]
        (recur updated-state)))))

(defmulti launch :interface)

(defn start-game [initial-state]
  (launch initial-state)
  (play-game initial-state))

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

(defn do-update! [state]
  (-> state
      take-turn
      board/evaluate-board
      change-player
      save-game))

;(defn active-player-type [{:keys [players active-player-index]}]
;  (get-in players [active-player-index :play-type]))
;
;(defmulti select-box active-player-type)
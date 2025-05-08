(ns tic-tac-toe.core)

(defmulti start-game :interface)

(defn get-update-state [state]
  [(:interface state) (:status state)])

(defmulti update-state get-update-state)

(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))

(defmulti take-computer-turn get-computer-difficulty)

(defmulti take-human-turn :interface)

(defn currently-human? [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])]
    (= player-type :human)))

(defn next-player [{:keys [board]}]
  (let [flat-board (flatten board)
        total (count flat-board)
        available (count (filter number? flat-board))
        played (- total available)]
    (if (even? played) "X" "O")))

(defn take-turn [{:keys [active-player-index players] :as state}]
  (let [current-char (get-in players [active-player-index :character])
        next-player-char (next-player state)
        correct-player (= current-char next-player-char)]
    (cond (not correct-player) state
          (currently-human? state) (take-human-turn state)
          :else (take-computer-turn state))))

(def states-to-break-loop #{:tie :winner})

(defn change-player [state]
  (when-not (contains? states-to-break-loop (:status state))
    (assoc state :active-player-index
               (if (= (:active-player-index state) 0)
                 1 0))))

(defn break-loop? [{:keys [status] :as state}]
  (if (contains? states-to-break-loop status)
    (update-state state)
    state))
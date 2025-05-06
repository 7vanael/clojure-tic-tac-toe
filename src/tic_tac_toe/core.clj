(ns tic-tac-toe.core)

(defmulti start-game :interface)

(defn get-update-state [state]
  [(:interface state) (:status state)])

(defmulti update-state get-update-state)

(defn get-computer-difficulty [{:keys [active-player-index players]}]
  (get-in players [active-player-index :difficulty]))

(defmulti take-computer-turn get-computer-difficulty)

(defmulti take-human-turn :interface)

(defn human? [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])]
    (= player-type :human)))

(defn take-turn [state]
  (if (human? state)
    (take-human-turn state)
    (take-computer-turn state)))

(defn change-player [state]
  (assoc state :active-player-index
               (if (= (:active-player-index state) 0)
                 1 0)))
(def states-to-break-loop
  #{:tie :winner})

(defn break-loop? [{:keys [status] :as state}]
  (if (contains? states-to-break-loop status)
    (update-state state)
    state))
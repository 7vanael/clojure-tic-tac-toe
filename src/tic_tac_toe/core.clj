(ns tic-tac-toe.core)

(defn get-interface [state _] (:interface state))

(defmulti welcome-message :interface)
(defmulti display-board get-interface)
(defmulti get-next-play get-interface)
(defmulti announce-player get-interface)
(defmulti announce-draw :interface)
(defmulti announce-winner get-interface)
(defmulti get-player-type (fn [state & _] (:interface state)))
(defmulti play-again? :interface)
(defmulti get-board-size get-interface)
(defmulti get-difficulty (fn [state & _] (:interface state)))

(defmulti start-game :interface)

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
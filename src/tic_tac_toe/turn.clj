(ns tic-tac-toe.turn)

(defn get-turn-type [{:keys [active-player-index players]}]
  (get-in players [active-player-index :play-type]))

(defmulti take-turn get-turn-type)
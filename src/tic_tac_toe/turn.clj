(ns tic-tac-toe.turn)

(defn human? [player-type] (= player-type :human))

(defn get-turn-type [{:keys [active-player-index players]}]
  (let [player-type (get-in players [active-player-index :play-type])
        turn-type   (if (human? player-type)
                      :human
                      (get-in players [active-player-index :difficulty]))]
    turn-type))

(defmulti take-turn get-turn-type)
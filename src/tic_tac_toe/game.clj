(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.computer :as computer]))

(defn change-player [state]
  (assoc state :active-player-index (if (= (:active-player-index state) 0)
                                      (do (console/announce-player "O") 1)
                                      (do (console/announce-player "X") 0))))

(defn evaluate-board [{:keys [board active-player-index players] :as state}]
  (cond (board/winner? board (get-in players [active-player-index :character])) (assoc state :status "winner")
        (not (board/any-space-available? board)) (assoc state :status "draw")
        :else state))

(defn get-turn-type [{:keys [active-player-index players]}]
  (get-in players [active-player-index :play-type]))

(defmulti take-turn get-turn-type)

(defmethod take-turn :human [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char (get-in players [active-player-index :character])]
    (assoc state :board (board/take-square board next-play-coordinates player-char))))

(defmethod take-turn :computer [state]
  (computer/turn state))

(defmethod take-turn :easy [state]
  (computer/easy state))

(defmethod take-turn :medium [state]
  (computer/medium state))

#_(defn take-turn [state]
  (let [active-type (get-in state [:players (:active-player-index state) :play-type])]
    (if (= :human active-type)
      (human-turn state)
      (computer/turn state))))

(defn play [{:keys [board status active-player-index players] :as state}]
  (console/display-board board)
  (cond (= status "draw") (console/draw)
        (= status "winner") (console/announce-winner (get-in players [active-player-index :character]))
        :else (recur (evaluate-board (take-turn (change-player state))))))

(defn start [state]
  (console/welcome)
  (play state))
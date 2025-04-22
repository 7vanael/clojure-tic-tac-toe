(ns tic-tac-toe.hard
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.computer-util :as util]))

(defn eval-board [board depth comp-char opp-char]
  (cond (board/winner? board comp-char) (- 10 depth)
        (board/winner? board opp-char) (- depth 10)
        :else 0))


(defn done? [open-spaces board char opp-char depth max-depth]
  (or (= depth max-depth)
      (empty? open-spaces)
      (board/winner? board char)
      (board/winner? board opp-char)))

(defn calc-max-depth [size]
  (if (= 3 size) 8 3))

(defn minimax [board {:keys [char opp-char current-player depth max-depth] :as config}]
  (let [open-spaces (util/get-possible-moves board)]
    (if (done? open-spaces board char opp-char depth max-depth)
      (eval-board board depth char opp-char)
      (let [next-char (if (= current-player char) opp-char char)
            next-config (assoc config :current-player next-char :depth (inc depth))
            outcomes  (map #(minimax (assoc-in board % current-player) next-config) open-spaces)]
        (if (= current-player char)
          (apply max outcomes)
          (apply min outcomes))))))

(defn eval-moves [{:keys [board active-player-index players]}]
  (let [moves     (util/get-possible-moves board)
        char      (get-in players [active-player-index :character])
        opp-char  (if (= "X" char) "O" "X")
        max-depth (calc-max-depth (count board))
        config {:char char :opp-char opp-char :current-player opp-char :depth 0 :max-depth max-depth}]
    (map #(vector % (minimax (board/take-square board % char) config)) moves)))

(defn hard [{:keys [board active-player-index players] :as state}]
  (let [character (get-in players [active-player-index :character])
        next-play (first (apply max-key second (shuffle (eval-moves state))))]
    (assoc state :board (board/take-square board next-play character))))

(defmethod turn/take-turn :hard [state]
  (hard state))



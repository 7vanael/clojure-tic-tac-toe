(ns tic-tac-toe.computer
  (:require [tic-tac-toe.board :as board]))

(defn eval-board [board depth comp-char opp-char]
  (cond (board/winner? board comp-char) (- 10 depth)
        (board/winner? board opp-char) (- depth 10)
        :else 0))

(defn get-possible-moves [board]
  (for [x (range (count board))
        y (range (count board))
        :when (board/available? board [x y])]
    [x y]))

(defn done? [open-spaces board char opp-char depth max-depth]
  (or (= depth max-depth)
      (empty? open-spaces)
      (board/winner? board char)
      (board/winner? board opp-char)))

(defn calc-max-depth [difficulty size]
  (if (= 3 size) (min 8 (* difficulty size)) difficulty))

(defn minimax [board {:keys [char opp-char current-player depth max-depth] :as config}]
  (let [open-spaces (get-possible-moves board)]
    (if (done? open-spaces board char opp-char depth max-depth)
      (eval-board board depth char opp-char)
      (let [next-char (if (= current-player char) opp-char char)
            next-config (assoc config :current-player next-char :depth (inc depth))
            outcomes  (map #(minimax (assoc-in board % current-player) next-config) open-spaces)]
        (if (= current-player char)
          (apply max outcomes)
          (apply min outcomes))))))

(defn eval-moves [{:keys [board active-player-index players computer] :or {computer {:difficulty 3}}}]
  (let [moves     (get-possible-moves board)
        char      (get-in players [active-player-index :character])
        opp-char  (if (= "X" char) "O" "X")
        max-depth (calc-max-depth (get-in computer [:difficulty]) (count board))
        config {:char char :opp-char opp-char :current-player opp-char :depth 0 :max-depth max-depth}]
    (map #(vector % (minimax (board/take-square board % char) config)) moves)))

(defn turn [{:keys [board active-player-index players] :as state}]
  (let [character (get-in players [active-player-index :character])
        next-play (first (apply max-key second (shuffle (eval-moves state))))]
    (assoc state :board (board/take-square board next-play character))))
(ns tic-tac-toe.computer.hard
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.computer.computer-utilc :as util]))

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
  (cond (< size 10) 8
        (< size 17) 3
        :else 2))

(defn minimax [board {:keys [char opp-char current-player depth max-depth] :as config}]
  (let [open-spaces (util/get-possible-moves board)]
    (if (done? open-spaces board char opp-char depth max-depth)
      (eval-board board depth char opp-char)
      (let [next-char   (if (= current-player char) opp-char char)
            next-config (assoc config :current-player next-char :depth (inc depth))
            outcomes    (map #(minimax (assoc-in board % current-player) next-config) open-spaces)]
        (if (= current-player char)
          (apply max outcomes)
          (apply min outcomes))))))

(defn winning-spaces [board line char]
  (let [values                        (map #(get-in board %) line)
        avail-options                 (filter #(board/available? board %) line)
        already-claimed-pos-in-line   (count (filter #(= char %) values))
        size                          (count board)
        only-one-space-left           (= 1 (count avail-options))
        all-but-one-space-is-matching (= (dec size) already-claimed-pos-in-line)]
    (when (and only-one-space-left all-but-one-space-is-matching)
      (first avail-options))))

(defn winning-moves [board char score]
  (let [all-lines          (board/get-all-lines board)
        winnable-positions (keep #(winning-spaces board % char) all-lines)]
    (mapv (fn [space] [space score]) winnable-positions)))

(defn eval-moves [{:keys [board active-player-index players]}]
  (let [moves          (util/get-possible-moves board)
        char           (get-in players [active-player-index :character])
        opp-char       (if (= "X" char) "O" "X")
        max-depth      (calc-max-depth (count (flatten board)))
        config         {:char char :opp-char opp-char :current-player opp-char :depth 0 :max-depth max-depth}
        scored-moves   (map #(vector % (minimax (board/take-square board % char) config)) moves)
        best-score     (second (apply max-key second scored-moves))
        blocking-moves (winning-moves board opp-char 0)]
    (if (> -7 best-score)
      blocking-moves
      scored-moves)))

(defn hard [{:keys [board active-player-index players] :as state}]
  (let [character (get-in players [active-player-index :character])
        next-play (if (and (> (count (flatten board)) 16) (board/available? board [1 1 1]))
                    [1 1 1]
                    (first (apply max-key second (shuffle (eval-moves state)))))]
    (assoc state :board (board/take-square board next-play character))))

(defmethod core/take-computer-turn :hard [state]
  (hard state))

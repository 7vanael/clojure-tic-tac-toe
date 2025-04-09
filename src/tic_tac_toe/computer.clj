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

(defn game-over? [open-spaces board char opp-char]
  (or (empty? open-spaces)
      (board/winner? board char)
      (board/winner? board opp-char)))

(defn minimax [board char opp-char current-player depth]
  (let [open-spaces (get-possible-moves board)]
    (if (game-over? open-spaces board char opp-char)
      (eval-board board depth char opp-char)
      (let [next-char (if (= current-player char) opp-char char)
            outcomes  (map #(minimax (assoc-in board % current-player) char opp-char next-char (inc depth)) open-spaces)]
        (if (= current-player char)
          (apply max outcomes)
          (apply min outcomes))))))

(defn open-corners [board]
  (let [max-index (dec (count board))
        corners   [[0 0] [0 max-index] [max-index 0] [max-index max-index]]]
    (keep #(when (board/available? board %) %) corners)))

(defn add-random-score [spaces]
  (mapv (fn [space] [space (rand-int 10)]) spaces))

(defn next-location [location step]
  [(+ (first location) (first step)) (+ (second location) (second step))])

(defn get-line [start step max]
  (take max (iterate (partial next-location step) start)))

(defn get-rows [size]
  (map (fn [x] (get-line [x 0] [0 1] size)) (range size)))

(defn get-cols [size]
  (map (fn [y] (get-line [0 y] [1 0] size)) (range size)))

(defn get-diags [size]
  [(get-line [0 0] [1 1] size)
   (get-line [0 (dec size)] [1 -1] size)])

(defn get-all-lines [board]
  (let [size (count board)]
    (concat (get-rows size) (get-cols size) (get-diags size))))

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
  (let [all-lines          (get-all-lines board)
        winnable-positions (keep #(winning-spaces board % char) all-lines)]
    (mapv (fn [space] [space score]) winnable-positions)))

(defn calculate-line-score [board line char]
  (let [char-count (count (filter #(= char (get-in board %)) line))]
    (if (zero? char-count) 1 (* 10 char-count))))

(defn update-position-scores [space-score-map board line line-score]
  (reduce (fn [map pos]
            (if (board/available? board pos)
              (update map pos (fn [score] (+ line-score (or score 0))))
              map))
          space-score-map
          line))

(defn building-moves [board char opp-char]
  (let [all-lines          (get-all-lines board)
        viable-lines       (remove (fn [line]
                                     (some (fn [space] (= (get-in board space) opp-char))
                                           line))
                                   all-lines)
        position-score-map (reduce (fn [space-score-map line]
                                     (let [line-score (calculate-line-score board line char)]
                                       (update-position-scores space-score-map board line line-score)))
                                   {}
                                   viable-lines)
        scored-moves (sort-by second > (seq position-score-map))
        highest-score (second (first scored-moves))
        best-moves (take-while #(= (second %) highest-score) scored-moves)]
    (if (empty? scored-moves) [] best-moves)))

(defn initial-moves [board char opp-char moves]
  (let [winner   (winning-moves board char 100)
        blocker  (winning-moves board opp-char 90)
        builders (building-moves board char opp-char)
        corners  (add-random-score (open-corners board))
        opp-builders (building-moves board opp-char char)]
    (cond (not (empty? winner)) winner
          (not (empty? blocker)) blocker
          (not (empty? builders)) builders
          (not (empty? corners)) corners
          (not (empty? opp-builders)) opp-builders
          :else (add-random-score moves))))

(defn eval-moves [board char opp-char]
  (let [moves            (get-possible-moves board)
        minimax-threshold 9]
    (if (< (count moves) minimax-threshold)
      (map #(vector % (minimax (board/take-square board % char) char opp-char opp-char 0)) moves)
      (initial-moves board char opp-char moves))))

(defn turn [{:keys [board active-player-index] :as state}]
  (let [character (get-in state [:players active-player-index :character])
        opp-char  (if (= "X" character) "O" "X")
        next-play (first (apply max-key second (eval-moves board character opp-char)))]
    (assoc state :board (board/take-square board next-play character))))
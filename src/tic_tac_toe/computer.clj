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
            outcomes (map #(minimax (assoc-in board % current-player) char opp-char next-char (inc depth)) open-spaces)]
        (if (= current-player char)
          (apply max outcomes)
          (apply min outcomes))))))

(defn open-edges [board]
  (let [max-index (dec (count board))
        top-edge (map (fn [x] [0 x]) (range 1 max-index))
        bottom-edge (map (fn [x] [max-index x]) (range 1 max-index))
        left-edge (map (fn [x] [x 0]) (range 1 max-index))
        right-edge (map (fn [x] [x max-index]) (range 1 max-index))
        edges (concat top-edge bottom-edge left-edge right-edge)]
    (keep #(when (board/available? board %) %) edges)))

(defn open-corners [board]
  (let [max-index (dec (count board))
        corners [[0 0] [0 max-index] [max-index 0] [max-index max-index]]]
    (keep #(when (board/available? board %) %) corners)))

(defn add-random-score [spaces]
  (mapv  (fn [space] [space (rand-int 10)]) spaces))

(defn block? [board opp-char]
  (let [max-index (dec (count board))
        col ()]))

(defn initial-moves [board char opp-char moves]
  (let [corners (add-random-score (open-corners board))
        edges (add-random-score (open-edges board))]
    (if (empty? corners)
      edges
      corners)))

(defn eval-moves [board char opp-char]
  (let [moves   (get-possible-moves board)
        minmax-threshold (if (= 3 (count board)) 9 13)]
    (if (< (count moves) minmax-threshold)
      (map #(vector % (minimax (board/take-square board % char) char opp-char opp-char 0)) moves)
      (initial-moves board char opp-char moves))))

(defn turn [{:keys [board active-player-index] :as state}]
  (let [character (get-in state [:players active-player-index :character])
        opp-char  (if (= "X" character) "O" "X")
        next-play (first (apply max-key second (eval-moves board character opp-char)))]
    (assoc state :board (board/take-square board next-play character))))
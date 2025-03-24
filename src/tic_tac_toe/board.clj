(ns tic-tac-toe.board)

(defn new-board []
  [[nil nil nil]
   [nil nil nil]
   [nil nil nil]])

(defn available? [board [x y]]
  (nil? (get-in board [x y])))

(defn take-square [board [x y] character]
  (if (available? board [x y])
    (assoc-in board [x y] character)
    board))

(defn any-space-available? [board]
  (some nil? (flatten board)))

(defn win-row? [board character]
  (some (partial every? #(= % character)) board))

(defn win-column? [board character]
  (win-row? (apply mapv vector board) character))

(defn next-location [location step]
  [(+ (first location) (first step)) (+ (second location) (second step))])

(defn win-diag? [board character]
  (let [diag (take (count board) (iterate (partial next-location [1 1]) [0 0]))
        ortho-diag (take (count board) (iterate (partial next-location [1 -1]) [0 (dec (count board))]))]
    (or (every? #(= character (get-in board %)) diag)
        (every? #(= character (get-in board %)) ortho-diag))))

(defn winner? [board character]
  (or (win-row? board character) (win-column? board character) (win-diag? board character)))

#_(defn game-over? [board character]
  (or (winner? board character) (not (any-space-available? board))))
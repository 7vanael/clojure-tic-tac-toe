(ns tic-tac-toe.board)
;[z x y]

(defn new-2d-board [size]
  (mapv vec (partition size (range 1 (inc (* size size))))))
;mapv vec is needed to ensure we get a vector of vectors, not a list

(defn new-3d-board [size]
  (vec (for [z (range size)]
         (vec (for [x (range size)]
                (vec (for [y (range size)]
                       (+ 1 y (* size x) (* size size z)))))))))

(defn get-size-complexity [size]
  (if (sequential? size) (count size) :single-digit))

(defn new-board [size]
  (if (= :single-digit (get-size-complexity size))
    (new-2d-board size)
    (new-3d-board (first size))))

(defn available? [board coordinates]
    (number? (get-in board coordinates)))

(defn play-options [board]
  (filter number? (flatten board)))

(defn space->2d-coordinates [number board]
  (let [width (count board)
        x     (quot (dec number) width)
        y     (rem (dec number) width)]
    [x y]))

(defn space->3d-coordinates [number board]
  (let [single-dimension (count board)
        single-slice (* single-dimension single-dimension)
        z (quot (dec number) single-slice)
        one-board (rem (dec number) single-slice)
        x (quot one-board single-dimension)
        y (rem one-board single-dimension)]
    [z x y]))

(defn get-board-complexity [board]
  (if (vector? (first (first board))) 3 2))

(defn space->coordinates [number board]
  (if (= 2 (get-board-complexity board))
    (space->2d-coordinates number board)
    (space->3d-coordinates number board)))

(defn take-square [board coordinates character]
  (if (available? board coordinates)
    (assoc-in board coordinates character)
    board))

(defn any-space-available? [board]
  (some number? (flatten board)))

(defn win-row? [board character]
  (some (partial every? #(= % character)) board))

(defn win-column? [board character]
  (win-row? (apply mapv vector board) character))

(defn next-location [location step]
  (mapv + location step))

(defn win-diag? [board character]
  (let [diag       (take (count board) (iterate (partial next-location [1 1]) [0 0]))
        ortho-diag (take (count board) (iterate (partial next-location [1 -1]) [0 (dec (count board))]))]
    (or (every? #(= character (get-in board %)) diag)
        (every? #(= character (get-in board %)) ortho-diag))))


(defn winner-2d? [board character]
  (or (win-row? board character)
      (win-column? board character)
      (win-diag? board character)))

(defn winner-3d? [board character]
  #_(or (win-3d-row? board character)
      (win-3d-column? board character)
      (win-3d-z-line? board character)
      (win-3d-diag? board character)))

(defn winner? [board character]
    (if (= 2 (get-board-complexity board))
      (winner-2d? board character)
      (winner-3d? board character)))

  (defn evaluate-board [{:keys [board active-player-index players] :as state}]
    (cond (winner? board (get-in players [active-player-index :character])) (assoc state :status :winner)
          (not (any-space-available? board)) (assoc state :status :tie)
          :else state))
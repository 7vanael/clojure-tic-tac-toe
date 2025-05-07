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
        single-slice     (* single-dimension single-dimension)
        z                (quot (dec number) single-slice)
        one-board        (rem (dec number) single-slice)
        x                (quot one-board single-dimension)
        y                (rem one-board single-dimension)]
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

(defn all-matching? [collection character]
  (every? #(= % character) collection))

(defn win-row? [board character]
  (some #(all-matching? % character) board))

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

(defn win-3d-panel? [board character]
  (some #(winner-2d? % character) board))

(defn get-z-line [board [x y]]
  (let [start              [0 x y]
        step               [1 0 0]
        z-line-coordinates (take (count board) (iterate (partial next-location step) start))]
    (map #(get-in board %) z-line-coordinates)))

(defn win-3d-z-line? [board character]
  (let [xy-pairs (mapcat #(map (partial vector %) (range (count board))) (range (count board)))
        z-lines  (map #(get-z-line board %) xy-pairs)]
    (some #(all-matching? % character) z-lines)))

(defn z-plane-diags [z size]
  [[[z 0 0] [0 1 1]]
   [[z 0 (dec size)] [0 1 -1]]])

(defn y-plane-diags [y size]
  [[[0 0 y] [1 1 0]]
   [[(dec size) 0 y] [-1 1 0]]])

(defn x-plane-diags [x size]
  [[[0 x 0] [1 0 1]]
   [[0 x (dec size)] [1 0 -1]]])

(defn start-step->values [board start step]
  (let [coordinates (take (count board) (iterate (partial next-location step) start))]
    (map #(get-in board %) coordinates)))

(defn win-3d-diag? [board character]
  (let [size          (count board)
        cube-diags    [[[0 0 0] [1 1 1]]
                       [[0 0 (dec size)] [1 1 -1]]
                       [[0 (dec size) 0] [1 -1 1]]
                       [[0 (dec size) (dec size)] [1 -1 -1]]]
        plane-x-diags (mapcat #(x-plane-diags % size) (range size))
        plane-y-diags (mapcat #(y-plane-diags % size) (range size))
        plane-z-diags (mapcat #(z-plane-diags % size) (range size))
        all-diags     (concat cube-diags plane-x-diags plane-y-diags plane-z-diags)
        diag-values   (map #(start-step->values board (first %) (second %)) all-diags)]
    (boolean (some #(all-matching? % character) diag-values)))) ;nil -> false for consistency with 2d

(defn winner-3d? [board character]
  (or (win-3d-panel? board character)
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
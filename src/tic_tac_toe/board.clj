(ns tic-tac-toe.board)

#_(defn new-board [size]
  (mapv vec (partition size (range 1 (inc (* size size))))))
;mapv vec is needed to ensure we get a vector of vectors, not a list

(defn get-size-complexity [size]
  (if (sequential? size) (count size) :single-digit))

(defmulti new-board get-size-complexity)

(defmethod new-board :single-digit [size]
  (mapv vec (partition size (range 1 (inc (* size size))))))
;mapv vec is needed to ensure we get a vector of vectors, not a list

(defmethod new-board 3 [[size _]]
  (vec (for [z (range size)]
         (vec (for [x (range size)]
                (vec (for [y (range size)]
                       (+ 1 y (* size x) (* size size z)))))))))

#_(defn available? [board [x y]]
  (number? (get-in board [x y])))

(defn get-available-complexity [_ coordinates]
  (get-size-complexity coordinates))

(defmulti available? get-available-complexity)

(defmethod available? 2 [board [x y]]
          (number? (get-in board [x y])))

;should z just always come first? It's the panel selector
(defmethod available? 3 [board [z x y]]
  (number? (get-in board [z x y])))

(defn play-options [board]
  (filter number? (flatten board)))

#_(defn space->coordinates [number board]
  (let [width (count board)
        x     (quot (dec number) width)
        y     (rem (dec number) width)]
    [x y]))

(defn get-board-complexity [_ board]
  (if (vector? (first (first board))) 3 2))

(defmulti space->coordinates get-board-complexity)

(defmethod space->coordinates 2 [number board]
  (let [width (count board)
        x     (quot (dec number) width)
        y     (rem (dec number) width)]
    [x y]))

(defmethod space->coordinates 3 [number board]
  (let [single-dimension (count board)
        single-slice (* single-dimension single-dimension)
        z (quot (dec number) single-slice)
        one-board (rem (dec number) single-slice)
        x (quot one-board single-dimension)
        y (rem one-board single-dimension)]
    [z x y]))

#_(defn take-square [board [x y] character]
  (if (available? board [x y])
    (assoc-in board [x y] character)
    board))

(defn get-claim-complexity [_ coordinates _]
  (get-size-complexity coordinates))

(defmulti take-square get-claim-complexity)

(defmethod take-square 2 [board [x y] character]
  (if (available? board [x y])
    (assoc-in board [x y] character)
    board))

(defmethod take-square 3 [board [z x y] character]
  (if (available? board [z x y])
    (assoc-in board [z x y] character)
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

#_(defn winner? [board character]
  (or (win-row? board character)
      (win-column? board character)
      (win-diag? board character)))

(defn get-winner-complexity [board character]
  (get-board-complexity character board))

(defmulti winner? get-winner-complexity)

(defmethod winner? 2 [board character]
  (or (win-row? board character)
      (win-column? board character)
      (win-diag? board character)))

#_(defmethod winner? 3 [board character]
  (or (win-3d-row? board character)
      (win-3d-column? board character)
      (win-3d-z-line? board character)
      (win-3d-diag? board character)))

  (defn evaluate-board [{:keys [board active-player-index players] :as state}]
    (cond (winner? board (get-in players [active-player-index :character])) (assoc state :status :winner)
          (not (any-space-available? board)) (assoc state :status :tie)
          :else state))
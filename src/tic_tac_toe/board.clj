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

(defn board-3d? [board]
  (vector? (first (first board))))

(defn get-board-complexity [board]
  (if (board-3d? board) 3 2))

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

(defn get-z-lines [board]
  (let [xy-pairs (mapcat #(map (partial vector %) (range (count board))) (range (count board)))]
    (map #(get-z-line board %) xy-pairs)))

(defn win-3d-z-line? [board character]
  (let [z-lines  (get-z-lines board)]
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

(defn cube->diag-start-steps [size]
  [[[0 0 0] [1 1 1]]
   [[0 0 (dec size)] [1 1 -1]]
   [[0 (dec size) 0] [1 -1 1]]
   [[0 (dec size) (dec size)] [1 -1 -1]]])

(defn ->line-coordinates [board start step]
  (take (count board) (iterate (partial next-location step) start)))

(defn get-rows [board]
  (mapv #(->line-coordinates board [% 0] [0 1]) (range (count board))))

(defn get-cols [board]
  (mapv #(->line-coordinates board [0 %] [1 0]) (range (count board))))

(defn get-diags [board]
  [(->line-coordinates board [0 0] [1 1])
   (->line-coordinates board [0 (dec (count board))] [1 -1])])

(defn get-all-lines-2d [board]
    (concat (get-rows board) (get-cols board) (get-diags board)))

(defn cube-diags [size]
  (cube->diag-start-steps size))

(defn plane-x-diags [size]
  (mapcat #(x-plane-diags % size) (range size)))

(defn plane-y-diags [size]
  (mapcat #(y-plane-diags % size) (range size)))

(defn plane-z-diags [size]
  (mapcat #(z-plane-diags % size) (range size)))

(defn get-all-3d-diags [size]
  (concat (cube-diags size) (plane-x-diags size) (plane-y-diags size) (plane-z-diags size)))

(defn get-all-lines-3d [board]
  (let [size (count board)
        diags (get-all-3d-diags size)
        z-lines (get-z-lines board)
        panel-lines (mapv #(get-all-lines-2d %) board)]
    (concat diags z-lines panel-lines)))

(defn get-all-lines [board]
    (if (board-3d? board)
      (get-all-lines-3d board)
      (get-all-lines-2d board)))

(defn start-step->values [board start step]
    (map #(get-in board %) (->line-coordinates board start step)))

(defn winning-spaces [board line char]
  (let [values                        (map #(get-in board %) line)
        avail-options                 (filter #(available? board %) line)
        already-claimed-pos-in-line   (count (filter #(= char %) values))
        size                          (count board)
        only-one-space-left           (= 1 (count avail-options))
        all-but-one-space-is-matching (= (dec size) already-claimed-pos-in-line)]
    (when (and only-one-space-left all-but-one-space-is-matching)
      (first avail-options))))

;score is what score you want assigned to all winning-moves
; pass in opponent's-char to find blocking moves instead
(defn winning-moves [board char score]
  (let [all-lines          (get-all-lines board)
        winnable-positions (keep #(winning-spaces board % char) all-lines)]
    (mapv (fn [space] [space score]) winnable-positions)))

(defn win-3d-diag? [board character]
  (let [size          (count board)
        all-diags     (get-all-3d-diags size)
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
(ns tic-tac-toe.gui.draw
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defmethod core/draw-state [:gui :welcome] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 32)
  (q/text "Welcome to Tic-Tac-Toe" (/ (q/width) 2) (/ (q/height) 2))
  (q/text-size 16)
  (q/text "Click anywhere to continue" (/ (q/width) 2) (+ (/ (q/height) 2) 40)))

(def yes-no-labels ["Yes" "No"])

(defmethod core/draw-state [:gui :found-save] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Would you like to resume your last game?" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons yes-no-labels))


(def type-labels ["Human" "Computer"])
(def difficulty-options ["Easy" "Medium" "Hard"])


(defmethod core/draw-state [:gui :config-x-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/draw-state [:gui :config-x-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(defmethod core/draw-state [:gui :config-o-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/draw-state [:gui :config-o-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(def board-labels ["3 X 3" "4 X 4" "3 X 3 X 3"])

(defmethod core/draw-state [:gui :select-board] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-3-buttons board-labels))


(def play-again-labels ["Play Again" "Exit"])

(defmethod core/draw-state [:gui :tie] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "It's a draw, good game!" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons play-again-labels))

(defmethod core/draw-state [:gui :winner] [{:keys [active-player-index]}]
  (let [current-player (if (= active-player-index 0) "X" "O")
        title-text     (str current-player " wins! Good game!")]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (util/draw-2-options-buttons play-again-labels)))
;
; in-progress starts here
;
(def usable-screen util/screen-width)
(def grid-origin-x 0)
(def layer-margin 18)
(def grid-origin-y-2d (- util/screen-height usable-screen))
(def grid-origin-y-3d (+ (* util/title-offset-y 2) layer-margin))

(defn board-3d? [board] (vector? (get-in board [0 0] nil)))

(defn ->horiz-line-points [start-y start-x end-x cell-size i]
  (let [y (+ start-y (* i cell-size))]
    [start-x y end-x y]))

(defn get-horizontal-lines [start-y start-x end-x cell-size count]
  (map #(->horiz-line-points start-y start-x end-x cell-size %)
       (range (inc count))))

(defn ->vert-line-points [start-x cell-size start-y end-y i]
  (let [x (+ start-x (* i cell-size))]
    [x start-y x end-y]))

(defn get-vertical-lines [start-y end-y cell-size count start-x]
  (map #(->vert-line-points start-x cell-size start-y end-y %)
       (range (inc count))))

(defn layer-lines [size z-layer]
  (let [layer-width (/ (- usable-screen (* 2 layer-margin)) 3)
        start-y     grid-origin-y-3d
        end-y       (+ start-y layer-width)
        start-x     (* z-layer (+ layer-margin layer-width))
        end-x       (+ start-x layer-width)
        cell-size   (/ layer-width size)
        horiz-lines (get-horizontal-lines start-y start-x end-x cell-size size)
        vert-lines  (get-vertical-lines start-y end-y cell-size size start-x)]
    (concat horiz-lines vert-lines)))

(defn get-lines-3d [size]
  (mapcat #(layer-lines size %) (range size)))

(defn get-lines-2d [board]
  (let [start-y         (* util/title-offset-y 2)
        end-y           util/screen-height
        board-dimension util/screen-width
        start-x         0
        end-x           board-dimension
        cell-size       (/ board-dimension (count board))
        horiz-lines     (get-horizontal-lines start-y start-x end-x cell-size (count board))
        vert-lines      (get-vertical-lines start-y end-y cell-size (count board) start-x)]
    (concat horiz-lines vert-lines)))

(defn get-lines [board]
  (if (board-3d? board)
    (get-lines-3d (count board))
    (get-lines-2d board)))

(defn draw-grid [cells]
  (doseq [{:keys [x y value]} cells]
    (when (or (= value "X") (= value "O"))
      (q/text-align :center :center)
      (q/text value x y))))

(defn draw-lines [lines]
  (q/stroke 0)
  (q/stroke-weight 2)
  (doseq [[x1 y1 x2 y2] lines]
    (q/line x1 y1 x2 y2)))

(defn generate-cells-2d [board cell-size [origin-x origin-y]]
  (let [board-size (count board)
        [first-x first-y] [(+ origin-x (/ cell-size 2)) (+ origin-y (/ cell-size 2))]] ;shifts from corner to center
    (for [row (range board-size)
          col (range board-size)]
      {:x     (+ first-x (* col cell-size))
       :y     (+ first-y (* row cell-size))
       :value (get-in board [row col])})))

(defn layer-cells [board cell-size z-layer [origin-x origin-y]]
  (let [dimension     (count (get board 0))
        layer-width   (/ (- usable-screen (* 2 layer-margin)) 3)
        layer-start-x (+ origin-x (* z-layer (+ layer-width layer-margin)))
        [first-x first-y] [(+ layer-start-x (/ cell-size 2))
                           (+ origin-y (/ cell-size 2))]]
    (for [row (range dimension)
          col (range dimension)]
      {:x     (+ first-x (* col cell-size))
       :y     (+ first-y (* row cell-size))
       :z     z-layer
       :value (get-in board [z-layer row col])})))

(defn generate-cells-3d [board cell-size [origin-x origin-y]]
  (mapcat #(layer-cells board cell-size % [origin-x origin-y]) (range 3)))

(defn calculate-cell-size [is-3d? board]
  (if is-3d? (/ (/ (- usable-screen (* 2 layer-margin)) 3) (count board)) (/ usable-screen (count board))))

(defn generate-cells [board]
  (let [is-3d?    (board-3d? board)
        cell-size (calculate-cell-size is-3d? board)]
    (if is-3d? (generate-cells-3d board cell-size [grid-origin-x grid-origin-y-3d])
               (generate-cells-2d board cell-size [grid-origin-x grid-origin-y-2d]))))

(defmethod core/draw-state [:gui :in-progress] [{:keys [board active-player-index players]}]
  (let [character  (get-in players [active-player-index :character])
        title-text (str character "'s turn")
        lines      (get-lines board)
        is-3d?     (board-3d? board)
        cell-size  (calculate-cell-size is-3d? board)
        cells      (generate-cells board)]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (draw-lines lines)
    (q/text-size (if is-3d? (/ cell-size 3) (/ cell-size 2)))
    (draw-grid cells)))
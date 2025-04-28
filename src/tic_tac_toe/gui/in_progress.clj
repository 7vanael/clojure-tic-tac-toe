(ns tic-tac-toe.gui.in-progress
  (:require [quil.core :as q]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.board :as board]))

(defn ->horiz-line-points [start-y start-x end-x cell-size i]
  (let [y (+ start-y (* i cell-size))]
    [start-x y end-x y]))

(defn get-horizontal-lines [start-y start-x end-x cell-size count]
  (map #(->horiz-line-points start-y start-x end-x cell-size %)
       (range (inc count))))

(defn ->vert-line-points [cell-size start-y end-y i]
  (let [x (* i cell-size)]
    [x start-y x end-y]))


(defn get-vertical-lines [start-y end-y cell-size count]
  (map #(->vert-line-points cell-size start-y end-y %)
       (range (inc count))))

(defn get-lines [board]
  (let [start-y         (* util/title-offset-y 2)
        end-y           util/screen-height
        board-dimension util/screen-width
        start-x         0
        end-x           board-dimension
        cell-size       (/ board-dimension (count board))
        horiz-lines     (get-horizontal-lines start-y start-x end-x cell-size (count board))
        vert-lines      (get-vertical-lines start-y end-y cell-size (count board))]
    (concat horiz-lines vert-lines)))

(defn generate-cells [board cell-size [origin-x origin-y]]
  (let [board-size (count board)
        [first-x first-y] [(+ origin-x (/ cell-size 2)) (+ origin-y (/ cell-size 2))]] ;shifts from corner to center
    (for [row (range board-size)
          col (range board-size)]
      {:x     (+ first-x (* col cell-size))
       :y     (+ first-y (* row cell-size))
       ;:size  cell-size
       :value (get-in board [row col])})))

(defn draw-grid [cells]
  (doseq [{:keys [x y value]} cells]
    (when (or (= value "X") (= value "O"))
      (q/text value x y))))

(defn draw-lines [lines]
  (q/stroke 0)
  (q/stroke-weight 2)
  (doseq [[x1 y1 x2 y2] lines]
    (q/line x1 y1 x2 y2)))

(defmethod multis/draw-state :in-progress [{:keys [board active-player-index players] :as state}]
  (let [character     (get-in players [active-player-index :character])
        title-text    (str character "'s turn")
        lines         (get-lines board)
        usable-screen util/screen-width
        grid-origin-x 0
        grid-origin-y (- util/screen-height usable-screen)
        cell-size     (/ usable-screen (count board))
        cells         (generate-cells board cell-size [grid-origin-x grid-origin-y])]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (draw-lines lines)
    (q/text-size (/ cell-size 2))
    (draw-grid cells)))

(defmethod multis/update-state :in-progress [{:keys [board] :as state}]
  (let [play-options (board/play-options board)])
  state)

(defmethod multis/mouse-clicked :in-progress [state {:keys [x y]}]
  state)
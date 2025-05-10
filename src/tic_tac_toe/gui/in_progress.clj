(ns tic-tac-toe.gui.in-progress
  (:require [quil.core :as q :include-macros true]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence :as persistence]))


(def usable-screen util/screen-width)
(def grid-origin-x 0)
(def grid-origin-y (- util/screen-height usable-screen))
(def layer-margin 18)

(defn board-3d? [board] (vector? (get-in board [0 0] nil)))

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

(defn layer-lines [size z-layer]
  (let [layer-width (/ (- usable-screen (* 2 layer-margin)) 3)
        start-y (+ (* util/title-offset-y 2) layer-margin)
        end-y (- util/screen-height layer-margin)
        start-x (* z-layer (+ layer-margin layer-width))
        end-x (+ start-x layer-width)
        cell-size (/ layer-width size)
        horiz-lines (get-horizontal-lines start-y start-x end-x cell-size size)
        vert-lines (get-vertical-lines start-y end-y cell-size size)]
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
        vert-lines      (get-vertical-lines start-y end-y cell-size (count board))]
    (concat horiz-lines vert-lines)))

(defn get-lines [board]
  (if (board-3d? board)
    board;(get-lines-3d board)
    (get-lines-2d board)))

(defn draw-grid [cells]
  (doseq [{:keys [x y value]} cells]
    (when (or (= value "X") (= value "O"))
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

(defn generate-cells [board cell-size [origin-x origin-y]]
  (if (board-3d? board)
    board;(generate-cells-3d board cell-size [origin-x origin-y])
    (generate-cells-2d board cell-size [origin-x origin-y])))

(defmethod multis/draw-state :in-progress [{:keys [board active-player-index players] :as state}]
  (let [character     (get-in players [active-player-index :character])
        title-text    (str character "'s turn")
        lines         (get-lines board)
        cell-size     (/ usable-screen (count board))
        cells         (generate-cells board cell-size [grid-origin-x grid-origin-y])
        updated-state (assoc state :cells cells)]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (draw-lines lines)
    (q/text-size (/ cell-size 2))
    (draw-grid cells)
    updated-state))


(defmethod multis/mouse-clicked :in-progress [{:keys [board active-player-index players] :as state} {:keys [x y]}]
  (let [play-options (set (board/play-options board))
        cell-size    (/ usable-screen (count board))
        relative-x   (- x grid-origin-x)
        relative-y   (- y grid-origin-y)
        clicked-col  (int (/ relative-x cell-size))
        clicked-row  (int (/ relative-y cell-size))
        value        (get-in board [clicked-row clicked-col])
        player-char  (get-in players [active-player-index :character])
        player-type  (get-in players [active-player-index :play-type])]
    (if (and (contains? play-options value) (= :human player-type))
      (-> state
          (assoc :board (board/take-square board (board/space->coordinates value board) player-char))
          board/evaluate-board
          core/change-player
          persistence/save-game)
      state)))

(defmethod core/take-human-turn :gui [state]
  (core/update-state state))

(defmethod core/update-state [:gui :in-progress] [state]
  (if (core/currently-human? state)
    state
    (-> state
        core/take-turn
        board/evaluate-board
        core/change-player
        persistence/save-game)))

(defmethod multis/draw-state :board-ready [state]
  (multis/draw-state (assoc state :status :in-progress)))

(defmethod core/update-state [:gui :board-ready] [state]
  (assoc state :status :in-progress))
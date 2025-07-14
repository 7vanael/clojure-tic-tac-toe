(ns tic-tac-toe.gui.cells
  (:require [tic-tac-toe.gui.gui-util :as util]))

(def usable-screen util/screen-width)
(def grid-origin-x 0)
(def layer-margin 18)
(def grid-origin-y-2d (- util/screen-height usable-screen))
(def grid-origin-y-3d (+ (* util/title-offset-y 2) layer-margin))

(defn board-3d? [board] (vector? (get-in board [0 0] nil)))

(defn cell-contains-point? [{cell-x :x cell-y :y} x y half-size]
  (and (<= (- cell-x half-size) x (+ cell-x half-size))
       (<= (- cell-y half-size) y (+ cell-y half-size))))

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

(defn find-clicked-cell [board x y]
  (let [is-3d?    (board-3d? board)
        cells     (generate-cells board)
        cell-size (calculate-cell-size is-3d? board)
        half-size (/ cell-size 2)]
    (first (filter #(cell-contains-point? % x y half-size) cells))))
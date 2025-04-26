(ns tic-tac-toe.gui.gui-util
  (:require [quil.core :as q]))


(def screen-size 720)

(defn button-clicked? [[x y] [x1 y1 width height]]
  (and (> x x1) (< x (+ x1 width))
       (> y y1) (< y (+ y1 height))))


(defn draw-button [label x y width height]
  (q/rect-mode :center)
  (q/no-stroke)
  (q/fill 200 200 250)                                      ;;background color of button
  (q/rect x y width height)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 10)
  (q/text label x y))
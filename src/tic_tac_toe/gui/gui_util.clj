(ns tic-tac-toe.gui.gui-util
  (:require [quil.core :as q]))


(def screen-size 720)

(defn button-clicked? [[click-x click-y] [center-x center-y rect-width rect-height]]
  (prn "[click-x click-y]:" [click-x click-y])
  (prn "[center-x center-y rect-width rect-height]:" [center-x center-y rect-width rect-height])
  (let [min-x (- center-x (/ rect-width 2))
        max-x (+ center-x (/ rect-width 2))
        min-y (- center-y (/ rect-height 2))
        max-y (+ center-y (/ rect-height 2))]
    (and (>= click-x min-x) (<= click-x max-x)
       (>= click-y min-y) (<= click-y max-y))))


(defn draw-button [label [x y width height]]
  (q/rect-mode :center)
  (q/no-stroke)
  (q/fill 200 200 250)                                      ;;background color of button
  (q/rect x y width height)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 10)
  (q/text label x y))
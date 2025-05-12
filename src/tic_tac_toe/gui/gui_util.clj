(ns tic-tac-toe.gui.gui-util
  (:require [quil.core :as q]))

(def initial-state
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

(def screen-height 720)
(def title-offset-y 72)
(def screen-width (- screen-height (* title-offset-y 2)))

(defn button-clicked? [[click-x click-y] [center-x center-y rect-width rect-height]]
  (let [min-x (- center-x (/ rect-width 2))
        max-x (+ center-x (/ rect-width 2))
        min-y (- center-y (/ rect-height 2))
        max-y (+ center-y (/ rect-height 2))]
    (and (>= click-x min-x) (<= click-x max-x)
       (>= click-y min-y) (<= click-y max-y))))


(defn draw-button [label [x y width height]]
  (q/rect-mode :center)
  (q/no-stroke)
  (q/fill 200 200 250)
  (q/rect x y width height)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 10)
  (q/text label x y))


(def button-width 120)
(def button-height 50)
(def center-x (/ screen-width 2))
(def button-type-y 350)

(def opt1-of-2-x-center (/ center-x 2))
(def opt2-of-2-x-center (+ center-x opt1-of-2-x-center))

(def opt1-of-2-rect [opt1-of-2-x-center button-type-y button-width button-height])
(def opt2-of-2-rect [opt2-of-2-x-center button-type-y button-width button-height])


(defn draw-2-options-buttons [labels]
  (draw-button (first labels) opt1-of-2-rect)
  (draw-button (second labels) opt2-of-2-rect))

(def start-y (- screen-height screen-width))
(def button-offsets-y (/ screen-width 4))

(def button-easy-y-center (+ start-y button-offsets-y))
(def button-medium-y-center (+ button-easy-y-center button-offsets-y))
(def button-hard-y-center (+ button-medium-y-center button-offsets-y))

(def opt1-of-3-rect [center-x button-easy-y-center button-width button-height])
(def opt2-of-3-rect [center-x button-medium-y-center button-width button-height])
(def opt3-of-3-rect [center-x button-hard-y-center button-width button-height])

(defn draw-3-buttons [[label1 label2 label3]]
  (draw-button label1 opt1-of-3-rect)
  (draw-button label2 opt2-of-3-rect)
  (draw-button label3 opt3-of-3-rect))
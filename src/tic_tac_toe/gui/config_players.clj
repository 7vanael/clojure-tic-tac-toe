(ns tic-tac-toe.gui.config-players
  (:require [quil.core :as q]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-util :as util]))

(def button-width 120)
(def button-height 50)
(def button-type-y 350)

(def center-x (/ util/screen-size 2))
(def human-x-center (/ center-x 2))
(def computer-x-center (+ center-x human-x-center))

;Top left corners of the buttons
(def human-rect-corner-x (- human-x-center (/ button-width 2)))
(def human-rect-corner-y (- button-type-y (/ button-height 2)))
(def computer-rect-corner-x (* 3 (/ util/screen-size 4)))
(def computer-rect-corner-y (- button-type-y (/ button-height 2)))

(def title-offset-y 72)
(def remaining-y-range (- util/screen-size title-offset-y))
(def button-offsets-y (/ remaining-y-range 3))

(def difficulty-rect-corner-x (- center-x (/ button-width 2)))
(def button-easy-y-center (+ title-offset-y button-offsets-y))
(def button-medium-y-center (+ button-easy-y-center button-offsets-y))
(def button-hard-y-center (+ button-medium-y-center button-offsets-y))

(def human-rect [human-rect-corner-x human-rect-corner-y button-width button-height])
(def computer-rect [computer-rect-corner-x computer-rect-corner-y button-width button-height])
(def easy-rect [difficulty-rect-corner-x (- button-easy-y-center (/ button-height 2)) button-width button-height])
(def medium-rect [difficulty-rect-corner-x (- button-medium-y-center (/ button-height 2)) button-width button-height])
(def hard-rect [difficulty-rect-corner-x (- button-hard-y-center (/ button-height 2)) button-width button-height])

(defn draw-difficulty-buttons []
  (util/draw-button "Easy" difficulty-rect-corner-x (- button-easy-y-center (/ button-height 2)) button-width button-height)
  (util/draw-button "Medium" difficulty-rect-corner-x (- button-medium-y-center (/ button-height 2)) button-width button-height)
  (util/draw-button "Hard" difficulty-rect-corner-x (- button-hard-y-center (/ button-height 2)) button-width button-height))

(defn draw-type-buttons []
  (util/draw-button "Human" human-x-center button-type-y button-width button-height)
  (util/draw-button "Computer" computer-x-center button-type-y button-width button-height))

(defmethod multis/draw-state :config-x-type [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" (/ util/screen-size 2) title-offset-y)
  (draw-type-buttons))

(defmethod multis/update-state :config-x-type [state]
  state)

(defmethod multis/mouse-clicked :config-x-type [state {:keys [x y] :as event}]
  (cond (util/button-clicked? [x y] human-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :human)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] computer-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :computer)
            (assoc :status :config-x-difficulty))
        :else state))

(defmethod multis/draw-state :config-x-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" (/ util/screen-size 2) title-offset-y)
  (draw-difficulty-buttons))

(defmethod multis/update-state :config-x-difficulty [state]
  state)

(defmethod multis/mouse-clicked :config-x-difficulty [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] easy-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :easy)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] medium-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :medium)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] hard-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :hard)
            (assoc :status :config-o-type))
        :else state))

(defmethod multis/draw-state :config-o-type [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Type" (/ util/screen-size 2) title-offset-y)
  (draw-type-buttons))

(defmethod multis/update-state :config-o-type [state]
  state)

(defmethod multis/mouse-clicked :config-o-type [state {:keys [x y] :as event}]
  (cond (util/button-clicked? [x y] human-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :human)
            (assoc :status :select-board))
        (util/button-clicked? [x y] computer-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :computer)
            (assoc :status :config-o-difficulty))
        :else state))

(defmethod multis/draw-state :config-o-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" (/ util/screen-size 2) title-offset-y)
  (draw-difficulty-buttons))

(defmethod multis/update-state :config-o-difficulty [state]
  state)

(defmethod multis/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] easy-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :easy)
            (assoc :status :select-board))
        (util/button-clicked? [x y] medium-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :medium)
            (assoc :status :select-board))
        (util/button-clicked? [x y] hard-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :hard)
            (assoc :status :select-board))
        :else state))
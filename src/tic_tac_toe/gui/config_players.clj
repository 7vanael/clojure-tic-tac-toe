(ns tic-tac-toe.gui.config-players
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))



(def center-x (/ util/screen-width 2))
(def start-y (- util/screen-height util/screen-width))
(def button-offsets-y (/ util/screen-width 4))

(def button-easy-y-center (+ start-y button-offsets-y))
(def button-medium-y-center (+ button-easy-y-center button-offsets-y))
(def button-hard-y-center (+ button-medium-y-center button-offsets-y))

(def easy-rect [center-x button-easy-y-center util/button-width util/button-height])
(def medium-rect [center-x button-medium-y-center util/button-width util/button-height])
(def hard-rect [center-x button-hard-y-center util/button-width util/button-height])

(defn draw-difficulty-buttons []
  (util/draw-button "Easy" easy-rect)
  (util/draw-button "Medium" medium-rect)
  (util/draw-button "Hard" hard-rect))

(def type-labels ["Human" "Computer"])


(defmethod multis/draw-state :config-x-type [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :config-x-type] [state]
  state)

(defmethod multis/mouse-clicked :config-x-type [state {:keys [x y] :as event}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :human)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :computer)
            (assoc :status :config-x-difficulty))
        :else state))

(defmethod multis/draw-state :config-x-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" center-x util/title-offset-y)
  (draw-difficulty-buttons))

(defmethod core/update-state [:gui :config-x-difficulty] [state]
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
  (q/text "Choose Player O Type" center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :config-o-type] [state]
  state)

(defmethod multis/mouse-clicked :config-o-type [state {:keys [x y] :as event}]
  (core/inspect state)
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :human)
            (assoc :status :select-board))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :computer)
            (assoc :status :config-o-difficulty))
        :else state))

(defmethod multis/draw-state :config-o-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" center-x util/title-offset-y)
  (draw-difficulty-buttons))

(defmethod core/update-state [:gui :config-o-difficulty] [state]
  state)

(defmethod multis/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (core/inspect state)
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
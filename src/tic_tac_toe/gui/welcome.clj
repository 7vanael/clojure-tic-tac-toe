(ns tic-tac-toe.gui.welcome
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core :as core]))


(defmethod multis/mouse-clicked :welcome [state event]
  (assoc state :status :config-x-type))

(defmethod core/update-state [:gui :welcome] [state]
  state)

(defmethod multis/draw-state :welcome [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 32)
  (q/text "Welcome to Tic-Tac-Toe" (/ (q/width) 2) (/ (q/height) 2))
  (q/text-size 16)
  (q/text "Click anywhere to continue" (/ (q/width) 2) (+ (/ (q/height) 2) 40)))


(ns tic-tac-toe.gui.welcome
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence :as persistence]))


(defmethod multis/mouse-clicked :welcome [state event]
  (let [saved-game (persistence/load-game)]
    (if (nil? saved-game)
    (assoc state :status :config-x-type)
    (assoc saved-game :status :found-save :interface :gui))))

(defmethod core/update-state [:gui :welcome] [state]
  state)

(defmethod multis/draw-state :welcome [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 32)
  (q/text "Welcome to Tic-Tac-Toe" (/ (q/width) 2) (/ (q/height) 2))
  (q/text-size 16)
  (q/text "Click anywhere to continue" (/ (q/width) 2) (+ (/ (q/height) 2) 40)))


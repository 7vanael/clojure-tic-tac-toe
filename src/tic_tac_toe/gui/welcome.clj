(ns tic-tac-toe.gui.welcome
  (:require [quil.core :as q]
            [tic-tac-toe.core :as core]))


(defmethod core/mouse-clicked :welcome [state _] (core/update-state state 1))

;Update game before you update the state...
(defmethod core/update-state [:gui :welcome] [state value]
  (let [saved-game (core/load-game state)]
    (cond (nil? value) state
          (nil? saved-game) (assoc state :status :config-x-type)
          :else (assoc saved-game :status :found-save :interface :gui))))

(defmethod core/draw-state [:gui :welcome] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 32)
  (q/text "Welcome to Tic-Tac-Toe" (/ (q/width) 2) (/ (q/height) 2))
  (q/text-size 16)
  (q/text "Click anywhere to continue" (/ (q/width) 2) (+ (/ (q/height) 2) 40)))


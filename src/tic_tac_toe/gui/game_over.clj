(ns tic-tac-toe.gui.game-over
  (:require [quil.core :as q]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-util :as util]))

(def type-labels ["Play Again" "Exit"])

(defmethod multis/draw-state :game-over [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-size 2) util/title-offset-y)
  (util/draw-type-buttons type-labels))

(defmethod multis/update-state :game-over [state]
  state)

(defmethod multis/mouse-clicked :game-over [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        {:interface           :gui
         :board               nil
         :active-player-index 0
         :status              :config-x-type
         :players             [{:character "X" :play-type nil :difficulty nil}
                               {:character "O" :play-type nil :difficulty nil}]
         :board-size          nil}
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (q/exit)
        :else state))

(ns tic-tac-toe.gui.select-board
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(def type-labels ["3 X 3" "4 X 4" "3 X 3 X 3"])

(defmethod multis/draw-state :select-board [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :select-board] [state]
  state)

(defmethod multis/mouse-clicked :select-board [state {:keys [x y]}]

  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc :board (board/new-board 3))
            (assoc :status :board-ready))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc :board (board/new-board 4))
            (assoc :status :board-ready))
        :else state))
(ns tic-tac-toe.gui.select-board
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(def type-labels ["3 X 3" "4 X 4" "3 X 3 X 3"])

(defmethod core/draw-state [:gui :select-board] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-3-buttons type-labels))

(defmethod core/update-state [:gui :select-board] [state value]
  (cond (= 1 value) (-> state
                        (assoc :board (board/new-board 3))
                        (assoc :status :in-progress))
        (= 2 value) (-> state
                        (assoc :board (board/new-board 4))
                        (assoc :status :in-progress))
        (= 3 value) (-> state
                        (assoc :board (board/new-board [3 3 3]))
                        (assoc :status :in-progress))
        :else state))

(defmethod core/mouse-clicked :select-board [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect) (core/update-state state 1)
        (util/button-clicked? [x y] util/opt2-of-3-rect) (core/update-state state 2)
        (util/button-clicked? [x y] util/opt3-of-3-rect) (core/update-state state 3)
        :else nil))
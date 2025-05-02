(ns tic-tac-toe.gui.winner
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["Play Again" "Exit"])

(defmethod multis/draw-state :winner [{:keys [active-player-index players] :as state}]
  (let [current-player (get-in players [active-player-index :character])
        title-text     (str current-player " wins! Good game!")]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (util/draw-2-options-buttons type-labels)))

(defmethod core/update-state [:gui :winner] [state]
  state)

(defmethod multis/mouse-clicked :winner [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) (assoc-in util/initial-state [:status] :config-x-type)
        (util/button-clicked? [x y] util/opt2-of-2-rect) (q/exit)
        :else state))

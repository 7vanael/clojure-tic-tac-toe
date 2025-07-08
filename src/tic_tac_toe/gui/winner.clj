(ns tic-tac-toe.gui.winner
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["Play Again" "Exit"])

(defmethod core/draw-state [:gui :winner] [{:keys [active-player-index] :as state}]
  (let [current-player (if (= active-player-index 0) "X" "O")
        title-text     (str current-player " wins! Good game!")]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (util/draw-2-options-buttons type-labels)))

(defmethod core/get-selection :winner [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) 1
        (util/button-clicked? [x y] util/opt2-of-2-rect) 2
        :else nil))

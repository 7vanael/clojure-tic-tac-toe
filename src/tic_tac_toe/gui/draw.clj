(ns tic-tac-toe.gui.draw
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))


(defmethod core/draw-state [:gui :welcome] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 32)
  (q/text "Welcome to Tic-Tac-Toe" (/ (q/width) 2) (/ (q/height) 2))
  (q/text-size 16)
  (q/text "Click anywhere to continue" (/ (q/width) 2) (+ (/ (q/height) 2) 40)))


(def yes-no-labels ["Yes" "No"])

(defmethod core/draw-state [:gui :found-save] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Would you like to resume your last game?" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons yes-no-labels))


(def type-labels ["Human" "Computer"])
(def difficulty-options ["Easy" "Medium" "Hard"])


(defmethod core/draw-state [:gui :config-x-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/draw-state [:gui :config-x-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(defmethod core/draw-state [:gui :config-o-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/draw-state [:gui :config-o-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(def board-labels ["3 X 3" "4 X 4" "3 X 3 X 3"])

(defmethod core/draw-state [:gui :select-board] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Board Size" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-3-buttons board-labels))



(def play-again-labels ["Play Again" "Exit"])

(defmethod core/draw-state [:gui :tie] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "It's a draw, good game!" (/ util/screen-width 2) util/title-offset-y)
  (util/draw-2-options-buttons play-again-labels))

(defmethod core/draw-state [:gui :winner] [{:keys [active-player-index]}]
  (let [current-player (if (= active-player-index 0) "X" "O")
        title-text     (str current-player " wins! Good game!")]
    (q/background 240)
    (q/fill 0)
    (q/text-align :center :center)
    (q/text-size 28)
    (q/text title-text (/ util/screen-width 2) util/title-offset-y)
    (util/draw-2-options-buttons play-again-labels)))
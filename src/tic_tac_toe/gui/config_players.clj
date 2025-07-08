(ns tic-tac-toe.gui.config-players
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(def type-labels ["Human" "Computer"])
(def difficulty-options ["Easy" "Medium" "Hard"])

(defmethod core/draw-state [:gui :config-x-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/get-selection [:gui :config-x-type] [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) 1
        (util/button-clicked? [x y] util/opt2-of-2-rect) 2
        :else nil))

(defmethod core/draw-state [:gui :config-x-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(defmethod core/get-selection :config-x-difficulty [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect) 1
        (util/button-clicked? [x y] util/opt2-of-3-rect) 2
        (util/button-clicked? [x y] util/opt3-of-3-rect) 3
        :else nil))

(defmethod core/draw-state [:gui :config-o-type] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/get-selection :config-o-type [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect) 1
        (util/button-clicked? [x y] util/opt2-of-2-rect) 2
        :else nil))

(defmethod core/draw-state [:gui :config-o-difficulty] [_]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))


(defmethod core/get-selection :config-o-difficulty [_ {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect) 1
        (util/button-clicked? [x y] util/opt2-of-3-rect) 2
        (util/button-clicked? [x y] util/opt3-of-3-rect) 3
        :else nil))
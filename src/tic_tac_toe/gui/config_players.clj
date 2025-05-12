(ns tic-tac-toe.gui.config-players
  (:require [quil.core :as q]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))



(def type-labels ["Human" "Computer"])

(def difficulty-options ["Easy" "Medium" "Hard"])


(defmethod multis/draw-state :config-x-type [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :config-x-type] [state]
  state)

(defmethod multis/mouse-clicked :config-x-type [state {:keys [x y] :as event}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :human)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc-in [:players 0 :play-type] :computer)
            (assoc :status :config-x-difficulty))
        :else state))

(defmethod multis/draw-state :config-x-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player X Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(defmethod core/update-state [:gui :config-x-difficulty] [state]
  state)

(defmethod multis/mouse-clicked :config-x-difficulty [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :easy)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] util/opt2-of-3-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :medium)
            (assoc :status :config-o-type))
        (util/button-clicked? [x y] util/opt3-of-3-rect)
        (-> state
            (assoc-in [:players 0 :difficulty] :hard)
            (assoc :status :config-o-type))
        :else state))

(defmethod multis/draw-state :config-o-type [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Type" util/center-x util/title-offset-y)
  (util/draw-2-options-buttons type-labels))

(defmethod core/update-state [:gui :config-o-type] [state]
  state)

(defmethod multis/mouse-clicked :config-o-type [state {:keys [x y] :as event}]
  (cond (util/button-clicked? [x y] util/opt1-of-2-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :human)
            (assoc :status :select-board))
        (util/button-clicked? [x y] util/opt2-of-2-rect)
        (-> state
            (assoc-in [:players 1 :play-type] :computer)
            (assoc :status :config-o-difficulty))
        :else state))

(defmethod multis/draw-state :config-o-difficulty [state]
  (q/background 240)
  (q/fill 0)
  (q/text-align :center :center)
  (q/text-size 28)
  (q/text "Choose Player O Computer Difficulty" util/center-x util/title-offset-y)
  (util/draw-3-buttons difficulty-options))

(defmethod core/update-state [:gui :config-o-difficulty] [state]
  state)

(defmethod multis/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (cond (util/button-clicked? [x y] util/opt1-of-3-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :easy)
            (assoc :status :select-board))
        (util/button-clicked? [x y] util/opt2-of-3-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :medium)
            (assoc :status :select-board))
        (util/button-clicked? [x y] util/opt3-of-3-rect)
        (-> state
            (assoc-in [:players 1 :difficulty] :hard)
            (assoc :status :select-board))
        :else state))
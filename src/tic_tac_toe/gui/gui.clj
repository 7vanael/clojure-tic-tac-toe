(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.draw]
            [tic-tac-toe.gui.in-progress]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.winner]))

(defn setup []
  util/initial-state)

(defn mouse-clicked-core [state event]
  (multis/mouse-clicked state event))

(defn draw-state-core [state]
  (multis/draw-state state))

(defn update-state-core [state]
  (multis/update-state state))

(defmethod core/take-human-turn :gui [state]
  (-> state
      (assoc :status :in-progress)
      (assoc :turn-phase :awaiting-input)
      multis/update-state))

(defmethod core/start-game :gui [state]
  (q/defsketch tic-tac-toe
    :title "Tic-Tac-Toe"
    :size [util/screen-width util/screen-height]
    :setup setup
    :update update-state-core
    :draw draw-state-core
    :mouse-clicked mouse-clicked-core
    :middleware [m/fun-mode]))

(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.in-progress]
            [tic-tac-toe.gui.game-over]))

(defn setup []
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]
   :board-size          nil})

(defn mouse-clicked-core [state event]
  (multis/mouse-clicked state event))

(defn draw-state-core [state]
  (multis/draw-state state))

(defn update-state-core [state]
  (multis/update-state state))

(defn create-sketch [state]
  (q/defsketch tic-tac-toe
    :title "Tic-Tac-Toe"
    :size [util/screen-width util/screen-height]
    :setup setup
    :update update-state-core
    :draw draw-state-core
    :mouse-clicked mouse-clicked-core
    :middleware [m/fun-mode]))
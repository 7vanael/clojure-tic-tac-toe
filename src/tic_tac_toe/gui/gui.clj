(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.draw]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.tie]
            [tic-tac-toe.gui.in-progress]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.winner]
            [tic-tac-toe.gui.found-save]))

(defn setup []
  (core/initial-state {:interface :gui}))

(defn update-gui [state]
  (if (or (not (= :in-progress (:status state))) (= :human (core/active-player-type state)))
    state
    (core/play-turn! state)))

(defmethod core/start-game :gui [_]
  (q/sketch
    :title "Tic-Tac-Toe"
    :size [util/screen-width util/screen-height]
    :setup setup
    :update update-gui
    :draw core/draw-state
    :mouse-pressed core/mouse-clicked
    :middleware [m/fun-mode]))
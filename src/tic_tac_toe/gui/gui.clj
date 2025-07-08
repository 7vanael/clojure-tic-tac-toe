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

(defmethod core/start-game :gui [_]
  (q/sketch
    :title "Tic-Tac-Toe"
    :size [util/screen-width util/screen-height]
    :setup core/initial-state
    :update core/update-state
    :draw core/draw-state
    :mouse-pressed core/mouse-clicked
    :middleware [m/fun-mode]))
(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.tie]
            [tic-tac-toe.gui.in-progress]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.winner]
            [tic-tac-toe.gui.found-save]))


(defn debug-overlay [options]
  (let [debugging false
        original-draw (:draw options)]
    (assoc options :draw
                   (fn [state]
                     (original-draw state)
                     (when debugging
                       (q/push-style)
                       (q/text-size 8)
                       (doseq [[ind capt fn] [[0 "mouse-pressed?" q/mouse-pressed?]
                                              [1 "mouse-x" q/mouse-x] [2 "mouse-y" q/mouse-y]]]
                         (q/text (str capt " " (fn)) 20 (+ (* 20 ind) 20)))
                       (q/pop-style))))))

(defn setup [state]
  (util/initial-state (:save state)))

(defmethod core/start-game :gui [state]
  (q/defsketch tic-tac-toe
    :title "Tic-Tac-Toe"
    :size [util/screen-width util/screen-height]
    :setup setup
    :update core/update-state
    :draw multis/draw-state
    :mouse-pressed multis/mouse-clicked
    :middleware [m/fun-mode debug-overlay]))
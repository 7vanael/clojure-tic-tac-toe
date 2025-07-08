(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.tie]
            [tic-tac-toe.gui.in-progress]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.winner]
            [tic-tac-toe.gui.found-save]))


(defn debug-overlay [options]
  (let [debugging     true
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

;(defn setup []
;  )

(defn ->inspect [x]
  (prn "->inspect: " x)
  x)

(defn quil-update [state]
  (->inspect state))

(defmethod core/launch :gui [state]
  (q/defsketch tic-tac-toe
               :title "Tic-Tac-Toe"
               :size [util/screen-width util/screen-height]
               :setup (fn [] state)
               :update quil-update
               :draw (fn [state] (q/background 255))
               ;:draw core/draw-state
               :mouse-pressed core/get-selection
               :middleware [m/fun-mode debug-overlay]))
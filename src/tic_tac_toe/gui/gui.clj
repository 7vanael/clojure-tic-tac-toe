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
  (let [debugging     false
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

(defn ->inspect [x]
  (prn "->inspect: " x)
  x)

(defn quil-update [state]
  (->inspect state)
  state
  (let [new-state (if (q/mouse-pressed?) (assoc state :mouse-click true) (assoc state :mouse-click false))]
    ;and/or will return the last value passed in, so this assigns mouse-clicked result to val if present
    (if-let [val (and (not (q/mouse-pressed?))
                      (:mouse-click state)
                      (core/get-selection new-state {:x (q/mouse-x) :y (q/mouse-y)}))]
      ;If mouse-button released, call it a click and see what it gives you
      (core/update-state new-state (->inspect val))
      (->inspect new-state))))

(defn launch-quil [state]
  (q/defsketch tic-tac-toe
               :title "Tic-Tac-Toe"
               :size [util/screen-width util/screen-height]
               :setup (constantly state)
               :update quil-update
               :draw core/draw-state
               :middleware [m/fun-mode #_debug-overlay]))
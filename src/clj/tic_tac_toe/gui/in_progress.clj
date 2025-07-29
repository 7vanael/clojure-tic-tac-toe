(ns tic-tac-toe.gui.in-progress
  (:require [quil.core :as q :include-macros true]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.cells :as cells]))

;;TODO Date Take-human-turn shouldn't need to call core/do-update! Update state should be about to call it.
;; Why doesn't that work?:

(defn take-human-turn [state]
  (-> state
      core/do-take-human-turn
      core/play-turn!
      ))

(defmethod core/mouse-clicked :in-progress [{:keys [board] :as state} {:keys [x y]}]
  (let [clicked-cell (cells/find-clicked-cell board x y)
        value        (:value clicked-cell)]
    (if (and (number? value) (= :human (core/active-player-type state)))
      (take-human-turn (assoc state :response value))
      state)))

(defmethod core/take-human-turn :gui [state] state)
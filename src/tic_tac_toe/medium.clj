(ns tic-tac-toe.medium
  (:require [tic-tac-toe.easy :as easy]
            [tic-tac-toe.hard :as hard]
            [tic-tac-toe.core :as core]))

(defmethod core/take-computer-turn :medium [state]
  (let [tenth-percent (rand-int 10)]
    (if (zero? tenth-percent)
      (assoc (easy/easy state) :turn-phase :input-received)
      (assoc (hard/hard state) :turn-phase :input-received))))
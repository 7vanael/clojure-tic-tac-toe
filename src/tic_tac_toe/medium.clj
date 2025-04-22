(ns tic-tac-toe.medium
  (:require [tic-tac-toe.easy :as easy]
            [tic-tac-toe.hard :as hard]
            [tic-tac-toe.turn :as turn]))

(defmethod turn/take-turn :medium [state]
  (let [tenth-percent (rand-int 10)]
    (if (zero? tenth-percent) (easy/easy state) (hard/hard state))))
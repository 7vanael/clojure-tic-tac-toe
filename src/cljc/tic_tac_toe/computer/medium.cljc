(ns tic-tac-toe.computer.medium
  (:require [tic-tac-toe.computer.easy :as easy]
            [tic-tac-toe.computer.hard :as hard]
            [tic-tac-toe.core :as core]))

(defmethod core/take-computer-turn :medium [state]
  (let [tenth-percent (rand-int 10)]
    (if (zero? tenth-percent)
      (easy/easy state)
      (hard/hard state))))
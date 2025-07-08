(ns tic-tac-toe.computer.easy
  (:require [tic-tac-toe.computer.computer-util :as util]
            [tic-tac-toe.core :as core]))

(defn easy [{:keys [board]}]
  (let [move     (first (shuffle (util/get-possible-moves board)))]
    (prn "move:" move)
    (prn "(get-in board move):" (get-in board move))
    (get-in board move)))

(defmethod core/take-computer-turn :easy [state]
  (easy state))
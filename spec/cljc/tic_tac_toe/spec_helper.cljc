(ns tic-tac-toe.spec-helper
  (:require [tic-tac-toe.core :as core]))

(defn gui-mock-state []
  (core/initial-state {:interface :gui :save :mock}))

(defn state-create [{:keys [interface board active-player-index status x-type o-type x-difficulty o-difficulty cells save response]
                     :or   {board               nil
                            active-player-index 0
                            status              :welcome
                            x-type              nil
                            o-type              nil
                            x-difficulty        nil
                            o-difficulty        nil
                            save                :mock}}]
  (cond-> {:board               board
           :active-player-index active-player-index
           :status              status
           :players             [{:character "X" :play-type x-type :difficulty x-difficulty}
                                 {:character "O" :play-type o-type :difficulty o-difficulty}]}
          (some? cells) (assoc :cells cells)
          (some? interface) (assoc :interface interface)
          (some? response) (assoc :response response)
          (some? save) (assoc :save save)))

(def empty-board
  [[1 2 3]
   [4 5 6]
   [7 8 9]])

(def empty-4-board
  [[1 2 3 4]
   [5 6 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def empty-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 8 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])

(def center-x-board
  [[1 2 3]
   [4 "X" 6]
   [7 8 9]])

(def first-X-4-board
  [[1 2 3 4]
   [5 "X" 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def first-x-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 "X" 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])
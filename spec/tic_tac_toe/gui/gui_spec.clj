(ns tic-tac-toe.gui.gui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.gui :refer :all]))

(def pre-state
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

(defn state-create [{:keys [board active-player-index status x-type o-type x-difficulty o-difficulty]
                     :or   {board               nil
                            active-player-index 0
                            status              :welcome
                            x-type              nil
                            o-type              nil
                            x-difficulty        nil
                            o-difficulty        nil}}]
  {:interface           :gui
   :board               board
   :active-player-index active-player-index
   :status              status
   :players             [{:character "X" :play-type x-type :difficulty x-difficulty}
                         {:character "O" :play-type o-type :difficulty o-difficulty}]})


(describe "gui with Quil"

  (it "initializes a null set-up"
    (should= pre-state (setup)))
  )
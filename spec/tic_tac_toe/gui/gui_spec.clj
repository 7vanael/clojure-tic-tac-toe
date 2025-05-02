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



(describe "gui with Quil"

  (it "initializes a null set-up"
    (should= pre-state (setup)))
  )
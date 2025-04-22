(ns tic-tac-toe.quil-spec
  (:require [speclj.core :refer :all]
            [quil.core :as q]
            [tic-tac-toe.gui :refer :all]))

(def pre-state
  {:current-screen      (:config screens)
   :board-size          nil
   :board               nil
   :active-player-index 0
   :status              "config"
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

(describe "gui with Quil"

  (it "initializes a null set-up"
    (should= pre-state (setup)))

  )
(ns tic-tac-toe.gui.gui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.gui :refer :all]))

(defn pre-state [save]
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]
   :save                save})



(describe "gui with Quil"

  (it "initializes a null set-up"
    (should= (pre-state :edn) (setup {:save :edn}))
    (should= (pre-state :sql) (setup {:save :sql})))
  )
(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]
            [tic-tac-toe.spec-helper :as helper]))

(describe "welcome mouse-clicked"
  (with-stubs)
  (before (reset! spec-helper/mock-db nil))

  (it "welcome; if no save present, it initializes a state and moves to config-x-type"
      (let [event {:x 100 :y 100}
            state {:save :mock :interface :gui :status :welcome}
            result (core/mouse-clicked state event)]
        (should= result (core/fresh-start state))
        (should= :config-x-type (:status result))))

  (it "welcome; if a save is present, it updates the status to found-save when clicked anywhere"
    (let [event {:x 100 :y 100}
          state {:save :mock :interface :gui :status :welcome}
          prior-game (core/save-game (helper/state-create {:save :mock :interface :gui :x-type :human
                                          :o-type :human :active-player-index 1
                                          :board [["X" 2 3] [4 5 6] [7 8 9]]}))
          result (core/mouse-clicked state event)]
      (should= result (assoc prior-game :status :found-save)))
    )
  )
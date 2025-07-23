(ns tic-tac-toe.gui.tie_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.tie :refer :all]
            [tic-tac-toe.spec-helper :as helper]))

(def state (helper/state-create {:status :tie :interface :gui :type-x :human :type-o :human
                                 :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                 :save   :mock}))

(describe "tie- end of game"
  (with-stubs)

  (it "calls update-state with true if yes button is clicked"
    (with-redefs [core/update-state (stub :update-state)]
      (let [event {:x 144 :y 350}
            result (core/mouse-clicked state event)]
        (should= result (core/fresh-start state)))))

  #_(it "sets the status to exit to end game if 'no' button is pressed"
      ;This is currently ending the program.. can't figure out redefs- see winner
      (with-redefs [core/update-state (stub :update-state)]
        (let [event    {:x 432 :y 350}
              expected (assoc state :status :exit)]
          (core/mouse-clicked state event)
          (should-have-invoked :update-state {:with [state event]}))))

  (it "returns the state unchanged if no button is clicked"
    (let [event {:x 2 :y 2}]
      (should= state (core/mouse-clicked state event))))
  )

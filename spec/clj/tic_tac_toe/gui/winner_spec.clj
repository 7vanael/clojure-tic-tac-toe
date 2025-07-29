(ns tic-tac-toe.gui.winner_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.winner]
            [tic-tac-toe.spec-helper :as helper]))

(def state (helper/state-create {:status :winner :interface :gui :type-x :human :type-o :human
                                 :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                 :save   :mock}))

(describe "winner- end of game"
  (with-stubs)

  (it "calls update-state with true if yes button is clicked"
    (let [event {:x 144 :y 350}
          result (core/mouse-clicked state event)]
      (should= result (core/fresh-start state))))

  #_(it "sets the status to exit to end game if 'no' button is pressed"
      (with-redefs [System/exit (stub :exit)]
        (let [event {:x 432 :y 350}]
          (core/mouse-clicked state event)
          (should-have-invoked :exit))))

  (it "returns the state unchanged if no button is clicked"
    (let [event {:x 2 :y 2}]
      (should= state (core/mouse-clicked state event))))
  )

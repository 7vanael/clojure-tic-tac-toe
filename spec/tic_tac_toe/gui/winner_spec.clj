(ns tic-tac-toe.gui.winner_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.winner]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(def state (helper/state-create {:status :winner :interface :gui :type-x :human :type-o :human
                                 :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                 :save   :mock}))

(describe "winner- end of game"
  (with-stubs)
  (before (reset! spec-helper/mock-db {}))

  (it "returns to config x type if yes button is clicked"
    (let [event    {:x 144 :y 350}
          expected (assoc (core/initial-state {:save :mock :interface :gui}) :status :config-x-type)]
      (should= expected (core/mouse-clicked state event))))

  #_(it "sets the status to exit to end game if 'no' button is pressed"
    (with-redefs [System/exit (stub :exit)]
      (let [event    {:x 432 :y 350}]
        (core/mouse-clicked state event)
      (should-have-invoked :exit) )))

  (it "returns the state unchanged if no button is clicked"
    (let [event {:x 2 :y 2}]
      (should= state (core/mouse-clicked state event))))

  (it "deletes the save file when the game ends in a win"
    (let [save-state (helper/state-create {:interface :gui :status :winner :x-type :human
                                           :o-type    :human :board [["X" "O" "X"]
                                                                     ["O" "X" "O"]
                                                                     ["O" "X" "X"]]
                                           :save      :mock})
          event      {:x 2 :y 2}]
      (core/save-game save-state)
      (core/mouse-clicked save-state event)
      (should= {:save :mock} (core/load-game {:save :mock}))))
  )

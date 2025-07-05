(ns tic-tac-toe.gui.winner_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.winner :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "winner- end of game"
  (with-stubs)
  (redefs-around [spit (stub :spit)
                  core/update-state (stub :update-state)])
  (before (reset! spec-helper/mock-db {}))

    (it "Invokes update state with option 1 if button 1 is clicked"
    (let [event {:x 144 :y 350}
            state {:status :winner}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]})))

  (it "Invokes update state with option 1 if button 2 is clicked"
    (let [event {:x 432 :y 350}
          state {:status :winner}]
      (core/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 2]})))

  (it "Returns nil if no valid button is clicked"
    (let [event {:x 1 :y 1}]
      (core/mouse-clicked {:status :winner} event)
      (should-not-have-invoked :update-state)))

  #_(it "deletes the save file when the game ends in a win"
    (let [state (test-core/state-create {:interface :gui :status :winner :x-type :human
                                         :o-type    :human :board [["X" "O" "X"]
                                                                   ["O" "X" "O"]
                                                                   ["O" "X" "X"]]
                                         :save :mock})]
      (core/save-game state)
      (core/update-state state)
      (should= nil (core/load-game {:save :mock}))))

  #_(it "if play-again button is clicked; sets the status to config-x-type and clears the board and player info"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1 :interface :gui :save :mock}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui :save :mock})
               new-state)))

  #_(it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}]
        (multis/mouse-clicked (test-core/state-create {:status :tie :save :mock}) event)
        (should-have-invoked :exit))))
  )

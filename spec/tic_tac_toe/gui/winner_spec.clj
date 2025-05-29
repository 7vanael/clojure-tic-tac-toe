(ns tic-tac-toe.gui.winner_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.winner :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "winner- end of game"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db {}))


  (it "deletes the save file when the game ends in a win"
    (let [state (test-core/state-create {:interface :gui :status :winner :x-type :human
                                         :o-type    :human :board [["X" "O" "X"]
                                                                   ["O" "X" "O"]
                                                                   ["O" "X" "X"]]
                                         :save :mock})]
      (core/save-game state)
      (core/update-state state)
      (should= nil (core/load-game {:save :mock}))))


  (it "if play-again button is clicked; sets the status to config-x-type and clears the board and player info"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1 :interface :gui :save :mock}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui :save :mock})
               new-state)))



  (it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}]
        (multis/mouse-clicked (test-core/state-create {:status :tie :save :mock}) event)
        (should-have-invoked :exit))))
  )

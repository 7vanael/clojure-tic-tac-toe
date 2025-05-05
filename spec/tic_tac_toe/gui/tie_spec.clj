(ns tic-tac-toe.gui.tie_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.tie :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.persistence :as persistence]
            [tic-tac-toe.persistence-spec :as test-persistence])
  (:import (java.io FileNotFoundException)))

(describe "tie- end of game"
  (with-stubs)

  (it "deletes the save file when the game ends in a draw"
    (with-redefs [println (stub :print-dup)
                  tic-tac-toe.persistence/savefile test-persistence/test-file]
      (let [state (test-core/state-create {:interface :gui :status :tie :board [["X" "O" "X"]
                                                                                ["O" "X" "O"]
                                                                                ["O" "X" "O"]]})]
        (persistence/save-game state)
        (should-not-throw (slurp test-persistence/test-file))
        (core/update-state state)
        (should-throw FileNotFoundException (slurp test-persistence/test-file)))))


  (it "sets the state to nil and the status to config-x-type if play-again button is clicked"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui})
               new-state)))

  (it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}]
        (multis/mouse-clicked (test-core/state-create {:status :tie}) event)
        (should-have-invoked :exit))))
  )

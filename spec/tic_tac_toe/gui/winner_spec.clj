(ns tic-tac-toe.gui.winner_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.winner :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.persistence.file :as persistence]
            [tic-tac-toe.persistence.file-spec :as test-persistence])
  (:import (java.io FileNotFoundException)))

(describe "winner- end of game"
  (with-stubs)
  (redefs-around [spit (stub :spit)])


  (it "deletes the save file when the game ends in a win"
    (let [state (test-core/state-create {:interface :gui :status :winner :x-type :human
                                         :o-type    :human :board [["X" "O" "X"]
                                                                   ["O" "X" "O"]
                                                                   ["O" "X" "X"]]})]
      (core/save-game state)
      (core/update-state state)
      (should-throw FileNotFoundException (slurp persistence/savefile))))


  (it "sets the state to nil and the status to config-x-type if play-again button is clicked"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1 :save :edn}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui :save :edn})
               new-state)))

  (it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}]
        (multis/mouse-clicked (test-core/state-create {:status :tie}) event)
        (should-have-invoked :exit))))
  )

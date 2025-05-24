(ns tic-tac-toe.gui.tie_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.tie :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]))

(describe "tie- end of game"
  (with-stubs)
  (redefs-around [spit (stub :spit)])

  (it "deletes the save file when the game ends in a draw"
    (with-redefs [println (stub :print-dup)
                  core/delete-save (stub :delete)]
      (let [state (test-core/state-create {:interface :gui :status :tie :board [["X" "O" "X"]
                                                                                ["O" "X" "O"]
                                                                                ["O" "X" "O"]]})]
        (core/update-state state)
        (should-have-invoked :delete))))


  (it "sets the state to nil and the status to config-x-type if play-again button is clicked"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1 :save :sql}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui})
               new-state)))

  (it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}
            state (test-core/state-create {:status :tie})]
        (multis/mouse-clicked state event)
        (should-have-invoked :exit)
      (core/delete-save state))))
  )

(ns tic-tac-toe.gui.tie_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.tie :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "tie- end of game"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db nil))

  (it "Returns 1 if button 1 is clicked"
    (let [event {:x 144 :y 350}]
      (should= 1 (multis/mouse-clicked {:status :tie} event))))

  (it "Returns 2 if button 2 is clicked"
    (let [event {:x 432 :y 350}]
      (should= 2 (multis/mouse-clicked {:status :tie} event))))

  (it "Returns nil if no valid button is clicked"
    (let [event {:x 1 :y 1}]
      (should-be-nil (multis/mouse-clicked {:status :tie} event))))

  #_(it "deletes the save file when the game ends in a draw"
    (with-redefs [println (stub :print-dup)]
      (let [state (test-core/state-create {:interface :gui :status :tie :board [["X" "O" "X"]
                                                                                ["O" "X" "O"]
                                                                                ["O" "X" "O"]]
                                           :save :mock})]
        (core/save-game state)
        (should-not= nil @spec-helper/mock-db)
        (core/update-state state)
        (should= nil @spec-helper/mock-db))))


  #_(it "sets the state to nil and the status to config-x-type if play-again button is clicked"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :tie :board [[1 2 3]] :active-player-index 1 :interface :gui :save :mock}) event)]
      (should= (test-core/state-create {:status :config-x-type :interface :gui :save :mock})
               new-state)))

  #_(it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}
            state (test-core/state-create {:status :tie})]
        (multis/mouse-clicked state event)
        (should-have-invoked :exit)
      (core/delete-save state))))
  )

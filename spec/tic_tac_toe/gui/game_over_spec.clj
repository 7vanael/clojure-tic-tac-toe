(ns tic-tac-toe.gui.game_over_spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.gui.game-over :refer :all]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]))

(describe "game-over"
  (with-stubs)

  (it "sets the state to nil and the status to config-x-type if play-again button is clicked"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-gui/state-create {:status :game-over :board-size 3 :board [[1 2 3]] :active-player-index 1}) event)]
      (should= (test-gui/state-create {:status :config-x-type :interface :gui})
               new-state)))

  (it "exits the game if exit button is clicked"
    (with-redefs [q/exit (stub :exit)]
      (let [event {:x 432 :y 350}]
        (multis/mouse-clicked (test-gui/state-create {:status :game-over}) event)
        (should-have-invoked :exit))))
  )

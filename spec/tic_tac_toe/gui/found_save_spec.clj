(ns tic-tac-toe.gui.found-save-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.found-save :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.gui.gui-spec :as test-gui]))

(describe "found-save"

  (it "resumes play of a saved game if Yes button is pressed"
    (let [event       {:x 144 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0})
          new-state   (assoc saved-state :status :in-progress)]
      (should= new-state (multis/mouse-clicked saved-state event))))

  (it "sets the status to config-x-type to start a new game if no button is pressed"
    (let [event       {:x 432 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0})
          new-state   (assoc (test-gui/pre-state :sql) :status :config-x-type)]
      (should= new-state (multis/mouse-clicked saved-state event))))
  )

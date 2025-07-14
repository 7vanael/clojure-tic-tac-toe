(ns tic-tac-toe.gui.found-save-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.spec-helper :as helper]))

(describe "found-save"

  (it "resumes play of a saved game if Yes button is pressed"
    (let [event       {:x 144 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                               :save   :mock})
          new-state   (assoc saved-state :status :in-progress)]
      (should= new-state (core/mouse-clicked saved-state event))))

  (it "sets the status to config-x-type to start a new game if 'no' button is pressed"
    (let [event       {:x 432 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                               :save   :mock})
          new-state   (assoc (helper/gui-mock-state) :status :config-x-type)]
      (should= new-state (core/mouse-clicked saved-state event))))

  (it "returns the state unchanged if no button is clicked"
    (let [event     {:x 2 :y 2}
          current-state (assoc (helper/gui-mock-state) :status :found-save)]
      (should= current-state (core/mouse-clicked current-state event))))
  )

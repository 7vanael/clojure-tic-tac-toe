(ns tic-tac-toe.gui.found-save-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.found-save :as sut]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.spec-helper :as helper]))

(describe "found-save"
  (with-stubs)
  (redefs-around [core/update-state (stub :update-state)])

  (it "Invokes update state with option 1 if button 1 is clicked"
    (let [event {:x 144 :y 350}
          state {:status :found-save}]
      (multis/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 1]})))

  (it "Invokes update state with option 1 if button 2 is clicked"
    (let [event {:x 432 :y 350}
          state {:status :found-save}]
      (multis/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 2]})))

  (it "Returns nil if no valid button pressed"
    (let [event {:x 1 :y 1}]
      (should-be-nil (multis/mouse-clicked {:status :found-save} event))))

  #_(it "resumes play of a saved game if Yes button is pressed"
      (let [event       {:x 144 :y 350}
            saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                                 :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                                 :save   :mock})
            new-state   (assoc saved-state :status :in-progress)]
        (should= new-state (multis/mouse-clicked saved-state event))))

  #_(it "sets the status to config-x-type to start a new game if 'no' button is pressed"
      (let [event       {:x 432 :y 350}
            saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                                 :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                                 :save   :mock})
            new-state   (assoc (helper/pre-state :mock) :status :config-x-type)]
        (should= new-state (multis/mouse-clicked saved-state event))))
  )

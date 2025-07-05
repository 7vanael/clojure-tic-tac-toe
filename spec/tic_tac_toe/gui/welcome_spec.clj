(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))


(describe "welcome mouse-clicked"
  (with-stubs)
  (redefs-around [spit (stub :spit)
                  core/update-state (stub :update-state)])
  (before (reset! spec-helper/mock-db nil))

  #_(Only testing the mouse-clicked method! Should test the update-state method in core)

  (it "calls update-state for any clicked location"
    (with-redefs [core/update-state (stub :update-state)]
      (let [event     {:x 100 :y 100}
            state (helper/pre-state :mock)]
        (core/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 1]}))))
  #_(Remainder of tests can be moved to core)

  #_(it "moves to initial config page from welcome page after a mouse-click if no save found"
    (let [event     {:x 100 :y 100}
          new-state (multis/mouse-clicked (helper/pre-state :mock) event)]
      (should= :config-x-type (:status new-state))))

  #_(it "moves to load save page from welcome page after a mouse-click if save found when loading from a file"
    (let [saved-game (core/save-game (test-core/state-create
                                       {:status              :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                        :active-player-index 1 :type-x :human :type-o :human :save :mock}))
          event      {:x 100 :y 100}
          new-state  (multis/mouse-clicked (helper/pre-state :mock) event)]
      (should= (assoc saved-game :interface :gui :status :found-save) new-state)))

  #_(it "moves to load save page from welcome page after a mouse-click if save found when loading from a database"
    (let [event      {:x 100 :y 100}
          saved-game (core/save-game (test-core/state-create
                                       {:status              :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                        :active-player-index 1 :type-x :human :type-o :human :save :mock}))
          new-state  (multis/mouse-clicked (helper/pre-state :mock) event)]
      (should= new-state (assoc saved-game :status :found-save :interface :gui))
      (should= :found-save (:status new-state))))
  )
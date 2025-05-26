(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))


(describe "welcome"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db nil))

  (it "moves to initial config page from welcome page after a mouse-click if no save found"
    (let [event     {:x 100 :y 100}
          new-state (multis/mouse-clicked (test-gui/pre-state :mock) event)]
      (should= :config-x-type (:status new-state))))

  (it "moves to load save page from welcome page after a mouse-click if save found when loading from a file"
    (let [saved-game (core/save-game (test-core/state-create
                                       {:status              :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                        :active-player-index 1 :type-x :human :type-o :human :save :mock}))
          event      {:x 100 :y 100}
          new-state  (multis/mouse-clicked (test-gui/pre-state :mock) event)]
      (should= (assoc saved-game :interface :gui :status :found-save) new-state)))

  (it "moves to load save page from welcome page after a mouse-click if save found when loading from a database"
    (let [event      {:x 100 :y 100}
          saved-game (core/save-game (test-core/state-create
                                       {:status              :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                        :active-player-index 1 :type-x :human :type-o :human :save :mock}))
          new-state  (multis/mouse-clicked (test-gui/pre-state :mock) event)]
      (should= new-state (assoc saved-game :status :found-save :interface :gui))
      (should= :found-save (:status new-state))))
  )
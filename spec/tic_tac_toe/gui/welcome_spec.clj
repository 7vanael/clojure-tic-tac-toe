(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))


(def state (core/initial-state {:save :mock}))

(describe "welcome mouse-clicked"
  (before (reset! spec-helper/mock-db nil))

  (context "on click anywhere"
    (it "no loaded game found"
      (let [event     {:x 100 :y 100}
            new-state (core/mouse-clicked state event)]
        (should= (assoc state :status :config-x-type) new-state)))

    (it "loaded game"
      (let [saved-game (core/save-game (helper/state-create
                                         {:status              :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                          :active-player-index 1 :type-x :human :type-o :human :save :mock}))
            event      {:x 100 :y 100}
            new-state  (core/mouse-clicked state event)]
        (should= (assoc saved-game :status :found-save) (assoc new-state :status :found-save))))
    )
  )
(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]
            [tic-tac-toe.persistence :as persistence]))


(describe "welcome"
  (with-stubs)
  (redefs-around [spit (stub :spit)])

  (it "moves to initial config page from welcome page after a mouse-click if no save found"
    (let [event     {:x 100 :y 100}
          new-state (multis/mouse-clicked test-gui/pre-state event)]
      (should= :config-x-type (:status new-state))))

  (it "moves to load save page from welcome page after a mouse-click if save found"
    (with-redefs [persistence/load-game (stub :load {:return (test-core/state-create
                                                               {:status :in-progress :board [["X" "O" "X"] [4 "X" 6] [7 8 "O"]]
                                                                :active-player-index 1 :type-x :human :type-o :human})})]
      (let [event       {:x 100 :y 100}]
        (let [new-state (multis/mouse-clicked test-gui/pre-state event)]
          (should= :found-save (:status new-state)))))))
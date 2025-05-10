(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]
            [tic-tac-toe.persistence :as persistence]
            [tic-tac-toe.persistence-spec :as test-persistence]))


(describe "welcome"
  (with-stubs)

  (it "moves to initial config page from welcome page after a mouse-click if no save found"
    (with-redefs [tic-tac-toe.persistence/savefile test-persistence/test-file]
      (persistence/delete-save);arranging, no starting save

      (let [event     {:x 100 :y 100}
            new-state (multis/mouse-clicked test-gui/pre-state event)]
        (should= :config-x-type (:status new-state)))))

  (it "moves to load save page from welcome page after a mouse-click if save found"
    (with-redefs [tic-tac-toe.persistence/savefile test-persistence/test-file]
      (let [event       {:x 100 :y 100}

            saved-state (test-core/state-create {:interface           :gui :status :in-progress :board [["X" "O" "X"]
                                                                                                        [4 "X" 6]
                                                                                                        [7 8 "O"]]
                                                 :active-player-index 1 :type-x :human :type-o :human})]
        (persistence/save-game saved-state)

        (let [new-state (multis/mouse-clicked test-gui/pre-state event)]
          (should= :found-save (:status new-state))))
      (persistence/delete-save)));cleanup
  )
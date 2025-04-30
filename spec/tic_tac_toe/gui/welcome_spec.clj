(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]))


(describe "welcome"

  (it "moves to initial config page from welcome page after a mouse-click"
    (let [event     {:x 100 :y 100}
          new-state (multis/mouse-clicked test-gui/pre-state event)]
      (should= :config-x-type (:status new-state)))))
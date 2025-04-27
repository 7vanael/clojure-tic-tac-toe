(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]))

(describe "select board"

  (it "sets the board-size to 3 and changes state to initialize-board if 3x3 button is pressed"
    (let [event     {:x 200 :y 350}
          new-state (multis/mouse-clicked (test-gui/state-create {:status :select-board}) event)]
      (should= (test-gui/state-create {:status :initialize-board :board-size 3})
               new-state)))

  (it "sets the board-size to 4 and changes state to initialize-board if 4x4 button is pressed"
    (let [event     {:x 600 :y 350}
          new-state (multis/mouse-clicked (test-gui/state-create {:status :select-board}) event)]
      (should= (test-gui/state-create {:status :initialize-board :board-size 4})
               new-state)))
  )

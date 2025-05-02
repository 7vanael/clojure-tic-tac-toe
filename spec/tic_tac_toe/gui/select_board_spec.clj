(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.board_spec :as test-board]))

(describe "select board"

  (it "sets the board-size to 3 and changes state to initialize-board if 3x3 button is pressed"
    (let [event     {:x 144 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :select-board}) event)]
      (should= (test-core/state-create {:status :in-progress :board test-board/empty-board})
               new-state)))

  (it "sets the board-size to 4 and changes state to initialize-board if 4x4 button is pressed"
    (let [event     {:x 432 :y 350}
          new-state (multis/mouse-clicked (test-core/state-create {:status :select-board}) event)]
      (should= (test-core/state-create {:status :in-progress :board test-board/empty-4-board})
               new-state)))
  )

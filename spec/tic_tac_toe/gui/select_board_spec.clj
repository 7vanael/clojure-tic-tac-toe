(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.board_spec :as test-board]))

(describe "select board"
  (it "Returns 1 if button 1 is clicked"
    (let [event {:x 288 :y 288}]
      (should= 1 (multis/mouse-clicked {:status :select-board} event))))

  (it "Returns 2 if button 2 is clicked"
    (let [event {:x 288 :y 432}]
      (should= 2 (multis/mouse-clicked {:status :select-board} event))))

  (it "Returns 3 if button 3 is clicked"
    (let [event {:x 288 :y 576}]
      (should= 3 (multis/mouse-clicked {:status :select-board} event))))

    (it "Returns nil if no valid button is clicked"
      (let [event {:x 1 :y 1}]
        (should-be-nil (multis/mouse-clicked {:status :select-board} event))))

    #_(it "sets the board-size to 3 and changes state to board-ready if 3x3 button is pressed"
        (let [event     {:x 288 :y 288}
              new-state (multis/mouse-clicked (test-core/state-create {:status :select-board}) event)]
          (should= (test-core/state-create {:status :board-ready :board test-board/empty-board})
                   new-state)))

    #_(it "sets the board-size to 4 and changes state to bord-ready if 4x4 button is pressed"
        (let [event     {:x 288 :y 432}
              new-state (multis/mouse-clicked (test-core/state-create {:status :select-board}) event)]
          (should= (test-core/state-create {:status :board-ready :board test-board/empty-4-board})
                   new-state)))

    #_(it "sets the board-size to 3x3x3 and changes state to board-ready if 4x4 button is pressed"
        (let [event     {:x 288 :y 576}
              new-state (multis/mouse-clicked (test-core/state-create {:status :select-board}) event)]
          (should= (test-core/state-create {:status :board-ready :board test-board/empty-3d-board})
                   new-state)))
  )
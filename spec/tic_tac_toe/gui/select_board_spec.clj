(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.board_spec :as test-board]))

(describe "select board"
  (with-stubs)
  (redefs-around [core/update-state (stub :update-state)])

  (it "Invokes update state with option 1 if button 1 is clicked"
    (let [event {:x 288 :y 288}
          state {:status :select-board}]
      (multis/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 1]})))

  (it "Invokes update state with option 2 if button 2 is clicked"
    (let [event {:x 288 :y 432}
          state {:status :select-board}]
      (multis/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 2]})))

  (it "Invokes update state with option 3 if button 3 is clicked"
    (let [event {:x 288 :y 576}
          state {:status :select-board}]
      (multis/mouse-clicked state event)
      (should-have-invoked :update-state {:with [state 3]})))

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
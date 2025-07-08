(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.spec-helper :as helper]))

(describe "select board"
  (with-stubs)
  (redefs-around [core/update-state (stub :update-state)])

  (it "moves to in-progress with board of 3x3 if 3x3 button pressed"
    (let [event     {:x 288 :y 288}
          starting-state (assoc (helper/gui-mock-state :any) :status :select-board)
          new-state (core/mouse-clicked starting-state event)
          expected (assoc starting-state :status :in-progress :board helper/empty-board)]
      (should= expected new-state)))

  (it "moves to in-progress with board of 4x4 if 4x4 button pressed"
    (let [event     {:x 288 :y 432}
          starting-state (assoc (helper/gui-mock-state :any) :status :select-board)
          new-state (core/mouse-clicked starting-state event)
          expected  (assoc starting-state :status :in-progress :board helper/empty-4-board)]
      (should= expected new-state)))

  (it "moves to in-progress with board of 3x3x3 if 3x3x3 button pressed"
    (let [event     {:x 288 :y 576}
          starting-state (assoc (helper/gui-mock-state :any) :status :select-board)
          new-state (core/mouse-clicked starting-state event)
          expected (assoc starting-state :status :in-progress :board helper/empty-3d-board)]
      (should= expected new-state)))

  (it "returns the state unchanged if no button is clicked"
    (let [event     {:x 2 :y 2}
          starting-state (assoc (helper/gui-mock-state :any) :status :select-board)
          new-state (core/mouse-clicked starting-state event)]
      (should= starting-state new-state)))
  )
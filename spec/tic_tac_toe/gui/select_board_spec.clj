(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.select-board :refer :all]
            [tic-tac-toe.spec-helper :as helper]))

(describe "select board"
  (with-stubs)

  (it "calls update-state with 3 if 3x3 button pressed"
    (with-redefs [core/update-state (stub :update-state)]
    (let [event     {:x 288 :y 288}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)]
      (core/mouse-clicked starting-state event)
      (should-have-invoked :update-state {:with [starting-state 3]}))))

  (it "calls update-state with 4 if 4x4 if 4x4 button pressed"
    (with-redefs [core/update-state (stub :update-state)]
    (let [event     {:x 288 :y 432}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)]
      (core/mouse-clicked starting-state event)
      (should-have-invoked :update-state {:with [starting-state 4]}))))

  (it "calls update-state with 3 3 3 if 3x3x3 button pressed"
    (with-redefs [core/update-state (stub :update-state)]
    (let [event     {:x 288 :y 576}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)]
      (core/mouse-clicked starting-state event)
      (should-have-invoked :update-state {:with [starting-state [3 3 3]]}))))

  (it "returns the state unchanged if no button is clicked"
    (let [event     {:x 2 :y 2}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)
          new-state (core/mouse-clicked starting-state event)]
      (should= starting-state new-state)))
  )
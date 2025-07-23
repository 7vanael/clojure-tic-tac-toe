(ns tic-tac-toe.gui.select-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.spec-helper :as helper]))

(describe "select board"
  (with-stubs)

  (it "assigns a 3x3 board to the state if 3x3 button is pressed"
    (let [event          {:x 288 :y 288}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)
          result         (core/mouse-clicked starting-state event)
          expected       (assoc starting-state :board helper/empty-board :status :in-progress)]
      (should= expected result)))

  (it "assigns a 4x4 board to the state if 4x4 button pressed"
      (let [event          {:x 288 :y 432}
            starting-state (assoc (helper/gui-mock-state) :status :select-board)
            result         (core/mouse-clicked starting-state event)
            expected       (assoc starting-state :board helper/empty-4-board :status :in-progress)]
        (should= expected result)))

  (it "assigns a 3x3x3 board if 3x3x3 button pressed"
      (let [event          {:x 288 :y 576}
            starting-state (assoc (helper/gui-mock-state) :status :select-board)
            result         (core/mouse-clicked starting-state event)
            expected       (assoc starting-state :board helper/empty-3d-board :status :in-progress)]
        (should= expected result)))

  (it "returns the state unchanged if no button is clicked"
    (let [event          {:x 2 :y 2}
          starting-state (assoc (helper/gui-mock-state) :status :select-board)
          new-state      (core/mouse-clicked starting-state event)]
      (should= starting-state new-state)))
  )
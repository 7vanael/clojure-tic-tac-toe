(ns tic-tac-toe.gui.config-players-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.spec-helper :as helper]))

(describe "configure players"

  (context "config x-type"
    (it "moves to config o type and sets x-type to Human if human button clicked"
      (let [event     {:x 144 :y 350}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-o-type)
                         (assoc-in [:players 0 :play-type] :human))]
        (should= expected new-state)))

    (it "moves to config x difficulty and sets x-type to computer if computer button clicked on config-x-type"
      (let [event     {:x 432 :y 350}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-x-difficulty)
                         (assoc-in [:players 0 :play-type] :computer))]
        (should= expected new-state)))

    (it "returns state unchanged if no button clicked on config-x-type"
      (let [event     {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            new-state (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-type"
    (it "moves to select board and sets o-type to Human if human button clicked"
      (let [event     {:x 144 :y 350}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :select-board)
                         (assoc-in [:players 1 :play-type] :human))]
        (should= expected new-state)))

    (it "moves to config o difficulty and sets o-type to computer if computer button clicked on config-o-type"
      (let [event     {:x 432 :y 350}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-o-difficulty)
                         (assoc-in [:players 1 :play-type] :computer))]
        (should= expected new-state)))

    (it "returns state unchanged if no button clicked on config-o-type"
      (let [event     {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
            new-state (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config x-difficulty"

    (it "moves to config o type if x-difficulty is set to easy"
      (let [event     {:x 288 :y 288}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-o-type)
                         (assoc-in [:players 0 :difficulty] :easy))]
        (should= expected new-state)))

    (it "moves to config o type if x-difficulty is set to medium"
      (let [event     {:x 288 :y 432}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-o-type)
                         (assoc-in [:players 0 :difficulty] :medium))]
        (should= expected new-state)))

    (it "moves to config o type if x-difficulty is set to hard"
      (let [event     {:x 288 :y 576}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :config-o-type)
                         (assoc-in [:players 0 :difficulty] :hard))]
        (should= expected new-state)))

    (it "returns the state unchanged if no button is clicked"
      (let [event     {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-difficulty"
    (it "moves to select board if o-difficulty is set to easy"
      (let [event     {:x 288 :y 288}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :select-board)
                         (assoc-in [:players 1 :difficulty] :easy))]
        (should= expected new-state)))

    (it "moves to config o type if x-difficulty is set to medium"
      (let [event     {:x 288 :y 432}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :select-board)
                         (assoc-in [:players 1 :difficulty] :medium))]
        (should= expected new-state)))

    (it "moves to config o type if x-difficulty is set to hard"
      (let [event     {:x 288 :y 576}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state (core/mouse-clicked starting-state event)
            expected (-> starting-state
                         (assoc :status :select-board)
                         (assoc-in [:players 1 :difficulty] :hard))]
        (should= expected new-state)))

    (it "returns the state unchanged if no button is clicked"
      (let [event     {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  )
(ns tic-tac-toe.gui.config-players-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.spec-helper :as helper]))

(describe "configure players"
  (with-stubs)

  (context "config x-type"
    (it "calls update-state with human Human if human button clicked"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 144 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-type)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :human]}))))

    (it "calls update-state with computer if computer button clicked on config-x-type"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 432 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-type)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :computer]}))))

    (it "returns state unchanged if no button clicked on config-x-type"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-type"
    (it "calls update-state with Human if human button clicked"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 144 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-type)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :human]}))))

    (it "calls update-state with computer if computer button clicked on config-o-type"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 432 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-type)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :computer]}))))

    (it "returns state unchanged if no button clicked on config-o-type"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config x-difficulty"
    (it "calls update-state with easy if easy button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 288}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :easy]}))))

    (it "calls update-state with medium if medium button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 432}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :medium]}))))

    (it "calls update-state with hard if hard button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 576}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :hard]}))))

    (it "returns the state unchanged if no button is clicked"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-difficulty"
    (it "calls update-state with easy if easy button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 288}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :easy]}))))

    (it "calls update-state with medium if medium button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 432}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :medium]}))))

    (it "calls update-state with hard if hard button is pressed"
      (with-redefs [core/update-state (stub :update-state)]
        (let [event          {:x 288 :y 576}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)]
          (core/mouse-clicked starting-state event)
          (should-have-invoked :update-state {:with [starting-state :hard]}))))

    (it "returns the state unchanged if no button is clicked"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )
  )
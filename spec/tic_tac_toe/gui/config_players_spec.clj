(ns tic-tac-toe.gui.config-players-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.spec-helper :as helper]))

(describe "configure players"
  (with-stubs)

  (context "config x-type"
    (it "updates x-type to Human if human button clicked and progresses state to config-o-type"
      (let [event          {:x 144 :y 350}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            result         (core/mouse-clicked starting-state event)]
        (should= :config-o-type (:status result))
        (should= :human (get-in result [:players 0 :play-type]))))

    (it "updates x-type to computer if computer button clicked and progresses state to config-o-type"
        (let [event          {:x 432 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
              result         (core/mouse-clicked starting-state event)]
          (should= :config-x-difficulty (:status result))
          (should= :computer (get-in result [:players 0 :play-type]))))

    (it "returns state unchanged if no button clicked on config-x-type"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-type)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-type"
    (it "updates o-type to Human if human button clicked and progresses state to select-board"
        (let [event          {:x 144 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
              result         (core/mouse-clicked starting-state event)]
          (should= :select-board (:status result))
          (should= :human (get-in result [:players 1 :play-type]))))

    (it "updates o-type to computer if computer button clicked and progresses state to config-o-difficulty"
        (let [event          {:x 432 :y 350}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
              result         (core/mouse-clicked starting-state event)]
          (should= :config-o-difficulty (:status result))
          (should= :computer (get-in result [:players 1 :play-type]))))

    (it "returns state unchanged if no button clicked on config-o-type"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-type)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config x-difficulty"
    (it "updates x-difficulty with easy if easy button is pressed and progresses state to config-o-type"
        (let [event          {:x 288 :y 288}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :config-o-type (:status result))
          (should= :easy (get-in result [:players 0 :difficulty]))))

    (it "updates x-difficulty with medium if medium button is pressed and progresses state to config-o-type"
        (let [event          {:x 288 :y 432}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :config-o-type (:status result))
          (should= :medium (get-in result [:players 0 :difficulty]))))

    (it "updates x-difficulty with hard if hard button is pressed and progresses state to config-o-type"
        (let [event          {:x 288 :y 576}
              starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :config-o-type (:status result))
          (should= :hard (get-in result [:players 0 :difficulty]))))

    (it "returns the state unchanged if no button is clicked"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-x-difficulty)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )

  (context "config o-difficulty"
    (it "updates o-difficulty with with easy if easy button is pressed and progresses state to select-board"
        (let [event          {:x 288 :y 288}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :select-board (:status result))
          (should= :easy (get-in result [:players 1 :difficulty]))))

    (it "updates o-difficulty with medium if medium button is pressed and progresses state to select-board"
        (let [event          {:x 288 :y 432}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :select-board (:status result))
          (should= :medium (get-in result [:players 1 :difficulty]))))

    (it "updates o-difficulty with hard if hard button is pressed and progresses state to select-board"
        (let [event          {:x 288 :y 576}
              starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
              result         (core/mouse-clicked starting-state event)]
          (should= :select-board (:status result))
          (should= :hard (get-in result [:players 1 :difficulty]))))

    (it "returns the state unchanged if no button is clicked"
      (let [event          {:x 2 :y 2}
            starting-state (assoc (helper/gui-mock-state) :status :config-o-difficulty)
            new-state      (core/mouse-clicked starting-state event)]
        (should= starting-state new-state)))
    )
  )
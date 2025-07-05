(ns tic-tac-toe.gui.config-players-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.gui.config-players :refer :all]
            [tic-tac-toe.core-spec :as test-core]))

(describe "configure players"
  (with-stubs)
  (redefs-around [core/update-state (stub :update-state)])

  (context "config x-type"
    (it "Invokes update state with option 1 if button 1 is clicked"
      (let [event {:x 144 :y 350}
            state {:status :config-x-type}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]})))

    (it "Invokes update state with option 2 if button 2 is clicked"
      (let [event {:x 432 :y 350}
            state {:status :config-x-type}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 2]})))

    (it "Returns nil if no valid button is clicked"
      (let [event {:x 1 :y 1}
            state {:status :config-x-type}]
        (core/mouse-clicked state event)
        (should-not-have-invoked :update-state)))
    )

  (context "config o-type"
    (it "Invokes update state with option 1 if button 1 is clicked"
      (let [event {:x 144 :y 350}
            state {:status :config-o-type}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]})))

    (it "Invokes update state with option 2 if button 2 is clicked"
      (let [event {:x 432 :y 350}
            state {:status :config-o-type}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 2]})))

    (it "Returns nil if no valid button is clicked"
      (let [event {:x 1 :y 1}
            state {:status :config-o-type}]
        (core/mouse-clicked state event)
        (should-not-have-invoked :update-state)))
    )

  (context "config x-difficulty"
    (it "Invokes update state with option 1 if button 1 is clicked"
      (let [event {:x 288 :y 288}
            state {:status :config-x-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]})))

    (it "Invokes update state with option 2 if button 2 is clicked"
      (let [event {:x 288 :y 432}
            state {:status :config-x-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 2]})))

    (it "Invokes update state with option 3 if button 3 is clicked"
      (let [event {:x 288 :y 576}
            state {:status :config-x-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 3]})))

    (it "Returns nil if no valid button is clicked"
      (let [event {:x 1 :y 1}]
        (should-be-nil (core/mouse-clicked {:status :config-x-difficulty} event))))
    )

  (context "config o-difficulty"
    (it "Invokes update state with option 1 if button 1 is clicked"
      (let [event {:x 288 :y 288}
            state {:status :config-o-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]})))

    (it "Invokes update state with option 2 if button 2 is clicked"
      (let [event {:x 288 :y 432}
            state {:status :config-o-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 2]})))

    (it "Invokes update state with option 3 if button 3 is clicked"
      (let [event {:x 288 :y 576}
            state {:status :config-o-difficulty}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 3]})))

    (it "Returns nil if no valid button is clicked"
      (let [event {:x 1 :y 1}]
        (should-be-nil (core/mouse-clicked {:status :config-o-difficulty} event))))
    )
  #_(it "moves to config o type and sets x-type to Human if human button clicked on config-x-type"
      (let [event     {:x 144 :y 350}
            new-state (core/mouse-clicked (test-core/state-create {:status :config-x-type}) event)]
        (should= (test-core/state-create {:status :config-o-type :x-type :human}) new-state)))

  #_(it "moves to config x difficulty and sets x-type to computer if computer button clicked on config-x-type"
      (let [event     {:x 432 :y 350}
            new-state (core/mouse-clicked (test-core/state-create {:status :config-x-type}) event)]
        (should= (test-core/state-create {:status :config-x-difficulty :x-type :computer}) new-state)))

  #_(it "moves to config o type if x-difficulty is set to easy"
      (let [event     {:x 288 :y 288}
            new-state (core/mouse-clicked (test-core/state-create {:status :config-x-difficulty :x-type :computer}) event)]
        (should= (test-core/state-create {:status :config-o-type :x-type :computer :x-difficulty :easy}) new-state)))

  #_(it "moves to config o type if x-difficulty is set to medium"
      (let [event     {:x 288 :y 432}
            new-state (core/mouse-clicked (test-core/state-create {:status :config-x-difficulty :x-type :computer}) event)]
        (should= (test-core/state-create {:status :config-o-type :x-type :computer :x-difficulty :medium}) new-state)))

  #_(it "moves to config o type if x-difficulty is set to hard"
      (let [event     {:x 288 :y 576}
            new-state (core/mouse-clicked (test-core/state-create {:status :config-x-difficulty :x-type :computer}) event)]
        (should= (test-core/state-create {:status :config-o-type :x-type :computer :x-difficulty :hard}) new-state)))


  #_(context "config o"
      (it "moves to select-board if o-type is set to human"
        (let [event     {:x 144 :y 350}
              new-state (core/mouse-clicked (test-core/state-create {:status :config-o-type}) event)]
          (should= (test-core/state-create {:status :select-board :o-type :human}) new-state)))

      (it "moves to config o difficulty and sets o-type to computer if right side of board clicked on config-o-type"
        (let [event     {:x 432 :y 350}
              new-state (core/mouse-clicked (test-core/state-create {:status :config-o-type}) event)]
          (should= (test-core/state-create {:status :config-o-difficulty :o-type :computer}) new-state)))

      (it "moves to select-board after o-difficulty is set to easy"
        (let [event     {:x 288 :y 288}
              new-state (core/mouse-clicked (test-core/state-create {:status :config-o-difficulty :o-type :computer}) event)]
          (should= (test-core/state-create {:status :select-board :o-type :computer :o-difficulty :easy}) new-state)))

      (it "moves to select-board after o-difficulty is set to medium"
        (let [event     {:x 288 :y 432}
              new-state (core/mouse-clicked (test-core/state-create {:status :config-o-difficulty :o-type :computer}) event)]
          (should= (test-core/state-create {:status :select-board :o-type :computer :o-difficulty :medium}) new-state)))

      (it "moves to select-board after o-difficulty is set to hard"
        (let [event     {:x 288 :y 576}
              new-state (core/mouse-clicked (test-core/state-create {:status :config-o-difficulty :o-type :computer}) event)]
          (should= (test-core/state-create {:status :select-board :o-type :computer :o-difficulty :hard}) new-state)))
      )
  )
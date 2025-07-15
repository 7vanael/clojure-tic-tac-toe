(ns tic-tac-toe.gui.welcome-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]))

(describe "welcome mouse-clicked"
  (with-stubs)

  (it "click anywhere"
    (with-redefs [core/update-state (stub :update-state)]
      (let [event {:x 100 :y 100}
            state {:save :mock :interface :gui :status :welcome}]
        (core/mouse-clicked state event)
        (should-have-invoked :update-state {:with [state 1]}))))
  )
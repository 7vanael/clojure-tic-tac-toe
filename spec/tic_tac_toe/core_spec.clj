(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game-spec :as test-game]))


(describe "core"
  (with-stubs)

  (it "can tell what play-type of turn it is"
    (should= true (human? test-game/state-center-x-mid-turn)))

  (it "can tell what difficulty computer turn it is"
    (should= :hard (get-computer-difficulty test-game/state-computer-2-4-empty)))
  )
(ns tic-tac-toe.turn-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.turn :refer :all]
            [tic-tac-toe.game-spec :as test-game]))

(describe "turn"
  (with-stubs)

  (it "can tell what type of turn it is"
    (should= :human (get-turn-type test-game/state-center-x-mid-turn))
    (should= :hard (get-turn-type test-game/state-computer-2-4-empty)))
  )
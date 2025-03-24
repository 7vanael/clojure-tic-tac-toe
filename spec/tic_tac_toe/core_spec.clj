(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game :as game]))

(describe "main"
    (with-stubs)

  (it "starts a new game"
    (with-redefs [game/start (stub :game/start)]
      (-main)
      (should-have-invoked :game/start))))
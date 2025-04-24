(ns tic-tac-toe.quil-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui :refer :all]
            [tic-tac-toe.board_spec :refer :all :as test-board]
            [tic-tac-toe.user-prompt :as user-prompt]))

(def pre-state
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :config
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

(describe "gui with Quil"

  (it "initializes a null set-up"
    (should= pre-state (setup)))

  (it "displays the board")
  )
(ns tic-tac-toe.tui.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def state-initial
  {:interface           :tui
   :board               test-board/empty-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-4-initial
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-4-first-x
  {:interface           :tui
   :board               test-board/first-X-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-computer-2-4-empty
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def state-easy-initial-4
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :easy}
                         {:character "O" :play-type :computer :difficulty :medium}]})

(def state-center-x
  {:interface           :tui
   :board               test-board/center-x-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-center-x-mid-turn
  {:interface           :tui
   :board               test-board/center-x-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(ns tic-tac-toe.common-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.common :refer :all]
            [tic-tac-toe.tui.game-spec :as test-game]
            [tic-tac-toe.board_spec :as test-board]))

(describe "common"
    (it "changes the active player O"
      (with-out-str
        (should= {:interface           :tui
                  :board               test-board/center-x-board
                  :active-player-index 1
                  :status              :in-progress
                  :players             [{:character "X" :play-type :human :difficulty nil}
                                        {:character "O" :play-type :human :difficulty nil}]}
                 (change-player test-game/state-center-x))))

    (it "changes the active player X"
      (with-out-str
        (should= {:interface           :tui
                  :board               test-board/empty-board
                  :active-player-index 0
                  :status              :in-progress
                  :players             [{:character "X" :play-type :human :difficulty nil}
                                        {:character "O" :play-type :human :difficulty nil}]}
                 (change-player test-game/state-initial))))


    )

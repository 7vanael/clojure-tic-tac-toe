(ns tic-tac-toe.tui.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as user-prompt]
            [tic-tac-toe.tui.game :refer :all]
            [tic-tac-toe.board_spec :as test-board]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.core :as core]))

(def state-initial
  {:interface           :tui
   :board               test-board/empty-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          nil})

(def state-4-initial
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(def state-4-first-x
  {:interface           :tui
   :board               test-board/first-X-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(def state-4-first-x-start-o
  {:interface           :tui
   :board               test-board/first-X-4-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-4-first-x-o
  {:interface           :tui
   :board               test-board/second-X-4-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(def state-computer-2-4-empty
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :computer :difficulty :hard}]
   :turn-phase          nil})

(def state-medium-initial-4
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :medium}
                         {:character "O" :play-type :computer :difficulty :easy}]})

(def state-easy-initial-4
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :medium}
                         {:character "O" :play-type :computer :difficulty :easy}]})

(def state-draw-evaluated
  {:interface           :tui
   :board               test-board/full-board-draw
   :active-player-index 0
   :status              :draw
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-draw
  {:interface           :tui
   :board               test-board/full-board-draw
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-win-x-row-evaluated
  {:interface           :tui
   :board               test-board/not-full-board-x-row-win
   :active-player-index 0
   :status              :winner
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-win-x-row
  {:interface           :tui
   :board               test-board/not-full-board-x-row-win
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-center-x
  {:interface           :tui
   :board               test-board/center-x-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(def state-center-x-mid-turn
  {:interface           :tui
   :board               test-board/center-x-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(def state-center-x-corner-o
  {:interface           :tui
   :board               test-board/center-x-corner-o-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :turn-phase          :input-received})

(describe "game"
  (with-stubs)

  (it "starts a new game"
    (with-redefs [user-prompt/welcome-message (stub :console/welcome)
                  play                        (stub :play)]
      (start state-initial)
      (should-have-invoked :play {:with [state-initial]})))

  (it "ends the game in a draw"
    (with-redefs [user-prompt/announce-draw (stub :console/draw)]
      (with-out-str (play state-draw-evaluated))
      (should-have-invoked :console/draw)))

  (it "ends the game in a win"
    (with-redefs [user-prompt/announce-winner (stub :console/announce-winner)]
      (with-out-str (play state-win-x-row-evaluated))
      (should-have-invoked :console/announce-winner)))

  (it "changes the active player O"
    (with-out-str
      (should= {:interface           :tui
                :board               test-board/center-x-board
                :active-player-index 1
                :status              :in-progress
                :players             [{:character "X" :play-type :human :difficulty nil}
                                      {:character "O" :play-type :human :difficulty nil}]
                :turn-phase          nil}
               (change-player (assoc state-center-x :turn-phase nil)))))

  (it "changes the active player X"
    (with-out-str
      (should= {:interface           :tui
                :board               test-board/empty-board
                :active-player-index 0
                :status              :in-progress
                :players             [{:character "X" :play-type :human :difficulty nil}
                                      {:character "O" :play-type :human :difficulty nil}]
                :turn-phase          nil}
               (change-player state-initial))))

  (it "updates status to winner if a player has won"
    (should= state-win-x-row-evaluated (evaluate-board state-win-x-row)))

  (it "updates status to draw if a board is full and no player has won"
    (should= state-draw-evaluated (evaluate-board state-draw)))

  (it "doesn't let a player play in an occupied space"
    (with-redefs [console/print-number-prompt (stub :print-dup-play-type)]
      (let [result (with-in-str "12\n1\n" (core/take-turn state-center-x-mid-turn))]
        (should-have-invoked :print-dup-play-type {:times 2})
        (should= state-center-x-corner-o result))))

  (it "doesn't let a player play in an occupied space in a 4x grid"
    (with-redefs [console/print-number-prompt (stub :print-dup-play-type)]
      (let [result (with-in-str "junk\n6\n1\n" (core/take-turn state-4-first-x-start-o))]
        (should-have-invoked :print-dup-play-type {:times 3})
        (should= state-4-first-x-o result))))
  )
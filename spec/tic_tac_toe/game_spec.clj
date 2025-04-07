(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def state-draw-evaluated
  {:board               test-board/full-board-draw
   :active-player-index 0
   :status              "draw"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-draw
  {:board               test-board/full-board-draw
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-win-x-row-evaluated
  {:board               test-board/not-full-board-x-row-win
   :active-player-index 0
   :status              "winner"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-win-x-row
  {:board               test-board/not-full-board-x-row-win
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-center-x
  {:board               test-board/center-x-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-center-x-mid-turn
  {:board               test-board/center-x-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-center-x-corner-o
  {:board               test-board/center-x-corner-o-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-initial
  {:board               test-board/empty-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-4-initial
  {:board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-4-first-x
  {:board               test-board/first-X-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-4-first-x-start-o
  {:board               test-board/first-X-4-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(def state-4-first-x-o
  {:board               test-board/second-X-4-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :human}]})

(describe "game"
  (with-stubs)


  (it "starts a new game"
    (with-redefs [console/welcome  (stub :console/welcome)
                  play             (stub :play)]
      (start state-initial)
      (should-have-invoked :play {:with [state-initial]})))

  (it "ends the game in a draw"
    (with-redefs [console/draw (stub :console/draw)]
      (with-out-str (play state-draw-evaluated))
      (should-have-invoked :console/draw)))

  (it "ends the game in a win"
    (with-redefs [console/announce-winner (stub :console/announce-winner)]
      (with-out-str (play state-win-x-row-evaluated))
      (should-have-invoked :console/announce-winner)))

  (it "changes the active player O"
    (with-out-str
      (should= {:board               test-board/center-x-board
                :active-player-index 1
                :status              "in-progress"
                :players             [{:character "X" :play-type :human}
                                      {:character "O" :play-type :human}]}
               (change-player state-center-x))))

  (it "changes the active player X"
    (with-out-str
      (should= {:board               test-board/empty-board
                :active-player-index 0
                :status              "in-progress"
                :players             [{:character "X" :play-type :human}
                                      {:character "O" :play-type :human}]}
               (change-player state-initial))))

  (it "updates status to winner if a player has won"
    (should= state-win-x-row-evaluated (evaluate-board state-win-x-row)))

  (it "updates status to draw if a board is full and no player has won"
    (should= state-draw-evaluated (evaluate-board state-draw)))

  (it "lets a player take a turn, repeatedly asks for input until valid play is selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (should= state-center-x
              (with-in-str "0\n45\njunk\n5\n" (take-turn (assoc state-initial :active-player-index 0))))
      (should-have-invoked :print-dup {:times 4})))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (should= state-4-first-x
              (with-in-str "0\n45\njunk\n6\n" (take-turn state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))

  (it "doesn't let a player play in an occupied space"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (let [result (with-in-str "12\n1\n" (take-turn state-center-x-mid-turn))]
        (should-have-invoked :print-dup {:times 2})
        (should= state-center-x-corner-o result))))

  (it "doesn't let a player play in an occupied space in a 4x grid"
    (with-redefs [console/print-number-prompt (stub :print-dup)]
      (let [result (with-in-str "junk\n6\n1\n" (take-turn state-4-first-x-start-o))]
        (should-have-invoked :print-dup {:times 3})
        (should= state-4-first-x-o result)))))
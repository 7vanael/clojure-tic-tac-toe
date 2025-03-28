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
                                      {:character "O" :play-type :human}]} (change-player state-center-x))))

  (it "changes the active player X"
    (with-out-str
      (should= {:board               test-board/empty-board
                :active-player-index 0
                :status              "in-progress"
                :players             [{:character "X" :play-type :human}
                                      {:character "O" :play-type :human}]} (change-player state-initial))))

  (it "updates status to winner if a player has won"
    (should= state-win-x-row-evaluated (evaluate-board state-win-x-row)))

  (it "updates status to draw if a board is full and no player has won"
    (should= state-draw-evaluated (evaluate-board state-draw)))

  (it "lets a player take a turn"
    (with-redefs [console/get-next-play (stub :next-play {:return [1 1]})]
      (should= state-center-x (take-turn (assoc state-initial :active-player-index 0)))))

  (it "doesn't let a player play in an occupied space"
    (with-redefs [console/occupied            (stub :console/occupied)
                  console/print-number-prompt (stub :print-dup)]
      (let [result (with-in-str "2\n2\n1\n1\n" (take-turn state-center-x-mid-turn))]
        (should-have-invoked :console/occupied)
        (should= state-center-x-corner-o result)))))
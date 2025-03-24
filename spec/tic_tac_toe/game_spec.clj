(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console :as console]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def state-draw-evaluated
  {:board  test-board/full-board-draw
   :player "X"
   :status "draw"})

(def state-draw
  {:board  test-board/full-board-draw
   :player "X"
   :status "in-progress"})

(def state-win-x-row-evaluated
  {:board  test-board/not-full-board-x-row-win
   :player "X"
   :status "winner"})

(def state-win-x-row
  {:board  test-board/not-full-board-x-row-win
   :player "X"
   :status "in-progress"})

(def state-center-x
  {:board  test-board/center-x-board
   :player "X"
   :status "in-progress"})

(def state-initial
  {:board  test-board/empty-board
   :player "O"
   :status "in-progress"})

(describe "game"
  (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= state-initial (initialize-state)))

  (it "starts a new game"
    (with-redefs [initialize-state (stub :initialize-state {:return :initial-state})
                  console/welcome (stub :console/welcome)
                  play (stub :play)]
      (start)
      (should-have-invoked :initialize-state)
      (should-have-invoked :play {:with [:initial-state]})))

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
      (should= {:board  test-board/center-x-board
                :player "O"
                :status "in-progress"} (change-player state-center-x))))

  (it "changes the active player X"
    (with-out-str
      (should= {:board  test-board/empty-board
              :player "X"
              :status "in-progress"} (change-player state-initial))))

  (it "changes state status to winner if a player has won"
    (should= state-win-x-row-evaluated (evaluate-board state-win-x-row)))

  (it "changes state status to draw if a board is full and no player has won"
    (should= state-draw-evaluated (evaluate-board state-draw)))

  (it "lets a player take a turn"
    (with-redefs [console/get-next-play (stub :console/get-next-play {:return [1 1]})]
      (should= state-center-x (take-turn (assoc state-initial :player "X"))))))
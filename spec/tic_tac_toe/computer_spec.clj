(ns tic-tac-toe.computer-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def board-one-remaining
  [["X" "O" 3]
   ["O" "O" "X"]
   ["X" "X" "O"]])

(def state-one-remaining
  {:board               board-one-remaining
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})

(def state-remaining-taken
  {:board               (assoc-in board-one-remaining [0 2] "X")
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})
(def board-o-could-win
  [["X" 2 "X"]
   ["O" "X" "X"]
   [7 "O" "O"]])

(def state-o-could-win
  {:board               board-o-could-win
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer}]})

(def state-o-took-win
  {:board               (assoc-in board-o-could-win [2 0] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer}]})

(def state-o-missed-win
  {:board               (assoc-in board-o-could-win [0 1] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer}]})

(def board-o-about-to-win
  [[1 2 "X"]
   [4 5 "X"]
   [7 "O" "O"]])

(def state-o-about-to-win
  {:board               board-o-about-to-win
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})
(def state-o-blocked
  {:board               (assoc-in board-o-about-to-win [2 0] "X")
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})

(def occupied-corners-4-board
  [["O" 2 3 "X"]
   [5 6 7 8]
   [9 10 11 12]
   ["O" 14 15 "X"]])

(def early-block-needed-4-board
  [["O" 2 3 "X"]
   [5 6 7 8]
   [9 10 11 "X"]
   ["O" 14 15 "X"]])

(describe "computer"

  (it "gets the possible moves"
    (should= [[0 1] [2 0]] (get-possible-moves board-o-could-win)))

  (it "takes the only available move"
    (should= state-remaining-taken (turn state-one-remaining)))

  (it "makes the winning move"
    (should= state-o-took-win (turn state-o-could-win))
    (should-not= state-o-missed-win (turn state-o-could-win)))

  (it "evaluates a board's score, highest for immediate win, lower for distant wins"
    (should= 10 (eval-board test-board/not-full-board-x-column-win 0 "X" "O")))

  (it "evaluates a board's score, lowest for opponent immediate win"
    (should= -10 (eval-board test-board/not-full-board-x-column-win 0 "O" "X")))

  (it "evaluates a board's score weighted by proximity: lower positive for distant wins"
    (should= 6 (eval-board test-board/not-full-board-x-column-win 4 "X" "O")))

  (it "evaluates a board's score weighted by proximity: higher negative for distant opponent win"
    (should= -6 (eval-board test-board/not-full-board-x-column-win 4 "O" "X")))

  (it "evaluates a board's score, 0 for a draw"
    (should= 0 (eval-board test-board/full-board-draw 5 "X" "O"))
    (should= 0 (eval-board test-board/full-board-draw 3 "O" "X")))

  (it "gets the best score for the active player, positive when computer's turn"
    (should= 9 (minimax board-o-could-win "O" "X" "O" 0)))

  (it "gets the best score for the active player, negative when not computer's turn"
    (should=   -9 (minimax board-o-could-win "O" "X" "X" 0)))

  (it "associates scores with possible moves"
    (should-contain [[2 0] 10] (eval-moves board-o-could-win "O" "X")))

  (it "scores all possible moves through game end, including opponent's play"
    (should= [[[0 1] -9] [[2 0] 10]] (eval-moves board-o-could-win "O" "X")))

  (it "blocks the opponents imminent win"
    (should= state-o-blocked (turn state-o-about-to-win)))

  (it "takes corners as the opening moves to reduce calculation time"
    (should= [[0 0] [0 3] [3 0] [3 3]] (mapv first (eval-moves test-board/empty-4-board "X" "O")))
    (should= [[0 0] [0 2] [2 0] [2 2]] (mapv first (eval-moves test-board/empty-board "X" "O"))))

  #_(it "if all corners occupied, it tries to build a winning row/col/diag"
    (should= [[1 0] [2 0]] (mapv first (eval-moves occupied-corners-4-board "X" "O"))))

  #_(it "identifies an opportunity to block a win prior to min-maxing"
    (should= true (block? early-block-needed-4-board "X")))

  #_(it "identifies options to block a win prior to min-maxing"
    (should= [1 3] (blocker early-block-needed-4-board "X")))
  )
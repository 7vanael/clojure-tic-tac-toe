(ns tic-tac-toe.computer-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def board-o-could-win
  [["X" nil "X"]
   ["O" "X" "X"]
   [nil "O" "O"]])

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
  [[nil nil "X"]
   [nil nil "X"]
   [nil "O" "O"]])

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

(describe "computer"

  (it "gets the possible moves"
    (should= [[0 1] [2 0]] (get-possible-moves board-o-could-win)))

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
    (should= -9 (minimax board-o-could-win "O" "X" "X" 0)))

  (it "associates scores with possible moves"
    (should-contain [[2 0] 10] (eval-moves board-o-could-win "O" "X")))

  (it "scores all possible moves through game end, including opponent's play"
    (should= [[[0 1] -9] [[2 0] 10]] (eval-moves board-o-could-win "O" "X")))

  (it "makes the winning move"
    (should= state-o-took-win (turn state-o-could-win))
    (should-not= state-o-missed-win (turn state-o-could-win)))

  (it "blocks the opponents imminent win"
    (should= state-o-blocked (turn state-o-about-to-win))))
(ns tic-tac-toe.computer-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer :refer :all]
            [tic-tac-toe.board_spec :as test-board]))

(def state-initial
  {:board               test-board/empty-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})

(def state-center-x
  {:board               test-board/center-x-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})

(def state-center-x-corner-o
  {:board               test-board/center-x-corner-o-board
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})

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
  {:board               (assoc-in board-o-could-win [0 2] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer}]})

(def state-o-missed-win
  {:board               (assoc-in board-o-could-win [1 0] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer}]})

;x= row, y=column
(describe "computer"

  (it "gets the possible moves"
    (should= [[0 1] [2 0]] (get-possible-moves board-o-could-win)))

  (it "finds the move that will win"
    (should= [2 0] (get-winning-move state-o-could-win [[0 1] [2 0]] )))


  #_(it "chooses the best move"
    (should= state-o-took-win (turn state-o-could-win))
    (should-not= state-o-missed-win (turn state-o-could-win)))

  #_(it "takes a turn"
    (should= state-center-x (turn state-initial)))

  )

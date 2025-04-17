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

(def state-empty-4
  {:board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer}
                         {:character "O" :play-type :human}]})
(def state-easy-empty-4
  {:board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :easy}
                         {:character "O" :play-type :human}]})

(def occupied-corners-4-board
  [["O" 2 3 "X"]
   [5 6 7 8]
   [9 10 11 12]
   ["O" 14 15 "X"]])

(def early-X-3-of-4-col-board
  [["O" 2 3 "X"]
   [5 6 7 8]
   [9 10 11 "X"]
   ["O" 14 15 "X"]])

(def state-medium-initial-4
  {:board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :medium}
                         {:character "O" :play-type :easy}]})

(describe "computer"
  (with-stubs)

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
    (should= 9 (minimax board-o-could-win {:char "O" :opp-char "X" :current-player "O" :depth 0 :max-depth 3})))

  (it "gets the best score for the active player, negative when not computer's turn"
    (should=   -9 (minimax board-o-could-win {:char "O" :opp-char "X" :current-player "X" :depth 0 :max-depth 3})))

  (it "associates scores with possible moves"
    (should-contain [[2 0] 10] (eval-moves state-o-could-win)))

  (it "scores all possible moves through game end, including opponent's play"
    (should= [[[0 1] -9] [[2 0] 10]] (eval-moves state-o-could-win)))

  (it "randomizes the selected move when all are equally scored"
    (let [first-result (turn state-empty-4)
          second-result (turn state-empty-4)]
      (should-not= first-result second-result)))

  (it "scales max-depth to board size"
    (should= 3 (calc-max-depth 4))
    (should= 8 (calc-max-depth 3)))

  (it "blocks the opponents imminent win"
    (should= state-o-blocked (turn state-o-about-to-win)))

  (it "for easy, takes a random space"
    (let [result1 (easy state-easy-empty-4)
          result2 (easy state-easy-empty-4)
          result3 (easy state-easy-empty-4)]
      (should-not (= result1 result2 result3))))

  (it "for medium, calls easy if random number is 0"
    (with-redefs [rand-int (stub :rand-int {:return 0})
                  easy (stub :easy)
                  turn (stub :turn)]
      (medium state-medium-initial-4)
      (should-have-invoked :easy)
      (should-not-have-invoked :turn)))

  (it "for medium, calls turn if random number is anything but 0"
    (with-redefs [rand-int (stub :rand-int {:return 1})
                  easy (stub :easy)
                  turn (stub :turn)]
      (medium state-medium-initial-4)
      (should-have-invoked :turn)
      (should-not-have-invoked :easy)))
  )
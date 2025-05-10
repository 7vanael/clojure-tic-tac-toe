(ns tic-tac-toe.computer.hard-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.computer.hard :refer :all]
            [tic-tac-toe.board_spec :as test-board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]))

(def board-one-remaining
  [["X" "O" 3]
   ["O" "O" "X"]
   ["X" "X" "O"]])

(def state-one-remaining
  {:interface :tui
   :board               board-one-remaining
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :human}]})

(def state-remaining-taken
  {:interface :tui
   :board               (assoc-in board-one-remaining [0 2] "X")
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :human}]})
(def board-o-could-win
  [["X" 2 "X"]
   ["O" "X" "X"]
   [7 "O" "O"]])

(def state-o-could-win
  {:interface :tui
   :board               board-o-could-win
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def state-o-took-win
  {:interface :tui
   :board               (assoc-in board-o-could-win [2 0] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def state-o-missed-win
  {:interface :tui
   :board               (assoc-in board-o-could-win [0 1] "O")
   :active-player-index 1
   :status              "in-progress"
   :players             [{:character "X" :play-type :human}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def board-o-about-to-win
  [[1 2 "X"]
   [4 5 "X"]
   [7 "O" "O"]])

(def state-o-about-to-win
  {:interface :tui
   :board               board-o-about-to-win
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :human}]})
(def state-o-blocked
  {:interface :tui
   :board               (assoc-in board-o-about-to-win [2 0] "X")
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :human}]})

(def state-empty-4
  {:interface :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :human}]})

(def state-computer-2-4-empty
  {:interface           :tui
   :board               test-board/empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(def split-board
  [["X" "X" 3]
   ["O" "X" 6]
   ["O" 8 9]])

(def blocked-split-1
  [["X" "X" 3]
   ["O" "X" 6]
   ["O" 8 "O"]])

(def blocked-split-2
  [["X" "X" "O"]
   ["O" "X" 6]
   ["O" 8 9]])

(def state-split
  (test-core/state-create {:interface :tui :board split-board :active-player-index 1 :x-type :human :o-type :computer :o-difficulty :hard :status :in-progress}))

(def state-split-blocked-1
  (test-core/state-create {:interface :tui :board blocked-split-1 :active-player-index 1 :x-type :human :o-type :computer :o-difficulty :hard :status :in-progress}))

(def state-split-blocked-2
  (test-core/state-create {:interface :tui :board blocked-split-2 :active-player-index 1 :x-type :human :o-type :computer :o-difficulty :hard :status :in-progress}))

(describe "computer- hard mode"
  (with-stubs)


  (it "The computer hard turn method is called if the active player is computer"
    (with-redefs [hard (stub :computer-turn)]
      (core/take-turn state-computer-2-4-empty)
      (should-have-invoked :computer-turn)))

  (it "takes the only available move"
    (should= state-remaining-taken (core/take-turn state-one-remaining)))

  (it "makes the winning move"
    (should= state-o-took-win (core/take-turn state-o-could-win))
    (should-not= state-o-missed-win (core/take-turn state-o-could-win)))

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
    (should= -9 (minimax board-o-could-win {:char "O" :opp-char "X" :current-player "X" :depth 0 :max-depth 3})))

  (it "associates scores with possible moves"
    (should-contain [[2 0] 10] (eval-moves state-o-could-win)))

  (it "scores all possible moves through game end, including opponent's play"
    (should= [[[0 1] -9] [[2 0] 10]] (eval-moves state-o-could-win)))

  (it "randomizes the selected move when all are equally scored"
    (let [first-result  (core/take-turn state-empty-4)
          second-result (core/take-turn state-empty-4)
          third-result (core/take-turn state-empty-4)]
      (should-not (= first-result second-result third-result))))

  (it "scales max-depth to board size"
    (should= 8 (calc-max-depth 9))
    (should= 3 (calc-max-depth 16))
    (should= 2 (calc-max-depth 27)))

  (it "blocks the opponents imminent win"
    (should= state-o-blocked (core/take-turn state-o-about-to-win)))

  #_(it "blocks a fork 1"
    (should= (:board state-split-blocked-1) (:board (hard state-split))))
  #_(it "blocks a fork 2"
    (should= (:board state-split-blocked-2) (:board (hard state-split))))
  )
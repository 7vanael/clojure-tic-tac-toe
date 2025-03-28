(ns tic-tac-toe.board_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]))

(def empty-board
  [[nil nil nil]
   [nil nil nil]
   [nil nil nil]])

(def center-x-board
  [[nil nil nil]
   [nil "X" nil]
   [nil nil nil]])

(def center-x-corner-o-board
  [["O" nil nil]
   [nil "X" nil]
   [nil nil nil]])

(def full-board-draw
  [["X" "X" "O"]
   ["O" "O" "X"]
   ["X" "O" "X"]])

(def not-full-board-x-row-win
  [["X" nil "O"]
   ["O" "O" nil]
   ["X" "X" "X"]])

(def not-full-board-x-column-win
  [["X" nil "O"]
   ["X" "O" nil]
   ["X" "O" "X"]])

(def not-full-board-x-ortho-diag-win
  [["X" nil "X"]
   ["O" "X" nil]
   ["X" "O" "O"]])

(def not-full-board-x-diag-win
  [["X" nil "O"]
   ["O" "X" nil]
   ["X" "O" "X"]])

(def full-board-x-column-win
  [["X" "X" "O"]
   ["X" "O" "O"]
   ["X" "O" "X"]])

(describe "board"
  (it "creates a new board"
    (should= [[nil nil nil]
              [nil nil nil]
              [nil nil nil]]
             (new-board)))

  (it "checks if a position is available"
    (should= true (available? empty-board [1 1]))
    (should= false (available? center-x-board [1 1])))

  (it "allows a player to take an empty square"
    (should= [[nil nil nil]
              [nil "X" nil]
              [nil nil nil]]
             (take-square empty-board [1 1] "X")))

  (it "does not take a square for a player if it is occupied"
    (should= [[nil nil nil]
              [nil "X" nil]
              [nil nil nil]]
             (take-square center-x-board [1 1] "O")))

  (it "checks if the board is full"
    (should-not (any-space-available? full-board-draw))
    (should (any-space-available? not-full-board-x-column-win)))

  (it "checks if there is a player with an entire row"
    (should= true (win-row? not-full-board-x-row-win "X"))
    (should-not (win-row? not-full-board-x-diag-win "X"))
    (should-not (win-row? not-full-board-x-row-win "O")))

  (it "checks if there is a player with an entire column"
    (should= true (win-column? not-full-board-x-column-win "X"))
    (should-not (win-column? not-full-board-x-column-win "O"))
    (should-not (win-column? not-full-board-x-row-win "X")))

  (it "checks if a player has a diagonal"
    (should= true (win-diag? not-full-board-x-diag-win "X"))
    (should= true (win-diag? not-full-board-x-ortho-diag-win "X"))
    (should-not (win-diag? not-full-board-x-row-win "X")))


  (it "checks if there is a winner"
    (should= true (winner? not-full-board-x-row-win "X"))
    (should= true (winner? not-full-board-x-column-win "X"))
    (should= true (winner? not-full-board-x-diag-win "X"))
    (should= true (winner? full-board-x-column-win "X"))
    (should= false (winner? full-board-x-column-win "O"))
    (should= false (winner? not-full-board-x-row-win "O"))
    (should= false (winner? not-full-board-x-diag-win "O"))
    (should= false (winner? full-board-draw "O"))))
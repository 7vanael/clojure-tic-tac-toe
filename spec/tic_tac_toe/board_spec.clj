(ns tic-tac-toe.board_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]))

(def empty-board
  [[1 2 3]
   [4 5 6]
   [7 8 9]])

(def empty-4-board
  [[1 2 3 4]
   [5 6 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def first-X-4-board
  [[1 2 3 4]
   [5 "X" 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def second-X-4-board
  [["O" 2 3 4]
   [5 "X" 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def full-draw-4-board
  [["O" "O" "O" "X"]
   ["X" "X" "O" "X"]
   ["X" "O" "X" "O"]
   ["O" "O" "X" "X"]])

(def row-win-X-4-board
  [["O" "O" 3 "X"]
   ["X" "X" "X" "X"]
   ["X" "O" "X" "O"]
   ["O" "O" "X" "X"]])

(def col-win-X-4-board
  [["O" "O" "X" "X"]
   ["X" 6 "X" "X"]
   ["X" "O" "X" "O"]
   ["O" "O" "X" "X"]])

(def diag-win-X-4-board
  [["O" "O" "O" "X"]
   ["X" 6 "X" "X"]
   ["X" "X" "O" "O"]
   ["X" "O" "X" "X"]])

(def center-x-board
  [[1 2 3]
   [4 "X" 6]
   [7 8 9]])

(def center-x-corner-o-board
  [["O" 2 3]
   [4 "X" 6]
   [7 8 9]])

(def full-board-draw
  [["X" "X" "O"]
   ["O" "O" "X"]
   ["X" "O" "X"]])

(def not-full-board-x-row-win
  [["X" 2 "O"]
   ["O" "O" 6]
   ["X" "X" "X"]])

(def not-full-board-x-column-win
  [["X" 2 "O"]
   ["X" "O" 6]
   ["X" "O" "X"]])

(def not-full-board-x-ortho-diag-win
  [["X" 2 "X"]
   ["O" "X" 6]
   ["X" "O" "O"]])

(def not-full-board-x-diag-win
  [["X" 2 "O"]
   ["O" "X" 6]
   ["X" "O" "X"]])

(def full-board-x-column-win
  [["X" "X" "O"]
   ["X" "O" "O"]
   ["X" "O" "X"]])

(describe "board"
  (it "creates a new board"
    (should= empty-board
             (new-board 3)))

  (it "creates a new board"
    (should= empty-4-board
             (new-board 4)))

  (it "checks if a position is available"
    (should= true (available? empty-board [1 1]))
    (should= true (available? empty-4-board [1 1]))
    (should= false (available? center-x-board [1 1]))
    (should= false (available? first-X-4-board [1 1])))

  (it "returns the numbers of available positions"
    (should= [2 6] (play-options not-full-board-x-column-win))
    (should= [6] (play-options diag-win-X-4-board)))

  (it "takes a single number and returns the coordinates of that position"
    (should= [0 1] (space->coordinates 2 not-full-board-x-column-win))
    (should= [1 2] (space->coordinates 6 not-full-board-x-column-win))
    (should= [1 1] (space->coordinates 6 diag-win-X-4-board)))

  (it "allows a player to take an empty square"
    (should= center-x-board
             (take-square empty-board [1 1] "X"))
    (should= first-X-4-board
             (take-square empty-4-board [1 1] "X")))

  (it "does not take a square for a player if it is occupied"
    (should= center-x-board
             (take-square center-x-board [1 1] "O")))

  (it "checks if the board is full"
    (should-not (any-space-available? full-board-draw))
    (should-not (any-space-available? full-draw-4-board))
    (should (any-space-available? not-full-board-x-column-win)))

  (it "checks if there is a player with an entire row"
    (should= true (win-row? not-full-board-x-row-win "X"))
    (should-not (win-row? not-full-board-x-diag-win "X"))
    (should-not (win-row? not-full-board-x-row-win "O"))
    (should-not (win-row? col-win-X-4-board "O"))
    (should= true (win-row? row-win-X-4-board "X")))

  (it "checks if there is a player with an entire column"
    (should= true (win-column? not-full-board-x-column-win "X"))
    (should= true (win-column? col-win-X-4-board "X"))
    (should-not (win-column? not-full-board-x-column-win "O"))
    (should-not (win-column? not-full-board-x-row-win "X")))

  (it "checks if a player has a diagonal"
    (should= true (win-diag? not-full-board-x-diag-win "X"))
    (should= true (win-diag? diag-win-X-4-board "X"))
    (should= true (win-diag? not-full-board-x-ortho-diag-win "X"))
    (should-not (win-diag? not-full-board-x-row-win "X")))


  (it "checks if there is a winner"
    (should= true (winner? not-full-board-x-row-win "X"))
    (should= true (winner? col-win-X-4-board "X"))
    (should= true (winner? not-full-board-x-column-win "X"))
    (should= true (winner? not-full-board-x-diag-win "X"))
    (should= true (winner? full-board-x-column-win "X"))
    (should= false (winner? full-board-x-column-win "O"))
    (should= false (winner? col-win-X-4-board "O"))
    (should= false (winner? not-full-board-x-row-win "O"))
    (should= false (winner? not-full-board-x-diag-win "O"))
    (should= false (winner? full-board-draw "O"))))
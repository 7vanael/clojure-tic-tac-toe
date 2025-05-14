(ns tic-tac-toe.board_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.core-spec :as test-core]))

(def empty-board
  [[1 2 3]
   [4 5 6]
   [7 8 9]])

(def empty-4-board
  [[1 2 3 4]
   [5 6 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def empty-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 8 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])

(def first-x-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 "X" 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])

(def cube-diag-board
  [[["X" 2 3]
    [4 5 "O"]
    [7 8 9]]
   [[10 11 12]
    [13 "X" "O"]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 "X"]]])

(def conventional-row-3d-board
  [[["X" "X" "X"]
    [4 5 "O"]
    [7 "O" 9]]
   [[10 11 12]
    [13 14 "O"]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 "X"]]])

(def z-line-board
  [[["X" 2 3]
    [4 5 "O"]
    [7 "O" 9]]
   [["X" 11 12]
    [13 14 "O"]
    [16 17 18]]
   [["X" 20 21 ]
    [22 23 24]
    [25 26 "X"]]])

(def z-diag-board
  [[["X" 2 3]
    [4 5 "O"]
    [7 "O" 9]]
   [[10 "X" 12]
    [13 "O" "O"]
    [16 17 18]]
   [[19 "O" "X"]
    [22 23 24]
    [25 26 "X"]]])

(def not-full-3d
  [[[1 "O" "O"]
    ["O" "X" 6]
    ["X" "O" "O"]]
   [[10 11 "X"]
    ["O" "X" "O"]
    ["O" "O" "X"]]
   [["X" "O" "O"]
    ["X" "O" "X"]
    [25 "X" "O"]]])

(def full-3d
  [[["X" "O" "O"]
    ["O" "X" "X"]
    ["X" "O" "O"]]
   [["O" "O" "X"]
    ["X" "X" "O"]
    ["O" "O" "X"]]
   [["X" "X" "O"]
    ["O" "O" "X"]
    ["X" "X" "O"]]])

(def first-X-4-board
  [[1 2 3 4]
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

(def center-x-corner-xo-board
  [["O" 2 3]
   [4 "X" 6]
   ["X" 8 9]])

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

  (it "gets the board complexity"
    (should= :single-digit (get-size-complexity 3))
    (should= 3 (get-size-complexity [3 3 3])))

  (it "creates a new 2D board"
    (should= empty-board
             (new-board 3)))

  (it "creates a new board"
    (should= empty-4-board
             (new-board 4)))

  (it "creates a new 3x3x3 board"
    (should= empty-3d-board (new-board [3 3 3])))

  (it "checks if a position is available in 2 or 3d"
    (should= true (available? empty-board [1 1]))
    (should= true (available? empty-4-board [1 1]))
    (should= false (available? center-x-board [1 1]))
    (should= false (available? first-X-4-board [1 1]))
    (should= true (available? empty-3d-board [1 1 1]))
    (should= false (available? cube-diag-board [0 0 0]))
    (should= false (available? cube-diag-board [1 1 2])))

  (it "returns the numbers of available positions"
    (should= [2 6] (play-options not-full-board-x-column-win))
    (should= [6] (play-options diag-win-X-4-board))
    (should= [1 6 10 11 25] (play-options not-full-3d)))

  (it "gets the complexity of the board"
    (should= 2 (get-board-complexity empty-board))
    (should= 3 (get-board-complexity empty-3d-board)))

  (it "takes a single number and returns the coordinates of that position on a 2 or 3d board"
    (should= [0 1] (space->coordinates 2 not-full-board-x-column-win))
    (should= [1 2] (space->coordinates 6 not-full-board-x-column-win))
    (should= [1 1] (space->coordinates 6 diag-win-X-4-board))
    (should= [1 1 2] (space->coordinates 15 empty-3d-board)))

  (it "allows a player to take an empty square in 2 or 3d"
    (should= center-x-board
             (take-square empty-board [1 1] "X"))
    (should= first-X-4-board
             (take-square empty-4-board [1 1] "X"))
    (should= first-x-3d-board
             (take-square empty-3d-board [0 2 1] "X")))

  (it "does not take a square for a player if it is occupied"
    (should= center-x-board
             (take-square center-x-board [1 1] "O"))
    (should= first-x-3d-board
             (take-square first-x-3d-board [0 2 1] "O")))

  (it "checks if the board is full"
    (should-not (any-space-available? full-board-draw))
    (should-not (any-space-available? full-draw-4-board))
    (should (any-space-available? not-full-board-x-column-win))
    (should (any-space-available? cube-diag-board))
    (should (any-space-available? first-x-3d-board))
    (should-not (any-space-available? full-3d)))

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


  (it "checks if there is a winner for 2-d boards"
    (should= true (winner? not-full-board-x-row-win "X"))
    (should= true (winner? col-win-X-4-board "X"))
    (should= true (winner? not-full-board-x-column-win "X"))
    (should= true (winner? not-full-board-x-diag-win "X"))
    (should= true (winner? full-board-x-column-win "X"))
    (should= false (winner? full-board-x-column-win "O"))
    (should= false (winner? col-win-X-4-board "O"))
    (should= false (winner? not-full-board-x-row-win "O"))
    (should= false (winner? not-full-board-x-diag-win "O"))
    (should= false (winner? full-board-draw "O")))

  (it "checks a 3d board for a conventional win"
    (should= true (win-3d-panel? conventional-row-3d-board "X"))
    (should-not (win-3d-panel? empty-3d-board "X")))

  (it "gets a z-line from a board"
    (should= [1 10 "X"] (get-z-line not-full-3d [0 0])))

  (it "checks for a z-line win in 3-d board"
    (should (win-3d-z-line? z-line-board "X"))
    (should-not (win-3d-z-line? z-diag-board "O")))

  (it "gets the diagonal starts & steps for y-plane diagonals (for 1 y)"
    (should= [[[0 0 1] [1 1 0]]
              [[2 0 1] [-1 1 0]]] (y-plane-diags 1 3)))

  (it "gets the diagonal starts & steps for x-plane diagonals (for 0 x)"
    (should= [[[0 0 0] [1 0 1]]
              [[0 0 2] [1 0 -1]]] (x-plane-diags 0 3)))

  (it "gets the coordinates of a line when given the board, start and step"
    (should= [[0 0] [0 1] [0 2]] (->line-coordinates [[1 2 3] [4 5 6] [7 8 9]] [0 0] [0 1])))

  (it "gets all lines (rows/cols/diags) from a 2d board"
    (should= [[[0 0] [0 1] [0 2]] [[1 0] [1 1] [1 2]] [[2 0] [2 1] [2 2]]
              [[0 0] [1 0] [2 0]] [[0 1] [1 1] [2 1]] [[0 2] [1 2] [2 2]]
              [[0 0] [1 1] [2 2]] [[0 2] [1 1] [2 0]]]
             (get-all-lines empty-board))
    (should= [[[0 0] [0 1] [0 2] [0 3]] [[1 0] [1 1] [1 2] [1 3]] [[2 0] [2 1] [2 2] [2 3]]
              [[3 0] [3 1] [3 2] [3 3]] [[0 0] [1 0] [2 0] [3 0]] [[0 1] [1 1] [2 1] [3 1]]
              [[0 2] [1 2] [2 2] [3 2]] [[0 3] [1 3] [2 3] [3 3]] [[0 0] [1 1] [2 2] [3 3]]
              [[0 3] [1 2] [2 1] [3 0]]]
             (get-all-lines empty-4-board)))

  (it "gets all lines from a 3d board"
    (should= '(([0 0 0] [1 1 1] [2 2 2]) ([0 0 2] [1 1 1] [2 2 0]) ([0 2 0] [1 1 1] [2 0 2]) ([0 2 2] [1 1 1] [2 0 0]) ([0 0 0] [1 0 1] [2 0 2]) ([0 0 2] [1 0 1] [2 0 0]) ([0 1 0] [1 1 1] [2 1 2]) ([0 1 2] [1 1 1] [2 1 0]) ([0 2 0] [1 2 1] [2 2 2]) ([0 2 2] [1 2 1] [2 2 0]) ([0 0 0] [1 1 0] [2 2 0]) ([2 0 0] [1 1 0] [0 2 0]) ([0 0 1] [1 1 1] [2 2 1]) ([2 0 1] [1 1 1] [0 2 1]) ([0 0 2] [1 1 2] [2 2 2]) ([2 0 2] [1 1 2] [0 2 2]) ([0 0 0] [0 1 1] [0 2 2]) ([0 0 2] [0 1 1] [0 2 0]) ([1 0 0] [1 1 1] [1 2 2]) ([1 0 2] [1 1 1] [1 2 0]) ([2 0 0] [2 1 1] [2 2 2]) ([2 0 2] [2 1 1] [2 2 0]) [[0 0 0] [1 0 0] [2 0 0]] [[0 0 1] [1 0 1] [2 0 1]] [[0 0 2] [1 0 2] [2 0 2]] [[0 1 0] [1 1 0] [2 1 0]] [[0 1 1] [1 1 1] [2 1 1]] [[0 1 2] [1 1 2] [2 1 2]] [[0 2 0] [1 2 0] [2 2 0]] [[0 2 1] [1 2 1] [2 2 1]] [[0 2 2] [1 2 2] [2 2 2]] [[0 0 0] [0 0 1] [0 0 2]] [[0 1 0] [0 1 1] [0 1 2]] [[0 2 0] [0 2 1] [0 2 2]] [[0 0 0] [0 1 0] [0 2 0]] [[0 0 1] [0 1 1] [0 2 1]] [[0 0 2] [0 1 2] [0 2 2]] [[0 0 0] [0 1 1] [0 2 2]] [[0 0 2] [0 1 1] [0 2 0]] [[1 0 0] [1 0 1] [1 0 2]] [[1 1 0] [1 1 1] [1 1 2]] [[1 2 0] [1 2 1] [1 2 2]] [[1 0 0] [1 1 0] [1 2 0]] [[1 0 1] [1 1 1] [1 2 1]] [[1 0 2] [1 1 2] [1 2 2]] [[1 0 0] [1 1 1] [1 2 2]] [[1 0 2] [1 1 1] [1 2 0]] [[2 0 0] [2 0 1] [2 0 2]] [[2 1 0] [2 1 1] [2 1 2]] [[2 2 0] [2 2 1] [2 2 2]] [[2 0 0] [2 1 0] [2 2 0]] [[2 0 1] [2 1 1] [2 2 1]] [[2 0 2] [2 1 2] [2 2 2]] [[2 0 0] [2 1 1] [2 2 2]] [[2 0 2] [2 1 1] [2 2 0]])
             (get-all-lines empty-3d-board)))

  (it "gets the values from a line given a board, a starting position and a step"
    (let [start [0 0 0]
          step [0 1 1]]
      (should= [1 5 9] (start-step->values empty-3d-board start step)))
    (let [start [2 0 1]
          step [-1 1 0]]
      (should= [20 14 8] (start-step->values empty-3d-board start step))))

  (it "finds a diagonal win in a 3d board"
    (should= false (win-3d-diag? empty-3d-board "X"))
    (should= false (win-3d-diag? conventional-row-3d-board "X"))
    (should= true (win-3d-diag? cube-diag-board "X")))

  (it "checks if there is a winner for 3-d boards"
    (should= true (winner? conventional-row-3d-board "X"))
    (should= true (winner? z-diag-board "X"))
    (should= true (winner? z-line-board "X"))
    (should= true (winner? cube-diag-board "X"))
    (should= false (winner? not-full-3d "X")))

  (it "updates status to winner if a player has won"
    (should= (test-core/state-create {:board not-full-board-x-row-win :active-player-index 0 :status :winner})
             (evaluate-board (test-core/state-create {:board not-full-board-x-row-win :active-player-index 0 :status :in-progress}))))

  (it "updates status to tie if a board is full and no player has won"
    (should= (test-core/state-create {:board full-board-draw :active-player-index 0 :status :tie})
             (evaluate-board (test-core/state-create {:board full-board-draw :active-player-index 0 :status :in-progress}))))
  )
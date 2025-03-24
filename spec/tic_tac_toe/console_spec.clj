(ns tic-tac-toe.console-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console :refer :all]
            [tic-tac-toe.board_spec :refer :all :as test-board]))

(describe "console"
  (with-stubs)

  (it "prints a welcome message"
    (should= "Welcome to tic-tac-toe!\n"
             (with-out-str (welcome))))

  (it "prints the board state"
    (should= "  |   |  \n--|---|---\n  |   |  \n--|---|---\n  |   |  \n"
             (with-out-str (display-board test-board/empty-board)))

    (should= "  |   |  \n--|---|---\n  | X |  \n--|---|---\n  |   |  \n"
             (with-out-str (display-board test-board/center-x-board))))

  (it "validates an entry"
    (should= true (validate-number 2))
    (should= false (validate-number 4))
    (should= false (validate-number "c")))

  (it "requests user input to select a row"
    (with-redefs [get-input (stub :get-input {:return 0})]
      (should= "Which row do you want to play in?\n"
               (with-out-str (get-row)))))

  (it "requests user input to select a column"
    (with-redefs [get-input (stub :get-input {:return 0})]
      (should= "Which column do you want to play in?\n"
               (with-out-str (get-column)))))

  (it "obtains coordinates for a square the player wants to play in"
    (with-redefs [get-row (stub :get-row {:return 1})
                  get-column (stub :get-column {:return 1})]
      (should= [1 1] (get-next-play))))

  (it "notifies the player that a play wasn't valid & returns the same state"
    (should= "That isn't a valid play, please try again\n"
             (with-out-str (occupied :state)))
    (should= :state (occupied :state)))

  (it "notifies the player that the game is a draw"
    (should= "It's a draw! Good game!\n"
             (with-out-str (draw))))

  (it "announces the winner of a game"
    (should= "X wins! Good game!\n" (with-out-str (announce-winner "X"))))
  )
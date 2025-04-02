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

  (it "prints the number prompt"
    (should= "Which row do you want to play in?\nPlease enter a number (1-3)\n"
             (with-out-str (print-number-prompt "row"))))

  (it "gets input from the user until a valid entry is provided"
    (with-redefs [print-number-prompt (stub :print-prompt)]
      (should= 0 (with-in-str "c\n5\n6\n1\nc" (get-input "")))))

  (it "notifies the player that a play wasn't valid & returns the same state"
    (should= "That isn't a valid play, please try again\n"
             (with-out-str (occupied))))

  (it "notifies the player that the game is a draw"
    (should= "It's a draw! Good game!\n" (with-out-str (draw))))

  (it "announces the winner of a game"
    (should= "X wins! Good game!\n" (with-out-str (announce-winner "X"))))

  (it "displays the options for players to choose from"
    (should= "Who will play  X ?\nhuman\ncomputer\n"
             (with-out-str (display-options "X" [:human :computer]))))

  (it "asks the user for who should play character O"
    (with-redefs [display-options (stub :display-options)]
      (should= :human (with-in-str ":human\n" (get-player-type "X" [:human :computer])))
      (should-have-invoked :display-options))))
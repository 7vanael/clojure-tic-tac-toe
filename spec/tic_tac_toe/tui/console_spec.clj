(ns tic-tac-toe.tui.console-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.tui.console :refer :all]
            [tic-tac-toe.board_spec :refer :all :as test-board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]))

(describe "console"
  (with-stubs)

  (it "prints a welcome message"
    (should= "Welcome to tic-tac-toe!\n"
             (with-out-str (welcome-message))))

  (it "prints the board state"
    (should= "  1 |  2 |  3 \n----|----|----\n  4 |  5 |  6 \n----|----|----\n  7 |  8 |  9 \n"
             (with-out-str (display-board test-board/empty-board)))

    (should= "  1 |  2 |  3 \n----|----|----\n  4 |  X |  6 \n----|----|----\n  7 |  8 |  9 \n"
             (with-out-str (display-board test-board/center-x-board))))

  (it "prints the board state for a 4x board"
    (should= "  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  6 |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n"
             (with-out-str (display-board test-board/empty-4-board)))

    (should= "  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  X |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n"
             (with-out-str (display-board test-board/first-X-4-board))))


  (it "prints the number prompt"
    (should= "Please enter the number for the space you'd like to take\n"
             (with-out-str (print-number-prompt))))

  (it "gets input from the user until a valid entry is provided"
    (with-redefs [print-number-prompt (stub :print-prompt)
                  announce-player (stub :print-dup)];need to feed in a state here instead of interface
      (should= 6 (with-in-str "c\n26\n6\n1\n" (get-next-play
                                                (test-core/state-create {:interface :tui :board [["X" 2 "O"]["X" "O" 6] ["O" "X" "O"]]})
                                                [2 6])))))

  (it "notifies the player that a play wasn't valid"
    (should= "That isn't a valid play, please try again\n"
             (with-out-str (invalid-selection))))

  (it "notifies the player that the game is a draw"
    (should= "It's a draw! Good game!\n" (with-out-str (core/update-state {:interface :tui :status :tie}))))

  (it "announces the winner of a game"
    (should= "X wins! Good game!\n" (with-out-str (core/update-state (test-core/state-create {:interface :tui :status :winner :active-player-index 0})))))

  (it "displays the options for players to choose from"
    (should= "Who will play  X ?\nhuman\ncomputer\n"
             (with-out-str (display-play-type-options "X" [:human :computer]))))

  (it "asks the user for who should play character O"
    (with-redefs [display-play-type-options (stub :display-options)]
      (should= :human (with-in-str "human\n" (get-player-type "X" [:human :computer])))
      (should-have-invoked :display-options)))

  (it "asks the player if they want to play again"
    (should= "Would you like to play again? (y/n)\n"
             (with-out-str (play-again-prompt))))

  (it "validates a user's selection to play again or not"
    (should= true (validate-play-again "y"))
    (should= true (validate-play-again "yes"))
    (should= true (validate-play-again "no"))
    (should= true (validate-play-again "n"))
    (should= false (validate-play-again "1"))
    (should= false (validate-play-again "g"))
    (should= false (validate-play-again "ned"))
    (should= false (validate-play-again "november"))
    (should= false (validate-play-again "yesterday"))
    (should= false (validate-play-again "Y4")))

  (it "returns true if the user wants to play again"
    (with-redefs [play-again-prompt (stub :prompt)]
      (should= true (with-in-str "Y\n" (play-again?)))
      (should= true (with-in-str "Yes\n" (play-again?)))
      (should= true (with-in-str "y\n" (play-again?)))
      (should= true (with-in-str "yes\n" (play-again?)))
      (should= true (with-in-str "none\nyes\n" (play-again?)))
      (should= true (with-in-str "yesterday\nyes\n" (play-again?)))
      (should= false (with-in-str "N\n" (play-again?)))
      (should= false (with-in-str "No\n" (play-again?)))
      (should= false (with-in-str "yesterday\nno\n" (play-again?)))
      (should= false (with-in-str "yell\nn\n" (play-again?)))
      (should= false (with-in-str "noise\nn\n" (play-again?)))
      (should= false (with-in-str "None\nn\n" (play-again?)))
      (should= false (with-in-str "nonsense\nn\n" (play-again?)))))

  (it "prints the board-size options"
    (should= "What size board do you want to play on?\n3) 3x3\n4) 4x4\n"
             (with-out-str (board-size-prompt [3 4]))))

  (it "allows the player to select a board size of 3 or 4"
    (with-redefs [board-size-prompt (stub :size-prompt)]
      (should= 3 (with-in-str "3\n" (get-board-size [3 4])))
      (should= 4 (with-in-str "4\n" (get-board-size [3 4])))
      (should= 3 (with-in-str "6\ngesf\nhello\n3\n4\n" (get-board-size [3 4])))))

  (it "prints the difficulty options"
    (should= "What difficulty setting should X use?\neasy\nmedium\nhard\n"
             (with-out-str (display-difficulty-options "X" [:easy :medium :hard]))))

  (it "asks the user for the difficulty selection for character X"
    (with-redefs [display-difficulty-options (stub :display-options)]
      (should= :hard (with-in-str "hard\n" (get-difficulty "X" [:easy :medium :hard])))
      (should-have-invoked :display-options)))
  )
(ns tic-tac-toe.console-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console :refer :all]
            [tic-tac-toe.board_spec :refer :all :as test-board]
            [tic-tac-toe.core :as user-prompt]
            [tic-tac-toe.game-spec :as test-game]))

(describe "console"
  (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= test-game/state-initial (initialize-state {:type-x       :human :type-o :human :difficulty-x nil
                                                        :difficulty-o nil :board-size 3 :interface :tui :turn-phase nil})))

  (it "prints a welcome message"
    (should= "Welcome to tic-tac-toe!\n"
             (with-out-str (user-prompt/welcome-message {:interface :tui}))))

  (it "prints the board state"
    (should= "  1 |  2 |  3 \n----|----|----\n  4 |  5 |  6 \n----|----|----\n  7 |  8 |  9 \n"
             (with-out-str (user-prompt/display-board {:interface :tui} test-board/empty-board)))

    (should= "  1 |  2 |  3 \n----|----|----\n  4 |  X |  6 \n----|----|----\n  7 |  8 |  9 \n"
             (with-out-str (user-prompt/display-board {:interface :tui} test-board/center-x-board))))

  (it "prints the board state for a 4x board"
    (should= "  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  6 |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n"
             (with-out-str (user-prompt/display-board {:interface :tui} test-board/empty-4-board)))

    (should= "  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  X |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n"
             (with-out-str (user-prompt/display-board {:interface :tui} test-board/first-X-4-board))))


  (it "prints the number prompt"
    (should= "Please enter the number for the space you'd like to take\n"
             (with-out-str (print-number-prompt))))

  (it "gets input from the user until a valid entry is provided"
    (with-redefs [print-number-prompt (stub :print-prompt)]
      (should= 6 (with-in-str "c\n26\n6\n1\n" (user-prompt/get-next-play {:interface :tui} [2 6])))))

  (it "notifies the player that a play wasn't valid"
    (should= "That isn't a valid play, please try again\n"
             (with-out-str (invalid-selection))))

  (it "notifies the player that the game is a draw"
    (should= "It's a draw! Good game!\n" (with-out-str (user-prompt/announce-draw {:interface :tui}))))

  (it "announces the winner of a game"
    (should= "X wins! Good game!\n" (with-out-str (user-prompt/announce-winner {:interface :tui} "X"))))

  (it "displays the options for players to choose from"
    (should= "Who will play  X ?\nhuman\ncomputer\n"
             (with-out-str (display-play-type-options "X" [:human :computer]))))

  (it "asks the user for who should play character O"
    (with-redefs [display-play-type-options (stub :display-options)]
      (should= :human (with-in-str "human\n" (user-prompt/get-player-type {:interface :tui} "X" [:human :computer])))
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
      (should= true (with-in-str "Y\n" (user-prompt/play-again? {:interface :tui})))
      (should= true (with-in-str "Yes\n" (user-prompt/play-again? {:interface :tui})))
      (should= true (with-in-str "y\n" (user-prompt/play-again? {:interface :tui})))
      (should= true (with-in-str "yes\n" (user-prompt/play-again? {:interface :tui})))
      (should= true (with-in-str "none\nyes\n" (user-prompt/play-again? {:interface :tui})))
      (should= true (with-in-str "yesterday\nyes\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "N\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "No\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "yesterday\nno\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "yell\nn\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "noise\nn\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "None\nn\n" (user-prompt/play-again? {:interface :tui})))
      (should= false (with-in-str "nonsense\nn\n" (user-prompt/play-again? {:interface :tui})))))

  (it "prints the board-size options"
    (should= "What size board do you want to play on?\n3) 3x3\n4) 4x4\n"
             (with-out-str (board-size-prompt [3 4]))))

  (it "allows the player to select a board size of 3 or 4"
    (with-redefs [board-size-prompt (stub :size-prompt)]
      (should= 3 (with-in-str "3\n" (user-prompt/get-board-size {:interface :tui} [3 4])))
      (should= 4 (with-in-str "4\n" (user-prompt/get-board-size {:interface :tui} [3 4])))
      (should= 3 (with-in-str "6\ngesf\nhello\n3\n4\n" (user-prompt/get-board-size {:interface :tui} [3 4])))))

  (it "prints the difficulty options"
    (should= "What difficulty setting should X use?\neasy\nmedium\nhard\n"
             (with-out-str (display-difficulty-options "X" [:easy :medium :hard]))))

  (it "asks the user for the difficulty selection for character X"
    (with-redefs [display-difficulty-options (stub :display-options)]
      (should= :hard (with-in-str "hard\n" (user-prompt/get-difficulty {:interface :tui} "X" [:easy :medium :hard])))
      (should-have-invoked :display-options)))
  )
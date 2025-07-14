(ns tic-tac-toe.tui.console-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.in-progress]
            [tic-tac-toe.tui.console :as sut]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(describe "console"
  (with-stubs)
  (before (reset! spec-helper/mock-db nil))

  (it "prints a welcome message"
    (should= "Welcome to tic-tac-toe!\n"
             (with-out-str (sut/welcome-message))))

  (it "prints the board state"
    (should= "\n  1 |  2 |  3 \n----|----|----\n  4 |  5 |  6 \n----|----|----\n  7 |  8 |  9 \n\n"
             (with-out-str (sut/display-board helper/empty-board)))

    (should= "\n  1 |  2 |  3 \n----|----|----\n  4 |  X |  6 \n----|----|----\n  7 |  8 |  9 \n\n"
             (with-out-str (sut/display-board helper/center-x-board))))

  (it "prints the board state for a 4x board"
    (should= "\n  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  6 |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n\n"
             (with-out-str (sut/display-board helper/empty-4-board)))

    (should= "\n  1 |  2 |  3 |  4 \n----|----|----|----\n  5 |  X |  7 |  8 \n----|----|----|----\n  9 | 10 | 11 | 12 \n----|----|----|----\n 13 | 14 | 15 | 16 \n\n"
             (with-out-str (sut/display-board helper/first-X-4-board))))

  (it "prints the board state for a 3d board"
    (should= "\n\nLayer 1:\n  1 |  2 |  3 \n----|----|----\n  4 |  5 |  6 \n----|----|----\n  7 |  8 |  9 \n\nLayer 2:\n 10 | 11 | 12 \n----|----|----\n 13 | 14 | 15 \n----|----|----\n 16 | 17 | 18 \n\nLayer 3:\n 19 | 20 | 21 \n----|----|----\n 22 | 23 | 24 \n----|----|----\n 25 | 26 | 27 \n\n\n"
             (with-out-str (sut/display-board helper/empty-3d-board)))

    (should= "\n\nLayer 1:\n  1 |  2 |  3 \n----|----|----\n  4 |  5 |  6 \n----|----|----\n  7 |  X |  9 \n\nLayer 2:\n 10 | 11 | 12 \n----|----|----\n 13 | 14 | 15 \n----|----|----\n 16 | 17 | 18 \n\nLayer 3:\n 19 | 20 | 21 \n----|----|----\n 22 | 23 | 24 \n----|----|----\n 25 | 26 | 27 \n\n\n"
             (with-out-str (sut/display-board helper/first-x-3d-board))))

  (it "prints the number prompt"
    (should= "Please enter the number for the space you'd like to take\n"
             (with-out-str (sut/print-number-prompt))))

  (it "gets input from the user until a valid entry is provided"
    (with-redefs [sut/print-number-prompt (stub :print-prompt)
                  sut/announce-player     (stub :print-dup)]
      (should= 6 (with-in-str "c\n26\n6\n1\n" (sut/get-next-play
                                                (helper/state-create {:interface :tui :board [["X" 2 "O"] ["X" "O" 6] ["O" "X" "O"]]})
                                                [2 6])))))

  (it "notifies a player that a saved game was found"
    (should= "A saved game was found, would you like to resume it? (y/n)\n"
             (with-out-str (sut/save-found-prompt))))

  (it "returns if a user wants to load a saved game or not"
    (with-redefs [sut/save-found-prompt (stub :print-dup)]
      (should= true (with-in-str "y\n" (sut/yes-or-no?)))
      (should= false (with-in-str "n\n" (sut/yes-or-no?)))))

  (it "notifies the player that a play wasn't valid"
    (should= "That isn't a valid play, please try again\n"
             (with-out-str (sut/invalid-selection))))

  (it "notifies the player that the game is a draw"
    (should= "It's a draw! Good game!\n" (with-out-str (sut/announce-draw))))

  (it "deletes a save when the game ends in a draw"
    (with-redefs [core/get-selection (stub :get-selection {:return false})]
      (let [saved-state  (core/save-game (helper/state-create
                                           {:interface           :tui
                                            :save                :mock
                                            :status              :tie
                                            :board               [["X" "X" "X"]]
                                            :active-player-index 0}))
            ending-state (core/update-state saved-state)]
        (should= (assoc saved-state :status :game-over) ending-state)
        (should= {:interface :fake :save :mock} (with-in-str "n\n" (core/load-game {:interface :fake :save :mock}))))))

  (it "deletes a save when the game ends in a win"
    (with-redefs [core/get-selection (stub :get-selection {:return false})]
      (let [saved-state  (core/save-game (helper/state-create {:save                :mock :status :winner :interface :tui :board [["X" "X" "X"]]
                                                               :active-player-index 0}))
            ending-state (core/update-state saved-state)]
        (should= (assoc saved-state :status :game-over) ending-state)
        (should= {:interface :fake :save :mock} (core/load-game {:interface :fake :save :mock})))))

  (it "announces the winner of a game"
    (should= "X wins! Good game!\n" (with-out-str (sut/announce-winner "X"))))

  (it "displays the options for players to choose from"
    (should= "Who will play  X ?\nhuman\ncomputer\n"
             (with-out-str (sut/display-play-type-options "X" [:human :computer]))))


  (it "asks the player if they want to play again"
    (should= "Would you like to play again? (y/n)\n"
             (with-out-str (sut/play-again-prompt))))

  (it "validates a user's selection to play again or not"
    (should= true (sut/validate-yes-no-entry "y"))
    (should= true (sut/validate-yes-no-entry "yes"))
    (should= true (sut/validate-yes-no-entry "no"))
    (should= true (sut/validate-yes-no-entry "n"))
    (should= false (sut/validate-yes-no-entry "1"))
    (should= false (sut/validate-yes-no-entry "g"))
    (should= false (sut/validate-yes-no-entry "ned"))
    (should= false (sut/validate-yes-no-entry "november"))
    (should= false (sut/validate-yes-no-entry "yesterday"))
    (should= false (sut/validate-yes-no-entry "Y4")))

  (it "returns true if the user indicates yes, false if now"
    (with-redefs [sut/play-again-prompt (stub :prompt)]
      (should= true (with-in-str "Y\n" (sut/yes-or-no?)))
      (should= true (with-in-str "Yes\n" (sut/yes-or-no?)))
      (should= true (with-in-str "y\n" (sut/yes-or-no?)))
      (should= true (with-in-str "yes\n" (sut/yes-or-no?)))
      (should= true (with-in-str "none\nyes\n" (sut/yes-or-no?)))
      (should= true (with-in-str "yesterday\nyes\n" (sut/yes-or-no?)))
      (should= false (with-in-str "N\n" (sut/yes-or-no?)))
      (should= false (with-in-str "No\n" (sut/yes-or-no?)))
      (should= false (with-in-str "yesterday\nno\n" (sut/yes-or-no?)))
      (should= false (with-in-str "yell\nn\n" (sut/yes-or-no?)))
      (should= false (with-in-str "noise\nn\n" (sut/yes-or-no?)))
      (should= false (with-in-str "None\nn\n" (sut/yes-or-no?)))
      (should= false (with-in-str "nonsense\nn\n" (sut/yes-or-no?)))))

  (it "prints the board-size options"
    (should= "What size board do you want to play on?\n1) :3x3\n2) :4x4\n3) :3x3x3\n"
             (with-out-str (sut/board-size-prompt {:3x3 3, :4x4 4, :3x3x3 [3 3 3]}))))

  (it "allows the player to select a board size of 3 or 4 or 3x3x3"
    (with-redefs [sut/board-size-prompt (stub :size-prompt)]
      (should= 3 (with-in-str "1\n" (sut/get-board-size {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})))
      (should= 4 (with-in-str "2\n" (sut/get-board-size {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})))
      (should= [3 3 3] (with-in-str "6\ngesf\nhello\n3\n4\n" (sut/get-board-size {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})))))

  (it "prints the difficulty options"
    (should= "What difficulty setting should X use?\neasy\nmedium\nhard\n"
             (with-out-str (sut/display-difficulty-options "X" [:easy :medium :hard]))))

  (it "prints the exit-message"
    (should= "Thanks for playing!\n" (with-out-str (sut/exit-message))))

  (context "draw-state"
    (redefs-around [sut/display-board (stub :display-board)
                    sut/display-difficulty-options (stub :difficulty-options)
                    sut/display-play-type-options (stub :play-type-options)
                    sut/board-size-prompt (stub :board-size)
                    sut/exit-message (stub :exit-message)
                    sut/announce-draw (stub :announce-draw)
                    sut/announce-winner (stub :announce-winner)
                    sut/announce-player (stub :announce-player)
                    sut/play-again-prompt (stub :play-again)
                    sut/save-found-prompt (stub :save-found)
                    sut/welcome-message (stub :welcome-message)
                    println (stub :print-dup)])


    (it "draws the state for game-over"
      (let [state       (helper/state-create {:interface :tui :status :game-over})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :exit-message)))

    (it "draws the state for a tie game"
      (let [state       (helper/state-create {:interface :tui :status :tie :board helper/first-X-4-board})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :display-board)
        (should-have-invoked :announce-draw)
        (should-have-invoked :play-again)))

    (it "draws the state for a won game"
      (let [state       (helper/state-create {:interface :tui :status :winner :board helper/first-X-4-board})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :display-board)
        (should-have-invoked :announce-winner)
        (should-have-invoked :play-again)))

    (it "draws the state for an in-progress game"
      (let [state       (helper/state-create {:interface :tui :status :in-progress :board helper/first-X-4-board})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :display-board)
        (should-have-invoked :announce-player)))

    (it "draws the state for selecting a board"
      (let [state       (helper/state-create {:interface :tui :status :select-board})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :board-size)))

    (it "draws the state for configuring o difficulty"
      (let [state       (helper/state-create {:interface :tui :status :config-o-difficulty})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :difficulty-options {:with ["O" core/difficulty-options]})))

    (it "draws the state for configuring o type"
      (let [state       (helper/state-create {:interface :tui :status :config-o-type})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :play-type-options {:with ["O" core/player-options]})))

    (it "draws the state for configuring x difficulty"
      (let [state       (helper/state-create {:interface :tui :status :config-x-difficulty})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :difficulty-options {:with ["X" core/difficulty-options]})))

    (it "draws the state for configuring x type"
      (let [state       (helper/state-create {:interface :tui :status :config-x-type})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :play-type-options {:with ["X" core/player-options]})))

    (it "draws the state for a found save"
      (let [state       (helper/state-create {:interface :tui :status :found-save})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :save-found)))

    (it "draws the state for welcome"
      (let [state       (helper/state-create {:interface :tui :status :welcome})
            final-state (core/draw-state state)]
        (should= state final-state)
        (should-have-invoked :welcome-message)))
    )
  )
(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.game-spec :as test-game]
            [tic-tac-toe.console :as console]))



(describe "main"
  (with-stubs)

  (it "initializes an empty board, and starting player O"
    (should= test-game/state-initial (initialize-state {:type-x :human :type-o :human :difficulty-x nil
                                                        :difficulty-o nil :board-size 3 :interface :tui})))

  (it "starts a new game"
    (with-redefs [game/start                        (stub :game/start)
                  console/welcome                   (stub :console/welcome)
                  console/display-play-type-options (stub :print-dup-play-type)
                  console/play-again-prompt         (stub :play-again?)
                  console/board-size-prompt         (stub :board-size-prompt {:return 3})]
      (with-in-str "human\nhuman\n3\nno\n" (-main "tui"))
      (should-have-invoked :console/welcome)
      (should-have-invoked :game/start)))

  (it "allows the user to choose to play again"
    (with-redefs [game/start                         (stub :game/start)
                  console/welcome                    (stub :console/welcome)
                  console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)
                  console/play-again-prompt          (stub :play-again?)
                  console/board-size-prompt          (stub :board-size-prompt)]
      (with-in-str "computer\neasy\ncomputer\neasy\n4\ny\ncomputer\neasy\ncomputer\nmedium\n3\nn\n" (-main "tui"))
      (should-have-invoked :console/welcome {:times 2})
      (should-have-invoked :print-dup-play-type {:times 4})
      (should-have-invoked :print-dup-difficulty {:times 4})
      (should-have-invoked :play-again? {:times 2})))

  (it "assigns a difficulty of nil if player type is human"
    (with-redefs [console/display-play-type-options (stub :print-dup-play-type)]
      (should= {:play-type :human :difficulty nil}
               (with-in-str "human\n" (get-player-config "X")))))

  (it "obtains a difficulty level if the play-type chosen is computer"
    (with-redefs [console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)]
      (should= {:play-type :computer :difficulty :hard}
               (with-in-str "1\njumbo\ncomputer\n1\nhangry\nhard\n" (get-player-config "X")))
      (should-have-invoked :print-dup-difficulty)
      (should-have-invoked :print-dup-play-type)))

  (it "initializes a new game with computer player and human player"
    (with-redefs [game/start                         (stub :game/start)
                  console/welcome                    (stub :console/welcome)
                  console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)
                  console/board-size-prompt          (stub :board-size-prompt)
                  console/play-again-prompt          (stub :play-again?)
                  game/start                         (stub :start)]
      (with-in-str "human\ncomputer\nmedium\n4\nn\n" (-main "tui"))
      (should-have-invoked :start {:with [{:interface :tui
                                           :board               [[1 2 3 4]
                                                                 [5 6 7 8]
                                                                 [9 10 11 12]
                                                                 [13 14 15 16]]
                                           :active-player-index 1
                                           :status              "in-progress"
                                           :players             [{:character "X" :play-type :human :difficulty nil}
                                                                 {:character "O" :play-type :computer :difficulty :medium}]}]})))

  (it "uses the console interface if launched with tui"
    (with-redefs [start-game                (stub :launch-cli)
                  console/play-again-prompt (stub :play-again?)
                  game/start                (stub :start)]
      (with-in-str "human\ncomputer\nmedium\n3\nn\n" (-main "tui"))
      (should-have-invoked :launch-cli {:with [{:interface :tui}]})))

  (it "uses the quil interface if launched with gui"
    (with-redefs [start-game               (stub :launch-quil)
                  console/play-again-prompt (stub :play-again?)
                  game/start                (stub :start)]
      (-main "gui")
      (should-have-invoked :launch-quil {:with [{:interface :gui}]})
      ))
  )
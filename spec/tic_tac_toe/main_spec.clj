(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.main :refer :all]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.tui.game-spec :as test-game]
            [tic-tac-toe.core :as user-prompt]
            [tic-tac-toe.tui.console :as console]))



(describe "main"
  (with-stubs)

  (it "starts a new game"
    (with-redefs [core/start-game                   (stub :core/start)
                  console/welcome-message           (stub :console/welcome)
                  console/display-play-type-options (stub :print-dup-play-type)
                  console/play-again-prompt         (stub :play-again?)
                  console/board-size-prompt         (stub :board-size-prompt {:return 3})]
      (with-in-str "human\nhuman\n3\nno\n" (-main "tui"))
      (should-have-invoked :core/start)))

  (it "allows the user to choose to play again"
    (with-redefs [game/play                          (stub :game/start)
                  console/welcome-message            (stub :console/welcome)
                  console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)
                  console/play-again-prompt          (stub :play-again?)
                  console/board-size-prompt          (stub :board-size-prompt)
                  core/save-game                     (stub :save-dup)]
      (with-in-str "computer\neasy\ncomputer\neasy\n4\ny\ncomputer\neasy\ncomputer\nmedium\n3\nn\n" (-main "tui"))
      (should-have-invoked :console/welcome {:times 2})
      (should-have-invoked :print-dup-play-type {:times 4})
      (should-have-invoked :print-dup-difficulty {:times 4})
      (should-have-invoked :play-again? {:times 2})))

  (it "initializes a new game with computer player and human player"
    (with-redefs [console/welcome-message            (stub :console/welcome)
                  console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)
                  console/board-size-prompt          (stub :board-size-prompt)
                  console/play-again-prompt          (stub :play-again?)
                  core/update-state                  (stub :update-loop)
                  core/save-game                     (stub :save-dup)]
      (with-in-str "human\ncomputer\nmedium\n4\nn\n" (-main "tui"))
      (should-have-invoked :update-loop {:with [{:interface           :tui
                                                 :board               [[1 2 3 4]
                                                                       [5 6 7 8]
                                                                       [9 10 11 12]
                                                                       [13 14 15 16]]
                                                 :active-player-index 1
                                                 :status              :in-progress
                                                 :players             [{:character "X" :play-type :human :difficulty nil}
                                                                       {:character "O" :play-type :computer :difficulty :medium}]}]})))

  (it "uses the console interface if launched with tui"
    (with-redefs [core/start-game           (stub :launch-cli)
                  console/play-again-prompt (stub :play-again?)
                  game/play                 (stub :start)]
      (with-in-str "human\ncomputer\nmedium\n3\nn\n" (-main "tui"))
      (should-have-invoked :launch-cli {:with [{:status :config :interface :tui}]})))

  (it "uses the quil interface if launched with gui"
    (with-redefs [core/start-game           (stub :launch-quil)
                  console/play-again-prompt (stub :play-again?)
                  game/play                 (stub :start)]
      (-main "gui")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui}]})))
  )
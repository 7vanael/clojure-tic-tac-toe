(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.main :refer :all]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.persistence :as persistence]))



(describe "main"
  (with-stubs)

  (it "starts a new game"
    (with-redefs [core/start-game                   (stub :core/start)
                  console/welcome-message           (stub :console/welcome)
                  console/display-play-type-options (stub :print-dup-play-type)
                  console/play-again-prompt         (stub :play-again?)
                  console/board-size-prompt         (stub :board-size-prompt {:return 3})]
      (with-in-str "human\nhuman\n1\nno\n" (-main "tui"))
      (should-have-invoked :core/start)))

  (it "initializes a new game with computer player and human player"
    (with-redefs [console/welcome-message            (stub :console/welcome)
                  console/display-play-type-options  (stub :print-dup-play-type)
                  console/display-difficulty-options (stub :print-dup-difficulty)
                  console/board-size-prompt          (stub :board-size-prompt)
                  console/play-again-prompt          (stub :play-again?)
                  core/update-state                  (stub :update-loop)
                  persistence/save-game              (stub :save-dup)
                  persistence/load-game              (stub :load {:return nil})]
      (with-in-str "human\ncomputer\nmedium\n2\nn\n" (-main "tui"))
      (should-have-invoked :update-loop {:with [{:interface           :tui
                                                 :board               [[1 2 3 4]
                                                                       [5 6 7 8]
                                                                       [9 10 11 12]
                                                                       [13 14 15 16]]
                                                 :active-player-index 0
                                                 :status              :in-progress
                                                 :players             [{:character "X" :play-type :human :difficulty nil}
                                                                       {:character "O" :play-type :computer :difficulty :medium}]}]})))

  (it "uses the console interface if launched with tui"
    (with-redefs [core/start-game           (stub :launch-cli)
                  console/play-again-prompt (stub :play-again?)
                  ]
      (with-in-str "human\ncomputer\nmedium\n1\nn\n" (-main "tui"))
      (should-have-invoked :launch-cli {:with [{:status :config :interface :tui}]})))

  (it "uses the quil interface if launched with gui"
    (with-redefs [core/start-game           (stub :launch-quil)
                  console/play-again-prompt (stub :play-again?)
                  ]
      (-main "gui")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui}]})))
  )
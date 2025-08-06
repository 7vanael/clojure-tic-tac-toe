(ns tic-tac-toe.draw_spec
  (:require-macros [speclj.core :refer [describe it should= should before context should-contain should-be-nil with-stubs stub should-have-invoked should-not-have-invoked]]
                   [c3kit.wire.spec-helperc :refer [should-select should-not-select]])
  (:require [reagent.core :as r]
            [c3kit.wire.spec-helper :as wire]
            [speclj.core]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.wrappers :as w]
            [tic-tac-toe.main :as sut]))


(describe "draw static"
  (with-stubs)
  (wire/with-root-dom)

  (before (wire/render [sut/game-component]))

  (context "welcome state"

    (it "renders welcome"
      (should-select ".tic-tac-toe-app")
      (should-select ".welcome")
      (should-select ".action-button"))

    (it "progresses from welcome to config-x-type on a click on start button"
      (wire/click! ".action-button")
      (should-select ".config-x-type")
      (should-select ".tic-tac-toe-app")
      )
    )

  (context "config-x-type"
    (before (reset! w/state {:interface :static :status :config-x-type :save :ratom
                               :players   [{:character "X" :play-type nil :difficulty nil}
                                           {:character "O" :play-type nil :difficulty nil}]})
            (wire/render [core/draw-state]))
    (it "renders config-x"
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer"))

    (it "sets x-type to human if human button clicked"
      (wire/click! ".human")
      (should= :human (get-in @w/state [:players 0 :play-type]))
      (should= :config-o-type (:status @w/state))
      (should-select ".config-o-type"))

    (it "sets x-type to computer if computer button clicked"
      (wire/click! ".computer")
      (should= :computer (get-in @w/state [:players 0 :play-type]))
      (should= :config-x-difficulty (:status @w/state))
      (should-select ".config-x-difficulty"))
    )

  (context "config-o-type"
    (before
      (reset! w/state {:interface :static :status :config-o-type :save :ratom
                         :players   [{:character "X" :play-type :human :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (wire/flush))
    (it "renders config-o-type"
      (should-select ".config-o-type")
      (should-select "div.config-o-type button.action-button")
      (should-select ".human")
      (should-select ".computer"))

    (it "sets o-type to human if human button clicked"
      (should-select ".human")
      (should-select ".o-type")
      (wire/click! ".human")
      (should= :human (get-in @w/state [:players 1 :play-type])))

    (it "sets x-type to computer if computer button clicked"
      (wire/click! ".computer")
      (should= :computer (get-in @w/state [:players 1 :play-type]))
      (should= :config-o-difficulty (:status @w/state))
      (should-select ".config-o-difficulty"))
    )

  (context "config-x-difficulty"
    (before (reset! w/state {:interface :static :status :config-x-difficulty :save :ratom
                               :players   [{:character "X" :play-type :computer :difficulty nil}
                                           {:character "O" :play-type nil :difficulty nil}]})
            (wire/flush))
    (it "renders config-x-difficulty"
      (should-select ".config-x-difficulty")
      (should-select "div.config-x-difficulty button.action-button")
      (should-select ".easy")
      (should-select ".medium")
      (should-select ".hard"))

    (it "sets x-difficulty to easy if easy button clicked"
      (wire/click! ".easy")
      (should= :easy (get-in @w/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @w/state))
      (should-select ".config-o-type"))

    (it "sets x-difficulty to medium if medium button clicked"
      (wire/click! ".medium")
      (should= :medium (get-in @w/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @w/state))
      (should-select ".config-o-type"))

    (it "sets x-difficulty to hard if hard button clicked"
      (wire/click! ".hard")
      (should= :hard (get-in @w/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @w/state))
      (should-select ".config-o-type"))
    )

  (context "config-o-difficulty"
    (before (reset! w/state {:interface :static :status :config-o-difficulty :save :ratom
                               :players   [{:character "X" :play-type :human :difficulty nil}
                                           {:character "O" :play-type :computer :difficulty nil}]})
            (wire/flush))
    (it "renders config-o-difficulty"
      (should-select ".config-o-difficulty")
      (should-select "div.config-o-difficulty button.action-button")
      (should-select ".easy")
      (should-select ".medium")
      (should-select ".hard"))

    (it "sets o-difficulty to easy if easy button clicked"
      (wire/click! ".easy")
      (should= :easy (get-in @w/state [:players 1 :difficulty]))
      (should= :select-board (:status @w/state))
      (should-select ".select-board"))

    (it "sets o-difficulty to medium if medium button clicked"
      (wire/click! ".medium")
      (should= :medium (get-in @w/state [:players 1 :difficulty]))
      (should= :select-board (:status @w/state))
      (should-select ".select-board"))

    (it "sets o-difficulty to hard if hard button clicked"
      (wire/click! ".hard")
      (should= :hard (get-in @w/state [:players 1 :difficulty]))
      (should= :select-board (:status @w/state))
      (should-select ".select-board"))
    )

  (context "select-board"
    (before (reset! w/state {:interface :static :status :select-board :save :ratom :active-player-index 0
                               :players   [{:character "X" :play-type :human :difficulty nil}
                                           {:character "O" :play-type :human :difficulty nil}]})
            (wire/render [sut/game-component]))
    (it "renders select-board"
      (should-select ".select-board")
      (should-select "div.select-board button.action-button")
      (should-select ".board-3x3")
      (should-select ".board-4x4"))

    (it "sets board to 3x3 if 3x3 button clicked"
      (wire/click! ".board-3x3")
      (should= [[1 2 3] [4 5 6] [7 8 9]] (:board @w/state))
      (should= :in-progress (:status @w/state))
      (should-select ".in-progress"))

    (it "sets board to 4x4 if 4x4 button clicked"
      (wire/click! ".board-4x4")
      (should= [[1 2 3 4] [5 6 7 8] [9 10 11 12] [13 14 15 16]] (:board @w/state))
      (should= :in-progress (:status @w/state))
      (should-select ".in-progress"))
    )

  (context "select-board when computer goes first:"
    (before (reset! w/state {:interface :static :status :select-board :save :ratom :active-player-index 0
                               :players   [{:character "X" :play-type :computer :difficulty :hard}
                                           {:character "O" :play-type :human :difficulty nil}]})
            (wire/render [sut/game-component]))

    (it "starts the computer turn if computer is first"
      (with-redefs [js/setTimeout (stub :timeout)]
                   (wire/click! ".board-4x4")
                   (should= :in-progress @w/status-cursor)
                   (should-have-invoked :timeout)))
    )

  (context "in-progress- initial-state"
    (before (reset! w/state {:interface           :static :status :in-progress :save :ratom
                               :active-player-index 0 :board [[1 2 3] [4 5 6] [7 8 9]]
                               :players             [{:character "X" :play-type :human :difficulty nil}
                                                     {:character "O" :play-type :human :difficulty nil}]})
            (wire/render [sut/game-component]))

    (it "renders in-progress"
      (should-select ".in-progress")
      (should-select "table")
      (should-select "tbody")
      (should= 3 (wire/count-all "tr"))
      (should= 9 (wire/count-all "td"))
      (should= 9 (wire/count-all "td.empty"))
      (should= 9 (wire/count-all "button.move-button"))
      (should-select "button.move-button"))

    (it "displays current player's turn"
      (should-select "h2.current-player")
      (should-contain "Player X's turn" (wire/text "h2.current-player")))

    (it "allows the human player to click an active cell, then updates the board"
      (wire/click! "#cell-5")
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @w/state))
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= 8 (wire/count-all "td.empty"))
      (should= "X" (wire/text "#cell-5"))
      (should= 1 (:active-player-index @w/state)))

    (it "doesn't allow the same cell to be clicked twice"
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @w/state))
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @w/state)))

    (it "does allow the next player to click to claim a different square"
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @w/state))
      (wire/click! "#cell-3")
      (should-contain "Player X's turn" (wire/text "h2.current-player"))
      (should= [[1 2 "O"] [4 "X" 6] [7 8 9]] (:board @w/state)))

    )

  (context "in-progress to tie"
    (before (reset! w/state {:interface           :static :status :in-progress :save :ratom
                               :active-player-index 0 :board [["X" "X" "O"] ["O" "O" "X"] ["X" 8 "O"]]
                               :players             [{:character "X" :play-type :human :difficulty nil}
                                                     {:character "O" :play-type :computer :difficulty :hard}]})
            (wire/render [sut/game-component]))

    (it "ends the game in a tie"
      (wire/click! "#cell-8")
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @w/state))
      (should= :tie (:status @w/state))
      (should= 0 (:active-player-index @w/state))
      (should-select ".game-over")
      )
    )

  (context "in-progress to winner"
    (before (reset! w/state {:interface           :static :status :in-progress :save :ratom
                               :active-player-index 0 :board [["X" "X" 3] ["O" "O" 6] [7 8 9]]
                               :players             [{:character "X" :play-type :human :difficulty nil}
                                                     {:character "O" :play-type :computer :difficulty :hard}]})
            (wire/render [sut/game-component]))

    (it "ends the game in a tie"
      (wire/click! "#cell-3")
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @w/state))
      (should= :winner (:status @w/state))
      (should= 0 (:active-player-index @w/state))
      (should-select ".game-over")
      )
    )

  (context "winner"
    (before (reset! w/state {:interface           :static :status :winner :save :ratom
                               :active-player-index 0 :board [["X" "X" "X"] ["O" "O" 6] [7 8 9]]
                               :players             [{:character "X" :play-type :human :difficulty nil}
                                                     {:character "O" :play-type :computer :difficulty :hard}]})
            (wire/render [sut/game-component]))

    (it "displays game-over state with disabled buttons"
      (should-select ".game-over")
      (should-select "#new-game")
      (should-contain "Player X wins! Good game" (wire/text "h2.game-over"))
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @w/state))
      (should= "Play Again?" (wire/text "#new-game"))
      (should= 9 (wire/count-all "button.move-button:disabled"))
      (wire/click! "#cell-6")
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @w/state))
      (should= "Play Again?" (wire/text "#new-game")))

    (it "lets the player play again"
      (wire/click! "#new-game")
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer")
      (should-be-nil (get-in @w/state [:players 0 :play-type]))
      (should-be-nil (get-in @w/state [:players 1 :play-type]))
      (should-be-nil (:board @w/state))
      )
    )

  (context "tie"
    (before (reset! w/state {:interface           :static :status :tie :save :ratom
                               :active-player-index 0 :board [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]]
                               :players             [{:character "X" :play-type :human :difficulty nil}
                                                     {:character "O" :play-type :computer :difficulty :hard}]})
            (wire/render [sut/game-component]))

    (it "displays game-over state"
      (should-select ".game-over")
      (should-select "#new-game")
      (should-contain "It's a tie! Good game" (wire/text "h2.game-over"))
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @w/state))
      (should= "Play Again?" (wire/text "#new-game"))
      (should= 9 (wire/count-all "button.move-button:disabled"))
      (wire/click! "#cell-6")
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @w/state))
      (should= "Play Again?" (wire/text "#new-game")))

    (it "lets the player play again"
      (wire/click! "#new-game")
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer")
      (should-be-nil (get-in @w/state [:players 0 :play-type]))
      (should-be-nil (get-in @w/state [:players 1 :play-type]))
      (should-be-nil (:board @w/state)))
    )

  )
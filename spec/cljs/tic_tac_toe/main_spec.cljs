(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should= should before context focus-context should-contain should-be-nil]]
                   [c3kit.wire.spec-helperc :refer [should-select should-not-select]])
  (:require [reagent.core :as r]
            [c3kit.wire.spec-helper :as wire]
            [speclj.core]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.main :as sut]))



(describe "main"

  (wire/with-root-dom)
  ;before and after functions that clear out all mounted components
  (before (wire/render [sut/game-component] (wire/select "#root")))
  ;I suspect this is the source of the warning ReactDOM rendering. It uses dom/render
  ; instead of reagent.dom.client/render

  (context "welcome state"
    (before (reset! sut/state {:interface :static :status :welcome :save :ratom}))
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
    (before (do
              (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                                 :players   [{:character "X" :play-type nil :difficulty nil}
                                             {:character "O" :play-type nil :difficulty nil}]})
              (wire/render [sut/game-component])))
    (it "renders config-x"
      #_(reset! sut/state {:interface :static :status :config-x-type :save :ratom
                           :players   [{:character "X" :play-type nil :difficulty nil}
                                       {:character "O" :play-type nil :difficulty nil}]})
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer"))

    (it "sets x-type to human if human button clicked"
      (wire/click! ".human")
      (should= :human (get-in @sut/state [:players 0 :play-type]))
      (should= :config-o-type (:status @sut/state))
      (should-select ".config-o-type"))

    (it "sets x-type to computer if computer button clicked"
      (wire/click! ".computer")
      (should= :computer (get-in @sut/state [:players 0 :play-type]))
      (should= :config-x-difficulty (:status @sut/state))
      (should-select ".config-x-difficulty"))
    )

  (context "config-o-type"
    (before (do
              (reset! sut/state {:interface :static :status :config-o-type :save :ratom
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type nil :difficulty nil}]})
              (wire/render [sut/game-component])))
    (it "renders config-o-type"
      (should-select ".config-o-type")
      (should-select "div.config-o-type button.action-button")
      (should-select ".human")
      (should-select ".computer"))

    (it "sets o-type to human if human button clicked"
      (should-select ".human")
      (should-select ".o-type")
      (wire/click! ".human")
      (should= :human (get-in @sut/state [:players 1 :play-type])))

    (it "sets x-type to computer if computer button clicked"
      (wire/click! ".computer")
      (should= :computer (get-in @sut/state [:players 1 :play-type]))
      (should= :config-o-difficulty (:status @sut/state))
      (should-select ".config-o-difficulty"))
    )

  (context "config-x-difficulty"
    (before (do
              (reset! sut/state {:interface :static :status :config-x-difficulty :save :ratom
                                 :players   [{:character "X" :play-type :computer :difficulty nil}
                                             {:character "O" :play-type nil :difficulty nil}]})
              (wire/render [sut/game-component])))
    (it "renders config-x-difficulty"
      (should-select ".config-x-difficulty")
      (should-select "div.config-x-difficulty button.action-button")
      (should-select ".easy")
      (should-select ".medium")
      (should-select ".hard"))

    (it "sets x-difficulty to easy if easy button clicked"
      (wire/click! ".easy")
      (should= :easy (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state))
      (should-select ".config-o-type"))

    (it "sets x-difficulty to medium if medium button clicked"
      (wire/click! ".medium")
      (should= :medium (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state))
      (should-select ".config-o-type"))

    (it "sets x-difficulty to hard if hard button clicked"
      (wire/click! ".hard")
      (should= :hard (get-in @sut/state [:players 0 :difficulty]))
      (should= :config-o-type (:status @sut/state))
      (should-select ".config-o-type"))
    )

  (context "config-o-difficulty"
    (before (do
              (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type :computer :difficulty nil}]})
              (wire/render [sut/game-component])))
    (it "renders config-o-difficulty"
      (should-select ".config-o-difficulty")
      (should-select "div.config-o-difficulty button.action-button")
      (should-select ".easy")
      (should-select ".medium")
      (should-select ".hard"))

    (it "sets o-difficulty to easy if easy button clicked"
      (wire/click! ".easy")
      (should= :easy (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state))
      (should-select ".select-board"))

    (it "sets o-difficulty to medium if medium button clicked"
      (wire/click! ".medium")
      (should= :medium (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state))
      (should-select ".select-board"))

    (it "sets o-difficulty to hard if hard button clicked"
      (wire/click! ".hard")
      (should= :hard (get-in @sut/state [:players 1 :difficulty]))
      (should= :select-board (:status @sut/state))
      (should-select ".select-board"))
    )

  (context "select-board"
    (before (do
              (reset! sut/state {:interface :static :status :select-board :save :ratom :active-player-index 0
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type :human :difficulty nil}]})
              (wire/render [sut/game-component])))
    (it "renders select-board"
      (should-select ".select-board")
      (should-select "div.select-board button.action-button")
      (should-select ".board-3x3")
      (should-select ".board-4x4"))

    (it "sets board to 3x3 if 3x3 button clicked"
      (wire/click! ".board-3x3")
      (should= [[1 2 3] [4 5 6] [7 8 9]] (:board @sut/state))
      (should= :in-progress (:status @sut/state))
      (should-select ".in-progress"))

    (it "sets board to 4x4 if 4x4 button clicked"
      (wire/click! ".board-4x4")
      (should= [[1 2 3 4] [5 6 7 8] [9 10 11 12] [13 14 15 16]] (:board @sut/state))
      (should= :in-progress (:status @sut/state))
      (should-select ".in-progress"))
    )

  (context "select-board when computer goes first:"
    (before (do
              (reset! sut/state {:interface :static :status :select-board :save :ratom :active-player-index 0
                                 :players   [{:character "X" :play-type :computer :difficulty :hard}
                                             {:character "O" :play-type :human :difficulty nil}]})
              (wire/render [sut/game-component])))
;I don't think this test runs with the async. The print lines don't print. How to make?
;
    #_(it "starts the computer turn if computer is first, returns state when it's the human's turn"
      (wire/click! ".board-4x4")
      (should= :in-progress @sut/status-cursor)
      (js/setTimeout #(do
                        (should= 1 (:active-player-index @sut/state))

                        (let [board   (:board @sut/state)
                              x-count (count (filter #{"X"} (flatten board)))
                              _       (prn "x-count:" x-count)]

                          (should= 1 x-count))
                        (should= 15 (wire/count-all "td.empty")))
                     300)
      )
    )
  (context "in-progress- initial-state"
    (before (do (reset! sut/state {:interface           :static :status :in-progress :save :ratom
                                   :active-player-index 0 :board [[1 2 3] [4 5 6] [7 8 9]]
                                   :players             [{:character "X" :play-type :human :difficulty nil}
                                                         {:character "O" :play-type :human :difficulty nil}]})
                (wire/render [sut/game-component])))

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
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @sut/state))
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= 8 (wire/count-all "td.empty"))
      (should= "X" (wire/text "#cell-5"))
      (should= 1 (:active-player-index @sut/state)))

    (it "doesn't allow the same cell to be clicked twice"
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @sut/state))
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @sut/state)))

    (it "does allow the next player to click to claim a different square"
      (wire/click! "#cell-5")
      (should-contain "Player O's turn" (wire/text "h2.current-player"))
      (should= [[1 2 3] [4 "X" 6] [7 8 9]] (:board @sut/state))
      (wire/click! "#cell-3")
      (should-contain "Player X's turn" (wire/text "h2.current-player"))
      (should= [[1 2 "O"] [4 "X" 6] [7 8 9]] (:board @sut/state)))

    )

  (context "in-progress to tie"
    (before (do (reset! sut/state {:interface           :static :status :in-progress :save :ratom
                                   :active-player-index 0 :board [["X" "X" "O"] ["O" "O" "X"] ["X" 8 "O"]]
                                   :players             [{:character "X" :play-type :human :difficulty nil}
                                                         {:character "O" :play-type :computer :difficulty :hard}]})
                (wire/render [sut/game-component])))

    (it "ends the game in a tie"
      (wire/click! "#cell-8")
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @sut/state))
      (should= :tie (:status @sut/state))
      (should= 0 (:active-player-index @sut/state))
      (should-select ".game-over")
      )
    )

  (context "in-progress to winner"
    (before (do (reset! sut/state {:interface           :static :status :in-progress :save :ratom
                                   :active-player-index 0 :board [["X" "X" 3] ["O" "O" 6] [7 8 9]]
                                   :players             [{:character "X" :play-type :human :difficulty nil}
                                                         {:character "O" :play-type :computer :difficulty :hard}]})
                (wire/render [sut/game-component])))

    (it "ends the game in a tie"
      (wire/click! "#cell-3")
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @sut/state))
      (should= :winner (:status @sut/state))
      (should= 0 (:active-player-index @sut/state))
      (should-select ".game-over")
      )
    )

  (context "winner"
    (before (do (reset! sut/state {:interface           :static :status :winner :save :ratom
                                   :active-player-index 0 :board [["X" "X" "X"] ["O" "O" 6] [7 8 9]]
                                   :players             [{:character "X" :play-type :human :difficulty nil}
                                                         {:character "O" :play-type :computer :difficulty :hard}]})
                (wire/render [sut/game-component])))

    (it "displays game-over state with disabled buttons"
      (should-select ".game-over")
      (should-select "#new-game")
      (should-contain "Player X wins! Good game" (wire/text "h2.game-over"))
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @sut/state))
      (should= "Play Again?" (wire/text "#new-game"))
      (should= 9 (wire/count-all "button.move-button:disabled"))
      (wire/click! "#cell-6")
      (should= [["X" "X" "X"] ["O" "O" 6] [7 8 9]] (:board @sut/state))
      (should= "Play Again?" (wire/text "#new-game")))

    (it "lets the player play again"
      (wire/click! "#new-game")
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer")
      (should-be-nil (get-in @sut/state [:players 0 :play-type]))
      (should-be-nil (get-in @sut/state [:players 1 :play-type]))
      (should-be-nil (:board @sut/state))
      )

    )

  (context "tie"
    (before (do (reset! sut/state {:interface           :static :status :tie :save :ratom
                                   :active-player-index 0 :board [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]]
                                   :players             [{:character "X" :play-type :human :difficulty nil}
                                                         {:character "O" :play-type :computer :difficulty :hard}]})
                (wire/render [sut/game-component])))

    (it "displays game-over state"
      (should-select ".game-over")
      (should-select "#new-game")
      (should-contain "It's a tie! Good game" (wire/text "h2.game-over"))
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @sut/state))
      (should= "Play Again?" (wire/text "#new-game"))
      (should= 9 (wire/count-all "button.move-button:disabled"))
      (wire/click! "#cell-6")
      (should= [["X" "X" "O"] ["O" "O" "X"] ["X" "X" "O"]] (:board @sut/state))
      (should= "Play Again?" (wire/text "#new-game")))

    (it "lets the player play again"
      (wire/click! "#new-game")
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.action-button")
      (should-select ".human")
      (should-select ".computer")
      (should-be-nil (get-in @sut/state [:players 0 :play-type]))
      (should-be-nil (get-in @sut/state [:players 1 :play-type]))
      (should-be-nil (:board @sut/state)))

    )

  (context "human move"
    (before (do
              (reset! sut/state {:status              :in-progress
                                 :interface           :static
                                 :save                :ratom
                                 :board               [[1 2 3] [4 5 6] [7 8 9]]
                                 :active-player-index 0
                                 :players             [{:character "X" :play-type :human}
                                                       {:character "O" :play-type :computer :difficulty :easy}]})
              (reset! sut/status-cursor :in-progress)
              (wire/render [sut/game-component])))

    (it "human move triggers computer move"
      (wire/click! "#cell-5")
      (should= "X" (get-in @sut/state [:board 1 1]))
      (should= 0 (:active-player-index @sut/state))

      (let [board   (:board @sut/state)
            o-count (count (filter #{"O"} (flatten board)))]
        (should= 1 o-count))
      (should= 7 (wire/count-all "td.empty"))
      (should= 2 (wire/count-all "td.occupied"))
      )

    )
  )
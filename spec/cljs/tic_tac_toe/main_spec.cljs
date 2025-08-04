(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should= should before context should-contain]]
                   [c3kit.wire.spec-helperc :refer [should-select]])
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdomc]
            [c3kit.wire.spec-helper :as wire]
            [speclj.core]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.main :as sut]))



(describe "main"

  (wire/with-root-dom)                                      ;before and after functions that clear out all mounted components
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
    (it "renders config-x"
      (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                         :players   [{:character "X" :play-type nil :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.option")
      (should-select ".human")
      (should-select ".computer"))

    (it "sets x-type to human if human button clicked"
      (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                         :players   [{:character "X" :play-type nil :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (wire/click! ".human")
      (should= :human (get-in @sut/state [:players 0 :play-type]))
      (should= :config-o-type (:status @sut/state))
      (should-select ".config-o-type"))

    (it "sets x-type to computer if computer button clicked"
      (reset! sut/state {:interface :static :status :config-x-type :save :ratom
                         :players   [{:character "X" :play-type nil :difficulty nil}
                                     {:character "O" :play-type nil :difficulty nil}]})
      (wire/render [sut/game-component]) ;had to add this line because the state was updated but
      ; it wasn't being re-rendered for some reason.
      (wire/click! ".computer")
      (should= :computer (get-in @sut/state [:players 0 :play-type]))
      (should= :config-x-difficulty (:status @sut/state))
      (should-select ".config-x-difficulty"))
    )

  #_(context "config-o-type"
      (before (reset! sut/state {:interface :static :status :config-o-type :save :ratom
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type nil :difficulty nil}]}))
      (it "renders config-o-type"
        (should-select ".config-xo-type")
        (should-select "div.config-o-type button.option")
        (should-select ".human")
        (should-select ".computer"))

      (it "sets o-type to human if human button clicked"
        (wire/click! ".human")
        (should= :human (get-in @sut/state [:players 1 :play-type]))
        (should= :select-board (:status @sut/state))
        (should-select ".select-board"))

      (it "sets x-type to computer if computer button clicked"
        (wire/click! ".computer")
        (should= :computer (get-in @sut/state [:players 1 :play-type]))
        (should= :config-o-difficulty (:status @sut/state))
        (should-select ".config-o-difficulty"))
      )

  #_(context "config-x-difficulty"
      (before (reset! sut/state {:interface :static :status :config-x-difficulty :save :ratom
                                 :players   [{:character "X" :play-type :computer :difficulty nil}
                                             {:character "O" :play-type nil :difficulty nil}]}))
      (it "renders config-x-difficulty"
        (should-select ".config-x-difficulty")
        (should-select "div.config-x-difficulty button.option")
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

  #_(context "config-o-difficulty"
      (before (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type :computer :difficulty nil}]}))
      (it "renders config-o-difficulty"
        (should-select ".config-o-difficulty")
        (should-select "div.config-o-difficulty button.option")
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

  #_(context "select-board"
      (before (reset! sut/state {:interface :static :status :config-o-difficulty :save :ratom
                                 :players   [{:character "X" :play-type :human :difficulty nil}
                                             {:character "O" :play-type :computer :difficulty :hard}]}))
      (it "renders select-board"
        (should-select ".select-board")
        (should-select "div.select-board button.option")
        (should-select ".3x3")
        (should-select ".4x4"))

      (it "sets board to 3x3 if 3x3 button clicked"
        (wire/click! ".3x3")
        (should= [[1 2 3] [4 5 6] [7 8 9]] (:board @sut/state))
        (should= :in-progress (:status @sut/state))
        (should-select ".in-progress"))

      (it "sets board to 4x4 if 4x4 button clicked"
        (wire/click! ".4x4")
        (should= [[1 2 3 4] [5 6 7 8] [9 10 11 12] [13 14 15 16]] (:board @sut/state))
        (should= :in-progress (:status @sut/state))
        (should-select ".in-progress"))

      )


  ;(it "processes board cell selection"
  ;  (let [initial-state {:status :in-progress
  ;                       :interface :web
  ;                       :board [[nil nil nil][nil nil nil][nil nil nil]]
  ;                       :players [{:character "X" :play-type :human}
  ;                                 {:character "O" :play-type :human}]
  ;                       :active-player-index 0
  ;                       :response 0}
  ;        result (sut/process-input initial-state)]
  ;    ;; Should place X in first position
  ;    (should= "X" (get-in result [:board 0 0]))))
  ;
  ;
  ;(describe "DOM integration"
  ;  (it "can render component to DOM"
  ;    (let [container (js/document.createElement "div")
  ;          root (rdomc/create-root container)]
  ;      (.setAttribute container "id" "test-container")
  ;      (js/document.body.appendChild container)
  ;
  ;      ;; Render your component
  ;      (rdomc/render root [sut/game-component])
  ;
  ;      ;; Test that it rendered
  ;      (should= 1 (.-length (.getElementsByClassName container "tic-tac-toe-app")))
  ;
  ;      ;; Cleanup
  ;      (js/document.body.removeChild container))))
  ;
  ;
  ;(it "clicking start button updates state"
  ;  (with-redefs [sut/state (r/atom {:status :welcome :interface :web})]
  ;               ;; Simulate the handle-input call that would happen on click
  ;               (sut/handle-input :start)
  ;
  ;               ;; Check that state was updated
  ;               (should= :config-x-type (:status @sut/state))))
  ;
  ;(it "selecting player type updates response"
  ;  (with-redefs [sut/state (r/atom {:status :config-x-type :interface :web})]
  ;               ;; Simulate clicking human option
  ;               (swap! sut/state assoc :response :human)
  ;
  ;               (should= :human (:response @sut/state))))
  ;
  ;(it "processes full game setup flow"
  ;  (with-redefs [sut/state (r/atom (core/initial-state {:interface :web}))]
  ;               ;; Start game
  ;               (sut/handle-input :start)
  ;               (should= :config-x-type (:status @sut/state))
  ;
  ;               ;; Configure X as human
  ;               (swap! sut/state assoc :response :human)
  ;               (sut/handle-input :human)
  ;               (should= :config-o-type (:status @sut/state))
  ;
  ;               ;; Configure O as computer
  ;               (swap! sut/state assoc :response :computer)
  ;               (sut/handle-input :computer)
  ;               (should= :config-o-difficulty (:status @sut/state))))
  )
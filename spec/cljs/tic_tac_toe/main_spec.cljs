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

  (wire/with-root-dom)
  (before (wire/render [sut/game-component] (wire/select "#root")))

  (context "welcome state"
    (before (reset! sut/state {:interface :static :status :welcome :save :ratom}))
    (it "renders welcome"
      (should-select ".tic-tac-toe-app")
      (should-select ".welcome")
      (should-select ".action-button"))

    (it "progresses from welcome to config-x-type on a click anywhere"
      (wire/click! ".action-button")
      (should-select ".config-x-type")
      (should-select ".tic-tac-toe-app")
      )
    )

  (context "config-x-type"
    (before (reset! sut/state {:interface :static :status :config-x-type :save :ratom}))
    (it "renders config-x"
      (should-select ".config-x-type")
      (should-select "div.config-x-type button.option"))
    )

  ;
  ;(it "welcome screen renders correctly"
  ;  (let [state {:status :welcome :interface :web}
  ;        result (core/draw-state state)]
  ;    (should= :div.welcome (first result))
  ;    (should-contain "Welcome to Tic-Tac-Toe!" (str result))))
  ;
  ;(it "config screen shows player options"
  ;  (let [state {:status :config-x-type :interface :web}
  ;        result (core/draw-state state)]
  ;    (should= :div.config (first result))
  ;    (should-contain "Choose X Player Type" (str result))))
  ;
  ;(it "board renders with correct dimensions"
  ;  (with-redefs [sut/state (r/atom {:status              :in-progress
  ;                                        :interface           :web
  ;                                        :board               [[nil nil nil]
  ;                                                [nil nil nil]
  ;                                                [nil nil nil]]
  ;                                        :players             [{:character "X"} {:character "O"}]
  ;                                        :active-player-index 0})]
  ;               (let [state @sut/state
  ;                     board-component (sut/render-board state)]
  ;                 (should= :div.board (first board-component))
  ;                 ;; Should have 3 rows for 3x3 board
  ;                 (should= 3 (count (rest board-component))))))
  ;
  ;
  ;
  ;(it "processes welcome input correctly"
  ;  (let [initial-state {:status :welcome :interface :web}
  ;        result (sut/process-input (assoc initial-state :response :start))]
  ;    (should= :config-x-type (:status result))))
  ;
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
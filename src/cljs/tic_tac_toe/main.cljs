(ns tic-tac-toe.main
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as dom]
            [c3kit.wire.js :as wjs]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.draw]
            [tic-tac-toe.wrappers :as w]))


(defmethod core/save-game :ratom [state] state)
(defmethod core/load-game :ratom [state] state)
(defmethod core/delete-save :ratom [state] state)

(defn game-component []
  (core/draw-state @w/state))

(defn ^:export init []
  (dom/render [game-component] (wjs/element-by-id "app")))

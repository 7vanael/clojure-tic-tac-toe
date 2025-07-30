(ns tic-tac-toe.main
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent.dom.client :as rdomc]))

(defn hello []
      (if false
        [:h1 "Hello from Reagent"]
        [:h1 "Goodbye from Reagent"]
        ))

(defn ^:export init []
      (rdomc/render (rdomc/create-root (js/document.getElementById "app")) [hello]))

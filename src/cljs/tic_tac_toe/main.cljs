(ns tic-tac-toe.main
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn hello []
      (if false
        [:h1 "Hello from Reagent"]
        [:h1 "Goodbye from Reagent"]
        ))

(defn ^:export init []
      (rdom/render [hello] (js/document.getElementById "app")))

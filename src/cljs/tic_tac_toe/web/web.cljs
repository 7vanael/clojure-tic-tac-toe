(ns tic-tac-toe.web.web
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn hello []
      (if- true
        [:h1 "Hello from Reagent"]
        [:h1 "Goodbye from Reagent"]
        ))



(defn ^:export init []
      (rdom/render [hello] (js/document.getElementById "app")))

(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [tic-tac-toe.main :as sut]))

(describe "main"

  (it "hello is goodbye"
    (should= [:h1 "Goodbye from Reagent"] (sut/hello)))
  )
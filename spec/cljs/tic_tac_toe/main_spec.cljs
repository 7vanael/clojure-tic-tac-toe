(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should= ]])
  (:require [tic-tac-toe.main :as sut]))

(describe "main"

  (it "hello is goodbye"
    (should= -1 (sut/hello)))
  )
(ns tic-tac-toe.persistence.spec-helper
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]))

(def mock-db (atom nil))

(defmethod core/save-game :mock [state] (reset! mock-db state))
(defmethod core/load-game :mock [_] @mock-db)
(defmethod core/delete-save :mock [_] (reset! mock-db nil))

(describe "mock"

  (it "saves"
    (should= {:save :mock :foo :bar} (core/save-game {:save :mock :foo :bar}))
    (should= {:save :mock :foo :bar} @mock-db))

  (it "loads"
    (core/save-game {:save :mock :foo :bar})
    (should= {:save :mock :foo :bar} (core/load-game {:save :mock})))

  (it "deletes"
    (core/delete-save {:save :mock})
    (should= nil (core/load-game {:save :mock :foo :bar}))))
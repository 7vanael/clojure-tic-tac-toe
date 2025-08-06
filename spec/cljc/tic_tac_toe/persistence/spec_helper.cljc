(ns tic-tac-toe.persistence.spec-helper
  (:require [speclj.core #?(:clj :refer :cljs :refer-macros) [describe it should=]]
            [tic-tac-toe.core :as core]))

(def mock-db (atom nil))

(defmethod core/save-game :mock [state] (reset! mock-db state))
(defmethod core/load-game :mock [state] (if (nil? @mock-db)
                                          state
                                          (assoc @mock-db :status :found-save)))
(defmethod core/delete-save :mock [_] (reset! mock-db nil))

(describe "mock"

  (it "saves"
    (should= {:save :mock :foo :bar} (core/save-game {:save :mock :foo :bar}))
    (should= {:save :mock :foo :bar} @mock-db))

  (it "loads"
    (core/save-game {:save :mock :foo :bar})
    (should= {:save :mock :foo :bar :status :found-save} (core/load-game {:save :mock}))
    (core/delete-save {:save :mock})
    (should= {:save :mock :bar :foo} (core/load-game {:save :mock :bar :foo})))

  (it "deletes"
    (core/delete-save {:save :mock})
    (should= {:save :mock :foo :bar} (core/load-game {:save :mock :foo :bar}))))
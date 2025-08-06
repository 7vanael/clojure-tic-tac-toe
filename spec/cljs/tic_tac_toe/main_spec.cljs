(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [describe it should= should before context focus-context should-contain should-be-nil with-stubs stub should-have-invoked should-not-have-invoked]]
                   [c3kit.wire.spec-helperc :refer [should-select should-not-select]])
  (:require [reagent.core :as r]
            [c3kit.wire.spec-helper :as wire]
            [speclj.core]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.spec-helper :as helper]
            [tic-tac-toe.main :as sut]
            [tic-tac-toe.draw]))



(describe "main static"
  (with-stubs)
  (wire/with-root-dom)

  (before (wire/render [sut/game-component]))




  )
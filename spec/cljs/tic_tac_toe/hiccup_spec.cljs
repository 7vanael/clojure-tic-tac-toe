;(ns tic-tac-toe.hiccup-spec
;  (:require-macros [speclj.core :refer [describe context it should= should-contain]]
;                   [c3kit.wire.spec-helperc :refer [should-select]])
;  (:require [reagent.core :as r]
;            [c3kit.wire.spec-helper :as wire]
;            [reagent.dom.client :as rdomc]
;            [speclj.core]
;            [tic-tac-toe.core :as core]
;            [tic-tac-toe.spec-helper :as helper]
;            [tic-tac-toe.hiccup :as sut]))
;
;(describe "hiccup"
;  (wire/with-root-dom)
;  (before (wire/render [sut/app]))
;
;
;  (context "welcome state"
;    (it "renders welcome"
;      (let [state (helper/state-create {:interface :static :status :welcome :save :ratom})
;            result (core/draw-state state)]
;        (should= :div.welcome (first result))
;        (should-contain {:id "welcome"} result)))))
;(ns tic-tac-toe.gui.gui-spec
;  (:require [speclj.core :refer :all]
;            [tic-tac-toe.gui.gui :refer :all]
;            [tic-tac-toe.spec-helper :as helper]))
;
;(describe "gui with Quil"
;
;  (it "initializes a null set-up"
;    (should= (helper/pre-state :edn) (setup {:save :edn :interface :gui}))
;    (should= (helper/pre-state :sql) (setup {:save :sql :interface :gui}))
;    (should= (helper/pre-state :mock) (setup {:save :mock :interface :gui})))
;  )
(ns tic-tac-toe.gui.config-players-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.config-players :refer :all]
            [tic-tac-toe.gui.gui_core :as multis]
            [tic-tac-toe.gui.gui-spec :as test-gui]))

(describe "configure players"
  (context "config x"
           (it "moves to config o type and sets x-type to Human if human button clicked on config-x-type"
             (let [event     {:x 144 :y 350}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-x-type}) event)]
               (should= (test-gui/state-create {:status :config-o-type :x-type :human}) new-state)))

           (it "moves to config x difficulty and sets x-type to computer if computer button clicked on config-x-type"
             (let [event     {:x 432 :y 350}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-x-type}) event)]
               (should= (test-gui/state-create {:status :config-x-difficulty :x-type :computer}) new-state)))

           (it "moves to config o type if x-difficulty is set to easy"
             (let [event     {:x 288 :y 288}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-x-difficulty :x-type :computer}) event)]
               (should= (test-gui/state-create {:status :config-o-type :x-type :computer :x-difficulty :easy}) new-state)))

           (it "moves to config o type if x-difficulty is set to medium"
             (let [event     {:x 288 :y 432}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-x-difficulty :x-type :computer}) event)]
               (should= (test-gui/state-create {:status :config-o-type :x-type :computer :x-difficulty :medium}) new-state)))

           (it "moves to config o type if x-difficulty is set to hard"
             (let [event     {:x 288 :y 576}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-x-difficulty :x-type :computer}) event)]
               (should= (test-gui/state-create {:status :config-o-type :x-type :computer :x-difficulty :hard}) new-state)))
           )

  (context "config o"
           (it "moves to select-board if o-type is set to human"
             (let [event     {:x 144 :y 350}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-o-type}) event)]
               (should= (test-gui/state-create {:status :select-board :o-type :human}) new-state)))

           (it "moves to config o difficulty and sets o-type to computer if right side of board clicked on config-o-type"
             (let [event     {:x 432 :y 350}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-o-type}) event)]
               (should= (test-gui/state-create {:status :config-o-difficulty :o-type :computer}) new-state)))

           (it "moves to select-board after o-difficulty is set to easy"
             (let [event     {:x 288 :y 288}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-o-difficulty :o-type :computer}) event)]
               (should= (test-gui/state-create {:status :select-board :o-type :computer :o-difficulty :easy}) new-state)))

           (it "moves to select-board after o-difficulty is set to medium"
             (let [event     {:x 288 :y 432}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-o-difficulty :o-type :computer}) event)]
               (should= (test-gui/state-create {:status :select-board :o-type :computer :o-difficulty :medium}) new-state)))

           (it "moves to select-board after o-difficulty is set to hard"
             (let [event     {:x 288 :y 576}
                   new-state (multis/mouse-clicked (test-gui/state-create {:status :config-o-difficulty :o-type :computer}) event)]
               (should= (test-gui/state-create {:status :select-board :o-type :computer :o-difficulty :hard}) new-state)))
           )
  )
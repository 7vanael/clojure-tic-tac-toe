(ns tic-tac-toe.gui.gui-util-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.gui-spec :as test-gui]
            [tic-tac-toe.gui.gui-util :refer :all]))

(describe "gui util"

  (it "can tell if a button is clicked"
    (should-not (button-clicked? [1 1] [4 4 2 2]))
    (should (button-clicked? [4 4] [4 4 2 2]))
    )


  )
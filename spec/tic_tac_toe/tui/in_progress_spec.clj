(ns tic-tac-toe.tui.in_progress_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.tui.in-progress :as sut]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.core :as core]))

(describe "tui in-progress"
  (with-stubs)
  (redefs-around [spit (stub :spit)])

  (context "get-selection"
    (it "found-save; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :found-save})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :found-save}))))

    (it "config-x-type; gets player type selection from console"
      (should= :human (with-in-str "human" (core/get-selection {:interface :tui :status :config-x-type})))
      (should= :computer (with-in-str "computer" (core/get-selection {:interface :tui :status :config-x-type}))))

    (it "config-o-type; gets player type selection from console"
      (should= :human (with-in-str "human" (core/get-selection {:interface :tui :status :config-o-type})))
      (should= :computer (with-in-str "computer" (core/get-selection {:interface :tui :status :config-o-type}))))

    (it "Config-x-difficulty; gets the computer difficulty selection from console"
      (should= :easy (with-in-str "easy" (core/get-selection {:interface :tui :status :config-x-difficulty})))
      (should= :medium (with-in-str "medium" (core/get-selection {:interface :tui :status :config-x-difficulty})))
      (should= :hard (with-in-str "hard" (core/get-selection {:interface :tui :status :config-x-difficulty}))))

    (it "Config-o-difficulty; gets the computer difficulty selection from console"
      (should= :easy (with-in-str "easy" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      (should= :medium (with-in-str "medium" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      (should= :hard (with-in-str "hard" (core/get-selection {:interface :tui :status :config-o-difficulty})))
      )

    (it "Select-board; gets the board size selection (3, 4, [3 3 3]) from console"
      (should= 3 (with-in-str "1" (core/get-selection {:interface :tui :status :select-board})))
      (should= 4 (with-in-str "2" (core/get-selection {:interface :tui :status :select-board})))
      (should= [3 3 3] (with-in-str "3" (core/get-selection {:interface :tui :status :select-board})))
      )
    (it "tie; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :tie})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :tie}))))

    (it "winner; gets a true/false"
      (should= true (with-in-str "y\n" (core/get-selection {:interface :tui :status :winner})))
      (should= false (with-in-str "n\n" (core/get-selection {:interface :tui :status :winner}))))
    )

  (it "The human turn method is called if the active player is human"
    (with-redefs [core/take-human-turn (stub :human-turn)]
      (core/take-turn test-core/state-center-x-mid-turn)
      (should-have-invoked :human-turn)))

  (it "lets a player take a turn on a 4x board, repeatedly asks for input until valid play selected"
    (with-redefs [console/print-number-prompt (stub :print-dup)
                  console/announce-player     (stub :print-dup-announce)]
      (should= test-core/state-4-first-x
               (with-in-str "0\n45\njunk\n6\n" (core/take-human-turn test-core/state-4-initial)))
      (should-have-invoked :print-dup {:times 4})))
  )
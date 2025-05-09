(ns tic-tac-toe.persistence-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.persistence :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.io FileNotFoundException)))

(def test-file
  (str (System/getProperty "java.io.tmpdir") "test-game-save.edn"))

(describe "persistence"

  (it "can save the state to a file"
    (let [state     (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human :board [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]})]
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (save-game state)
        (should= (dissoc state :interface) (edn/read-string (slurp test-file)))
        (io/delete-file test-file true))))

  (it "returns nil if file not found"
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (should= nil (load-game))))

  (it "can load a state from a file"
    (let [state     (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human :board [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]})]
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (save-game state)
        (should= (dissoc state :interface) (load-game))
        (io/delete-file test-file true))))

  (it "can delete a save-file"
    (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (save-game (test-core/state-create {:active-player-index 0 :interface :tui :x-type :human :o-type :human :board [["X" 2 "O"] ["X" "O" "O"] ["X" 8 "X"]]}))
        (delete-save)
        (should-throw FileNotFoundException (slurp test-file))))
  )
(ns tic-tac-toe.persistence-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.persistence :refer :all]
            [tic-tac-toe.core-spec :as test-core]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.io FileNotFoundException)))

(def test-file-path
  (str (System/getProperty "java.io.tmpdir") "test-game-save.edn"))

(describe "persistence"

  (it "can save the state to a file"
    (let [test-file test-file-path
          state     (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human :board [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]})]
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (save-game state)
        (should= state (edn/read-string (slurp test-file)))
        (io/delete-file test-file true))))

  (it "returns nil if file not found"
    (let [test-file test-file-path]
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (should= nil (load-game)))))

  (it "can load a state from a file"
    (let [test-file test-file-path
          state     (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human :board [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]})]
      (with-redefs [tic-tac-toe.persistence/savefile test-file]
        (save-game state)
        (should= state (load-game))
        (io/delete-file test-file true))))
  )

(ns tic-tac-toe.persistence.file-spec
  (:require [clojure.edn :as edn]
            [tic-tac-toe.core :as sut]
            [speclj.core :refer :all]
            [tic-tac-toe.persistence.file :as file]
            [tic-tac-toe.core-spec :as test-core])

  (:import (java.io FileNotFoundException)))

(def test-state-in-progress
  (test-core/state-create{:active-player-index 1 :interface :tui :x-type :human :o-type :human
                          :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]] :save :edn}))

(def test-state-new
  {:interface :gui :status :welcome :save :edn})

(describe "persist in File"
  (with-stubs)
  (redefs-around [spit (stub :spit)])

  (it "can save the state to a file"
    (sut/save-game test-state-in-progress)
    (should-have-invoked :spit))

  (it "returns the initial state if file not found"
    (should= test-state-new (sut/load-game test-state-new)))

  (it "can load a state from a file"
    (with-redefs [edn/read-string (stub :edn {:return (test-core/state-create {:active-player-index 1 :x-type :human :o-type :human
                                                                               :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]
                                                                               :status :in-progress})})
                  slurp           (stub :slurp)]
      (let [state (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human
                                           :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]] :status :in-progress})]
        (should= (dissoc state :interface) (sut/load-game test-state-new))
        )))

  (it "can delete a save-file"
    (sut/save-game (test-core/state-create {:active-player-index 0 :interface :tui :x-type :human :o-type :human
                                            :board               [["X" 2 "O"] ["X" "O" "O"] ["X" 8 "X"]] :save :edn}))
    (sut/delete-save test-state-in-progress)
    (should-throw FileNotFoundException (slurp file/savefile))
    (should= test-state-new (sut/load-game test-state-new)))
  )
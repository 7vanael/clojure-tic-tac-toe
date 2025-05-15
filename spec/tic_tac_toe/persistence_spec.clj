(ns tic-tac-toe.persistence-spec
  (:require [clojure.edn :as edn]
            [speclj.core :refer :all]
            [tic-tac-toe.persistence :as sut]
            [tic-tac-toe.core-spec :as test-core])

  (:import (java.io FileNotFoundException)))

(describe "persistence"
  (with-stubs)
  (redefs-around [spit (stub :spit)])

  (it "can save the state to a file"
    (let [state (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human
                                         :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]})]
      (sut/save-game state)
      (should-have-invoked :spit)))

  (it "returns nil if file not found"
    (should= nil (sut/load-game)))

  (it "can load a state from a file"
    (with-redefs [edn/read-string (stub :edn {:return (test-core/state-create {:active-player-index 1 :x-type :human :o-type :human
                                                                               :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]]
                                                                               :status :in-progress})})
                  slurp           (stub :slurp)]
      (let [state (test-core/state-create {:active-player-index 1 :interface :tui :x-type :human :o-type :human
                                           :board               [["X" 2 "O"] [4 5 "O"] ["X" 8 "X"]] :status :in-progress})]
        (should= (dissoc state :interface) (sut/load-game))
        )))

  (it "can delete a save-file"
    (sut/save-game (test-core/state-create {:active-player-index 0 :interface :tui :x-type :human :o-type :human
                                            :board               [["X" 2 "O"] ["X" "O" "O"] ["X" 8 "X"]]}))
    (sut/delete-save)
    (should-throw FileNotFoundException (slurp sut/savefile))
    (should= nil (sut/load-game)))
  )
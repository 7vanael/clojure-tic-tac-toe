(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as sut]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(defn state-create [{:keys [interface board active-player-index status x-type o-type x-difficulty o-difficulty cells save]
                     :or   {board               nil
                            active-player-index 0
                            status              :welcome
                            x-type              nil
                            o-type              nil
                            x-difficulty        nil
                            o-difficulty        nil
                            save                :mock}}]
  (cond-> {:board               board
           :active-player-index active-player-index
           :status              status
           :players             [{:character "X" :play-type x-type :difficulty x-difficulty}
                                 {:character "O" :play-type o-type :difficulty o-difficulty}]}
          (some? cells) (assoc :cells cells)
          (some? interface) (assoc :interface interface)
          (some? save) (assoc :save save)))

(def empty-board
  [[1 2 3]
   [4 5 6]
   [7 8 9]])

(def empty-4-board
  [[1 2 3 4]
   [5 6 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def empty-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 8 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])

(def first-x-3d-board
  [[[1 2 3]
    [4 5 6]
    [7 "X" 9]]
   [[10 11 12]
    [13 14 15]
    [16 17 18]]
   [[19 20 21]
    [22 23 24]
    [25 26 27]]])

(def first-X-4-board
  [[1 2 3 4]
   [5 "X" 7 8]
   [9 10 11 12]
   [13 14 15 16]])

(def center-x-corner-o-board
  [["O" 2 3]
   [4 "X" 6]
   [7 8 9]])

(def center-x-corner-xo-board
  [["O" 2 3]
   [4 "X" 6]
   ["X" 8 9]])

(def center-x-board
  [[1 2 3]
   [4 "X" 6]
   [7 8 9]])

(def state-medium-initial-4
  {:interface           :tui
   :board               empty-4-board
   :active-player-index 0
   :status              "in-progress"
   :players             [{:character "X" :play-type :computer :difficulty :medium}
                         {:character "O" :play-type :computer :difficulty :easy}]})

(def state-center-x-mid-turn
  {:interface           :tui
   :board               center-x-board
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(describe "core"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db nil))

  (it "can tell what play-type of turn it is"
    (should= true (sut/currently-human? (state-create {:interface :tui :active-player-index 0 :x-type :human :o-type :human}))))

  (it "knows which player is the correct player to take the next turn"
    (should= "X" (sut/next-player empty-board))
    (should= "X" (sut/next-player empty-4-board))
    (should= "X" (sut/next-player empty-3d-board))
    (should= "X" (sut/next-player center-x-corner-o-board))
    (should= "O" (sut/next-player center-x-corner-xo-board))
    (should= "O" (sut/next-player first-x-3d-board))
    (should= "O" (sut/next-player first-X-4-board)))


  (it "dispatches turns correctly when it's a human turn"
    (with-redefs [sut/take-human-turn    (stub :human-turn)
                  sut/take-computer-turn (stub :computer-turn)]
      (sut/take-turn (state-create {:interface    :tui :active-player-index 0 :x-type :human :o-type :computer
                                :o-difficulty :medium :board empty-board}))
      (should-have-invoked :human-turn)
      (should-not-have-invoked :computer-turn)))

  (it "The human turn method is called if the active player is human"
    (with-redefs [sut/take-human-turn (stub :human-turn)]
      (sut/take-turn state-center-x-mid-turn)
      (should-have-invoked :human-turn)))

  (it "can tell what difficulty computer turn it is"
    (should= :hard (sut/get-computer-difficulty
                     (state-create {:interface :tui :active-player-index 0 :x-type :computer :x-difficulty :hard :o-type :human}))))

  (it "changes the active player O"
    (should= (state-create {:active-player-index 1 :x-type :human :o-type :human :board first-x-3d-board :status :in-progress})
             (sut/change-player (state-create {:active-player-index 0 :x-type :human :o-type :human :board first-x-3d-board :status :in-progress}))))

  (it "changes the active player X"
    (should= (state-create {:active-player-index 0 :x-type :human :o-type :human :board center-x-corner-o-board :status :in-progress})
             (sut/change-player (state-create {:active-player-index 1 :x-type :human :o-type :human :board center-x-corner-o-board :status :in-progress}))))


  (it "does not change the active player if the game is over (:winner)"
    (let [state (state-create {:active-player-index 1 :x-type :human :o-type :computer :o-difficulty :medium
                               :board               empty-board :status :winner})]
      (should= state (sut/change-player state))))
  (it "does not change the active player if the game is over (:tie)"
    (let [state (state-create {:active-player-index 1 :x-type :human :o-type :computer :o-difficulty :medium
                               :board               empty-board :status :tie})]
      (should= state (sut/change-player state))))



  )
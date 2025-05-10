(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]))

(defn state-create [{:keys [interface board active-player-index status x-type o-type x-difficulty o-difficulty cells]
                     :or   {interface           :gui
                            board               nil
                            active-player-index 0
                            status              :welcome
                            x-type              nil
                            o-type              nil
                            x-difficulty        nil
                            o-difficulty        nil}}]
  (cond-> {:interface           interface
           :board               board
           :active-player-index active-player-index
           :status              status
           :players             [{:character "X" :play-type x-type :difficulty x-difficulty}
                                 {:character "O" :play-type o-type :difficulty o-difficulty}]}
          (some? cells) (assoc :cells cells)))

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

(def state-initial
  {:interface           :tui
   :board               empty-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-4-initial
  {:interface           :tui
   :board               empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

(def state-4-first-x
  {:interface           :tui
   :board               first-X-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]})

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

(def state-computer-2-4-empty
  {:interface           :tui
   :board               empty-4-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :computer :difficulty :hard}
                         {:character "O" :play-type :computer :difficulty :hard}]})

(describe "core"
  (with-stubs)

  (it "can tell what play-type of turn it is"
    (should= true (currently-human? (state-create {:interface :tui :active-player-index 0 :x-type :human :o-type :human}))))

  (it "knows which player is the correct player to take the next turn"
    (should= "X" (next-player empty-board))
    (should= "X" (next-player empty-4-board))
    (should= "X" (next-player empty-3d-board))
    (should= "X" (next-player center-x-corner-o-board))
    (should= "O" (next-player center-x-corner-xo-board))
    (should= "O" (next-player first-x-3d-board))
    (should= "O" (next-player first-X-4-board)))


  (it "dispatches turns correctly when it's a human turn"
    (with-redefs [take-human-turn    (stub :human-turn)
                  take-computer-turn (stub :computer-turn)]
      (take-turn (state-create {:interface    :tui :active-player-index 0 :x-type :human :o-type :computer
                                :o-difficulty :medium :board empty-board}))
      (should-have-invoked :human-turn)
      (should-not-have-invoked :computer-turn)))

  (it "returns the state unchanged when it's not the correct player's turn"
    (with-redefs [take-human-turn    (stub :human-turn)
                  take-computer-turn (stub :computer-turn)]
      (let [starting-state (state-create {:interface    :tui :active-player-index 0 :x-type :human :o-type :computer
                                          :o-difficulty :medium :board first-X-4-board})]
        (should= starting-state (take-turn starting-state))
        (should-not-have-invoked :human-turn))))

  (it "can tell what difficulty computer turn it is"
    (should= :hard (get-computer-difficulty
                     (state-create {:interface :tui :active-player-index 0 :x-type :computer :x-difficulty :hard :o-type :human}))))

  (it "changes the active player O"
    (should= (state-create {:active-player-index 1 :x-type :human :o-type :human :board first-x-3d-board :status :in-progress})
             (change-player (state-create {:active-player-index 0 :x-type :human :o-type :human :board first-x-3d-board :status :in-progress}))))

  (it "changes the active player X"
    (should= (state-create {:active-player-index 0 :x-type :human :o-type :human :board center-x-corner-o-board :status :in-progress})
             (change-player (state-create {:active-player-index 1 :x-type :human :o-type :human :board center-x-corner-o-board :status :in-progress}))))


  (it "does not change the active player if the game is over"
    (should= (state-create {:active-player-index 0 :x-type :human :o-type :computer :o-difficulty :medium
                            :board               empty-board :status :winner})
             (change-player (state-create {:active-player-index 0 :x-type :human :o-type :computer :o-difficulty :medium
                                           :board               empty-board :status :winner}))))
  )
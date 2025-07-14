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

(def state-initial
  {:interface           :tui
   :board               empty-board
   :active-player-index 0
   :status              :in-progress
   :players             [{:character "X" :play-type :human :difficulty nil}
                         {:character "O" :play-type :human :difficulty nil}]
   :save                :mock})

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
                         {:character "O" :play-type :computer :difficulty :hard}]
   :save                :mock})

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

  (context "updates the state"
    (it "Welcome; checks for a saved game, offers to load it if found"
      (let [new-game         (state-create {:status :welcome :interface :tui :save :mock})
            saved-game-state (sut/save-game (state-create {:interface                     :tui :status :in-progress :board [["X" "O" "X"]
                                                                                                                             [4 "X" 6]
                                                                                                                             [7 8 "O"]]
                                                                      :active-player-index 1 :type-x :human :type-o :human :save :mock}))
            result           (with-in-str "y\n" (sut/update-state new-game))]

        (should= (assoc saved-game-state :status :found-save) result)))

    (it "Welcome; proceeds to x-type configuration if no loaded game found"
      (let [new-game (state-create {:status :welcome :interface :tui :save :mock})
            result   (with-in-str "n\n" (sut/update-state new-game))]
        (should= (assoc new-game :status :config-x-type) result)))

    (it "Found-save; resumes play of a loaded game if player selects yes"
      (with-redefs [sut/get-selection (stub :selection {:return true})]
        (let [state     (state-create {:interface        :tui :status :found-save :active-player-index 1
                                                 :type-x :human :type-o :human :save :mock :board [["X" "O" "X"]
                                                                                                      [4 "X" 6]
                                                                                                      [7 8 "O"]]})
              new-state (sut/update-state state)]
          (should= (assoc state :status :in-progress) new-state))))

    (it "Found-save; moves to config-x-type if player selects not to load save"
      (with-redefs [sut/get-selection (stub :selection {:return false})]
        (let [state     (state-create {:interface        :tui :status :found-save :active-player-index 1
                                                 :type-x :human :type-o :human :save :mock :board [["X" "O" "X"]
                                                                                                      [4 "X" 6]
                                                                                                      [7 8 "O"]]})
              clean-state (sut/initial-state {:interface :tui :save :mock})
              new-state (sut/update-state state)]
          (should= (assoc clean-state :status :config-x-type) new-state))))

    (it "Config-x-type; to config-x-difficulty if computer is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :computer})]
        (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock})
              expected-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Config-x-type;to config-o-type if human is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :human})]
        (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock})
              expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Config-x-difficulty; changes status to config-o-type once difficulty is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :hard})]
        (let [starting-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})
              expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Config-o-type; changes status to config-o-difficulty if computer is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :computer})]
        (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock})
              expected-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Config-o-type; changes status to select-board if human is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :human})]
        (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock})
              expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
          (should= expected-state (sut/update-state starting-state)))))


    (it "Config-o-difficulty; changes status to select-board once difficulty is selected"
      (with-redefs [sut/get-selection (stub :selection {:return :easy})]
        (let [starting-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})
              expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :easy})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Select-board; changes status to in-progress and populates a board once a board has been selected"
      (with-redefs [sut/get-selection (stub :selection {:return 3})]
        (let [starting-state (state-create {:interface :tui :status :select-board :save :mock})
              expected-state (state-create {:interface :tui :status :in-progress :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Tie; changes status to config-x-type if get-selection (play again) returns true"
      (with-redefs [sut/get-selection (stub :selection {:return true})]
        (let [starting-state (state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (state-create {:interface :tui :status :config-x-type :save :mock })]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Tie; changes status to game-over if get-selection (play again) returns false"
      (with-redefs [sut/get-selection (stub :selection {:return false})]
        (let [starting-state (state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Winner; changes status to config-x-type if get-selection (play again) returns true"
      (with-redefs [sut/get-selection (stub :selection {:return true})]
        (let [starting-state (state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (state-create {:interface :tui :status :config-x-type :save :mock })]
          (should= expected-state (sut/update-state starting-state)))))

    (it "Winner; changes status to game-over if get-selection (play again) returns false"
      (with-redefs [sut/get-selection (stub :selection {:return false})]
        (let [starting-state (state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
              expected-state (state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
          (should= expected-state (sut/update-state starting-state)))))
    )

  )
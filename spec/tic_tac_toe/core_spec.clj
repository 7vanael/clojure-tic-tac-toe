(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
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
   :save                :mock
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

  (context "updates state"
    (it "Welcome; no loaded save goes to config-x"
      (let [state  {:interface :tui :save :mock :status :welcome}
            result (sut/update-state state 1)]
        (should= :config-x-type (:status result))))

    (it "Welcome; loaded save goes to found-save"
      (let [saved-game (sut/save-game state-medium-initial-4)
            state      {:interface :tui :save :mock :status :welcome}
            result     (sut/update-state state 1)]
        (should= :found-save (:status result))
        (should= empty-4-board (:board result))))

    (it "Found-save; if value is true, resume (in-progress)"
      (let [state  (assoc state-medium-initial-4 :status :found-save)
            result (sut/update-state state true)]
        (should= :in-progress (:status result))
        (should= empty-4-board (:board result))))

    (it "Found-save; if value is false, start-fresh"
      (let [state  (assoc state-medium-initial-4 :status :found-save)
            result (sut/update-state state false)]
        (should= :config-x-type (:status result))
        (should= nil (:board result))))

    (it "Config-x-type; if play-type is human, config-o-type"
      (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock})
            expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
        (should= expected-state (sut/update-state starting-state :human))))

    (it "Config-x-type; if play-type is computer, config-x-difficulty"
      (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock})
            expected-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
        (should= expected-state (sut/update-state starting-state :computer))))

    (it "Config-x-difficulty; changes status to config-o-type once difficulty is selected"
      (let [starting-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})
            expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
        (should= expected-state (core/update-state starting-state :hard))))

    (it "Config-o-type; changes status to config-o-difficulty if computer is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock})
            expected-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
        (should= expected-state (core/update-state starting-state :computer))))

    (it "Config-o-type; changes status to select-board if human is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock})
            expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
        (should= expected-state (core/update-state starting-state :human))))


    (it "Config-o-difficulty; changes status to select-board once difficulty is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})
            expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :easy})]
        (should= expected-state (core/update-state starting-state :easy))))

    (it "Select-board; changes status to in-progress and populates a board once a board has been selected"
      (let [starting-state (state-create {:interface :tui :status :select-board :save :mock})
            expected-state (state-create {:interface :tui :status :in-progress :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
        (should= expected-state (core/update-state starting-state 3))))

    (it "Tie; changes status to config-x-type if get-selection (play again) returns true"
      (let [starting-state (state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
            expected-state (state-create {:interface :tui :status :config-x-type :save :mock})]
        (should= expected-state (core/update-state starting-state true))))

    (it "Tie; changes status to game-over if get-selection (play again) returns false"
      (let [starting-state (state-create {:interface :tui :status :tie :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
            expected-state (state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
        (should= expected-state (core/update-state starting-state false))))

    (it "Winner; changes status to config-x-type if get-selection (play again) returns true"
      (let [starting-state (state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
            expected-state (state-create {:interface :tui :status :config-x-type :save :mock})]
        (should= expected-state (core/update-state starting-state true))))

    (it "Winner; changes status to game-over if get-selection (play again) returns false"
      (let [starting-state (state-create {:interface :tui :status :winner :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})
            expected-state (state-create {:interface :tui :status :game-over :save :mock :board [[1 2 3] [4 5 6] [7 8 9]] :x-type :human :o-type :computer :o-difficulty :medium})]
        (should= expected-state (core/update-state starting-state false))))

    )
  )
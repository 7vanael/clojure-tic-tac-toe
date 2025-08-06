(ns tic-tac-toe.core-spec
  (:require [speclj.core #?(:clj :refer :cljs :refer-macros) [describe before redefs-around context it with-stubs should-have-invoked stub should= should should-not should-not-have-invoked should-not=]]
            [tic-tac-toe.core :as sut]
            [tic-tac-toe.persistence.spec-helper :as spec-helper]))

(defn state-create [{:keys [interface board active-player-index status x-type o-type x-difficulty o-difficulty cells save response]
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
          (some? response) (assoc :response response)
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


(def state-winner (state-create {:x-type :human :o-type :human :save :mock :status :winner
                                 :board [["X" "O" "X"]
                                         ["O" "X" "O"]
                                         ["O" "X" "X"]] :active-player-index 0}))

(def state-tie (state-create {:x-type :human :o-type :human :save :mock :status :tie
                              :board [["X" "O" "X"]
                                      ["X" "O" "O"]
                                      ["O" "X" "X"]] :active-player-index 0}))

(def state-in-progress (state-create {:x-type :human :o-type :human :save :mock :status :in-progress
                                      :board [["X" "O" "X"]
                                              ["O" "X" "O"]
                                              ["O" "X" 9]] :active-player-index 1}))


(describe "core"
  (with-stubs)
  (redefs-around [spit (stub :spit)])
  (before (reset! spec-helper/mock-db nil))

  (it "can tell what play-type of turn it is"
    (should= true (sut/currently-human? (state-create {:interface :tui :active-player-index 0 :x-type :human :o-type :human})))
    (should= false (sut/currently-human? (state-create {:interface :tui :active-player-index 0 :x-type :computer :x-difficulty :medium :o-type :human}))))

  (it "dispatches turns correctly when it's a human turn"
    (with-redefs [sut/take-human-turn    (stub :human-turn)
                  sut/take-computer-turn (stub :computer-turn)]
      (sut/take-turn (state-create {:interface    :tui :active-player-index 0 :x-type :human :o-type :computer
                                    :o-difficulty :medium :board empty-board}))
      (should-have-invoked :human-turn)
      (should-not-have-invoked :computer-turn)))

  (it "dispatches turns correctly when it's a computer turn"
    (with-redefs [sut/take-human-turn    (stub :human-turn)
                  sut/take-computer-turn (stub :computer-turn)]
      (sut/take-turn (state-create {:interface    :tui :active-player-index 0 :o-type :human :x-type :computer
                                    :x-difficulty :medium :board empty-board}))
      (should-have-invoked :computer-turn)
      (should-not-have-invoked :human-turn)))

  (it "has a player maybe take a turn"
    (let [starting-state (state-create {:x-type :human :o-type :human :save :mock :board empty-board})
          expected       (state-create {:x-type :human :o-type :human :save :mock :board (assoc-in empty-board [1 1] "X")})
          ending-state   (sut/maybe-take-turn (assoc starting-state :response 5))]
      (should= expected ending-state)))

  (it "does not have a player take a turn if it isn't their turn"
    (let [starting-state (state-create {:x-type :human :o-type :human :save :mock :board (assoc-in empty-board [1 1] "X") :response 5})
          expected   (dissoc starting-state :response)]
      (should= expected (sut/maybe-take-turn starting-state))))

  (it "The human turn method is called if the active player is human"
    (with-redefs [sut/take-human-turn (stub :human-turn)]
      (sut/take-turn state-center-x-mid-turn)
      (should-have-invoked :human-turn)))

  (it "can tell what difficulty computer turn it is"
    (should= :hard (sut/get-computer-difficulty
                     (state-create {:interface :tui :active-player-index 0 :x-type :computer :x-difficulty :hard :o-type :human}))))

  (it "can tell if the game is over"
    (should (sut/game-over? state-winner))
    (should (sut/game-over? state-tie))
    (should-not (sut/game-over? state-in-progress)))

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

  (context "Updates-state; accepting only state"
    (it "Welcome; no loaded save goes to config-x"
      (let [state  {:interface :tui :save :mock :status :welcome}
            result (sut/maybe-load-save state)]
        (should= :config-x-type (:status result))))

    (it "Welcome; loaded save goes to found-save"
      (let [_      (sut/save-game state-in-progress)
            state  {:interface :tui :save :mock :status :welcome}
            result (sut/maybe-load-save state)]
        (should= :found-save (:status result))
        (should= [["X" "O" "X"]
                  ["O" "X" "O"]
                  ["O" "X" 9]] (:board result))))

    (it "Found-save; if value is true, resume (in-progress)"
      (let [state  (assoc state-in-progress :status :found-save)
            result (sut/maybe-resume-save state true)]
        (should= :in-progress (:status result))
        (should= [["X" "O" "X"]
                  ["O" "X" "O"]
                  ["O" "X" 9]] (:board result))))

    (it "Found-save; if value is false, start-fresh"
      (let [state  (assoc state-in-progress :status :found-save)
            result (sut/maybe-resume-save state false)]
        (should= :config-x-type (:status result))
        (should= nil (:board result))))

    (it "Config-x-type; if play-type is human,  config-o-type is next"
      (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock :response :human})
            expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :human})]
        (should= expected-state (sut/config-x-type starting-state))))

    (it "Config-x-type; if play-type is computer, config-x-difficulty"
      (let [starting-state (state-create {:interface :tui :status :config-x-type :save :mock :response :computer})
            expected-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer})]
        (should= expected-state (sut/config-x-type starting-state))))

    (it "Config-x-difficulty; changes status to config-o-type once difficulty is selected"
      (let [starting-state (state-create {:interface :tui :status :config-x-difficulty :save :mock :x-type :computer :response :hard})
            expected-state (state-create {:interface :tui :status :config-o-type :save :mock :x-type :computer :x-difficulty :hard})]
        (should= expected-state (sut/config-x-difficulty starting-state))))

    (it "Config-o-type; changes status to config-o-difficulty if computer is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock :response :computer})
            expected-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer})]
        (should= expected-state (sut/config-o-type starting-state))))

    (it "Config-o-type; changes status to select-board if human is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-type :save :mock :response :human})
            expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :human})]
        (should= expected-state (sut/config-o-type starting-state))))

    (it "Config-o-difficulty; changes status to select-board once difficulty is selected"
      (let [starting-state (state-create {:interface :tui :status :config-o-difficulty :save :mock :o-type :computer :response :easy})
            expected-state (state-create {:interface :tui :status :select-board :save :mock :o-type :computer :o-difficulty :easy})]
        (should= expected-state (sut/config-o-difficulty starting-state))))

    (it "Select-board; changes status to in-progress and populates a board once a board has been selected"
      (let [starting-state (state-create {:interface :tui :status :select-board :save :mock :response 3})
            expected-state (state-create {:interface :tui :status :in-progress :save :mock :board [[1 2 3] [4 5 6] [7 8 9]]})]
        (should= expected-state (sut/select-board starting-state))))

    (it "can play again; status back to config x-type, state reset"
      (let [ending-state (sut/maybe-play-again (assoc state-tie :response true))]
        (should= :config-x-type (:status ending-state))
        (should= nil (:board ending-state))))

    (it "can not play again; status to game-over, state not reset"
      (let [ending-state (sut/maybe-play-again (assoc state-tie :response false))]
        (should= :game-over (:status ending-state))
        (should= (:board state-tie) (:board ending-state))))

    (it "deletes the save at the end of a completed game"
      (sut/save-game (assoc state-tie :interface :tui))
      (let [loaded-game (sut/load-game {:interface :tui :save :mock})
            updated-state (sut/maybe-play-again (assoc state-tie :response false))
            next-loaded-game (sut/load-game {:interface :gui :save :mock})]
        (should= :found-save (:status loaded-game))
        (should= :tui (:interface loaded-game))
        (should= :gui (:interface next-loaded-game))
        (should-not= :found-save (:status next-loaded-game))
        (should= :game-over (:status updated-state))))
    )
  )
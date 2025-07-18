;(ns tic-tac-toe.gui.in-progress-spec
;  (:require [speclj.core :refer :all]
;            [tic-tac-toe.core :as core]
;            [tic-tac-toe.gui.in-progress :refer :all]
;            [tic-tac-toe.core-spec :as test-core]
;            [tic-tac-toe.board_spec :as test-board]
;            [tic-tac-toe.gui.draw :as draw]))
;
;(def cells-center-x-corner-o
;  [{:x 96, :y 240, :value "O"} {:x 288, :y 240, :value 2} {:x 480, :y 240, :value 3}
;   {:x 96, :y 432, :value 4} {:x 288, :y 432, :value "X"} {:x 480, :y 432, :value 6}
;   {:x 96, :y 624, :value 7} {:x 288, :y 624, :value 8} {:x 480, :y 624, :value 9}])
;
;(describe "gui in-progress"
;  (context "mouse-click"
;
;    (it "returns state unchanged if invalid play clicked"
;      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
;                                                    :active-player-index 0
;                                                    :interface           :gui
;                                                    :status              :in-progress
;                                                    :x-type              :human
;                                                    :o-type              :computer
;                                                    :cells               cells-center-x-corner-o
;                                                    :save                :mock})
;            event          {:x (+ draw/grid-origin-x (/ draw/usable-screen 2)) ;space [1 1]
;                            :y (+ draw/grid-origin-y-2d (/ draw/usable-screen 2))}]
;        (should= starting-state (core/mouse-clicked starting-state event))))
;
;    (it "updates the state with the board and calls do-update when a human player clicks a valid square"
;      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
;                                                    :active-player-index 0
;                                                    :interface           :gui
;                                                    :status              :in-progress
;                                                    :x-type              :human
;                                                    :o-type              :computer
;                                                    :save                :mock})
;            event          {:x 96
;                            :y 624}
;            expected       (test-core/state-create {:board               test-board/center-x-corner-xo-board
;                                                    :active-player-index 1
;                                                    :interface           :gui
;                                                    :status              :in-progress
;                                                    :x-type              :human
;                                                    :o-type              :computer
;                                                    :save                :mock})]
;        (should= expected (core/mouse-clicked starting-state event))))
;
;    (it "does not update if active player is not human"
;      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
;                                                    :active-player-index 0
;                                                    :status              :in-progress
;                                                    :interface           :gui
;                                                    :x-type              :computer
;                                                    :x-difficulty        :hard
;                                                    :o-type              :computer
;                                                    :o-difficulty        :hard
;                                                    :cells               cells-center-x-corner-o
;                                                    :save                :mock})
;            event          {:x (+ draw/grid-origin-x (/ (* 0.5 draw/usable-screen) 3)) ;space [2 0]
;                            :y (+ draw/grid-origin-y-2d (/ (* 2.5 draw/usable-screen) 3))}]
;        (should= (:board starting-state) (:board (core/mouse-clicked starting-state event)))))
;
;    (it "does update the board, evaluate it and change players if valid play is selected & player is human"
;      (let [starting-state (test-core/state-create {:board               test-board/center-x-corner-o-board
;                                                    :active-player-index 0
;                                                    :status              :in-progress
;                                                    :interface           :gui
;                                                    :x-type              :human
;                                                    :o-type              :computer
;                                                    :cells               cells-center-x-corner-o
;                                                    :save                :mock})
;            event          {:x (+ draw/grid-origin-x (/ (* 0.5 draw/usable-screen) 3)) ;space [2 0]
;                            :y (+ draw/grid-origin-y-2d (/ (* 2.5 draw/usable-screen) 3))}
;            new-state      (test-core/state-create {:board               test-board/center-x-corner-xo-board
;                                                    :active-player-index 1
;                                                    :interface           :gui
;                                                    :status              :in-progress
;                                                    :x-type              :human
;                                                    :o-type              :computer
;                                                    :cells               cells-center-x-corner-o
;                                                    :save                :mock})]
;        (should= new-state (dissoc (core/mouse-clicked starting-state event) :game-id))))
;    )
;  )

(ns tic-tac-toe.gui.found-save-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.core-spec :as test-core]
            [tic-tac-toe.spec-helper :as helper]))

(describe "found-save"
  (with-stubs)

  (it "Changes the status to in-progress if yes is clicked"
    (let [event       {:x 144 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                               :save   :mock})
          result      (core/mouse-clicked saved-state event)]
      (should= result (assoc saved-state :status :in-progress))))

  (it "calls update-state with 2 if 'no' button is pressed"
    (let [event       {:x 432 :y 350}
          saved-state (test-core/state-create {:status :found-save :interface :gui :type-x :human :type-o :human
                                               :board  [[1 2 "X"] ["O" 5 6] [7 8 9]] :active-player-index 0
                                               :save   :mock})
          result      (core/mouse-clicked saved-state event)]
      (should= result (core/fresh-start saved-state))))

  (it "returns the state unchanged if no button is clicked"
    (let [event         {:x 2 :y 2}
          current-state (assoc (helper/gui-mock-state) :status :found-save)]
      (should= current-state (core/mouse-clicked current-state event))))

  )
(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]))

(defn state-create [{:keys [interface board active-player-index status x-type o-type x-difficulty o-difficulty]
                     :or   {interface :gui
                            board               nil
                            active-player-index 0
                            status              :welcome
                            x-type              nil
                            o-type              nil
                            x-difficulty        nil
                            o-difficulty        nil}}]
  {:interface           interface
   :board               board
   :active-player-index active-player-index
   :status              status
   :players             [{:character "X" :play-type x-type :difficulty x-difficulty}
                         {:character "O" :play-type o-type :difficulty o-difficulty}]})


(describe "core"
  (with-stubs)

  (it "can tell what play-type of turn it is"
    (should= true (human? (state-create {:interface :tui :active-player-index 0 :x-type :human :o-type :human}))))

  (it "can tell what difficulty computer turn it is"
    (should= :hard (get-computer-difficulty
                     (state-create {:interface :tui :active-player-index 0 :x-type :computer :x-difficulty :hard :o-type :human}))))

  (it "changes the active player O"
    (with-out-str
      (should= (state-create {:active-player-index 1 :x-type :human :o-type :human})
               (change-player (state-create {:active-player-index 0 :x-type :human :o-type :human})))))

  (it "changes the active player X"
    (with-out-str
      (should= (state-create {:active-player-index 0 :x-type :human :o-type :human})
               (change-player (state-create {:active-player-index 1 :x-type :human :o-type :human})))))

  )
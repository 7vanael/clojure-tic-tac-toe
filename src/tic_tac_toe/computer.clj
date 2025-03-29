(ns tic-tac-toe.computer
  (:require [tic-tac-toe.board :as board]))

(defn winning-move-available [board moves character]
  (first (filter
           (fn [move] (board/winner? (assoc-in board move character) character))
           moves)))

(defn get-winning-move [{:keys [board active-player-index status players] :as state} moves]
  (let [character (get-in players [active-player-index :character])
        winning-move (winning-move-available board moves character)]
    (cond (= 1 (count moves)) (first moves)
          (not (nil? winning-move)) winning-move

          :else [-1 -1])))

(defn get-possible-moves [board]
  (vec (for [x (range (count board))
             y (range (count (first board)))
             :when (nil? (get-in board [x y]))]
         [x y])))

(defn turn [{:keys [board active-player-index status players] :as state}]
      (let [character (get-in state [:players (:active-player-index state) :character])
            next-play [1 1]]
        (assoc state :board (board/take-square board next-play character))))
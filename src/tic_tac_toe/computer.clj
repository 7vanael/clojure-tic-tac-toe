(ns tic-tac-toe.computer
  (:require [tic-tac-toe.board :as board]))

(defn eval-board [board depth comp-char opp-char]
  (cond (board/winner? board comp-char) (- 10 depth)
        (board/winner? board opp-char) (- depth 10)
        :else 0))

(defn get-possible-moves [board]
  (vec (for [x (range (count board))
             y (range (count board))
             :when (board/available? board [x y])]
         [x y])))

(defn game-over? [open-spaces board char opp-char]
  (or (empty? open-spaces)
      (board/winner? board char)
      (board/winner? board opp-char)))

(defn minimax [board char opp-char current-player depth]
  (let [open-spaces (get-possible-moves board)]
    (if (game-over? open-spaces board char opp-char)
      (eval-board board depth char opp-char)
      (let [outcomes (for [space open-spaces]
                       (minimax (assoc-in board space current-player)
                                char
                                opp-char
                                (if (= current-player char) opp-char char)
                                (inc depth)))
            function (if (= current-player char) max min)]
        (apply function outcomes)))))

(defn eval-moves [board char opp-char]
  (let [moves   (get-possible-moves board)
        results (for [move moves]
                  [move (minimax
                          (board/take-square board move char)
                          char
                          opp-char
                          opp-char
                          0)])]
    results))

(defn turn [{:keys [board active-player-index status players] :as state}]
  (let [character (get-in state [:players active-player-index :character])
        opp-char  (if (= "X" character) "O" "X")
        next-play (first (apply max-key second (eval-moves board character opp-char)))]
    (assoc state :board (board/take-square board next-play character))))
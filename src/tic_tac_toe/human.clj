(ns tic-tac-toe.human
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.turn :as turn]
            [tic-tac-toe.next-play :as next-play]))


(defmethod turn/take-turn :human [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (next-play/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char (get-in players [active-player-index :character])]
    (assoc state :board (board/take-square board next-play-coordinates player-char))))

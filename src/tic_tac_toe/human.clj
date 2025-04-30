(ns tic-tac-toe.human
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defmethod core/take-human-turn :tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (core/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char))
        (assoc :turn-phase :input-received))))

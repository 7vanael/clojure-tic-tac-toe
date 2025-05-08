(ns tic-tac-toe.tui.in-progress
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.console :as console]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]
            [tic-tac-toe.persistence :as persistence]))

#_(defn human-turn-tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char           (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char)))))

(defmethod core/take-human-turn :tui [{:keys [board active-player-index players] :as state}]
  (let [play-options          (board/play-options board)
        next-play             (console/get-next-play state play-options)
        next-play-coordinates (board/space->coordinates next-play board)
        player-char           (get-in players [active-player-index :character])]
    (-> state
        (assoc :board (board/take-square board next-play-coordinates player-char)))))

#_(defmethod core/update-state [:tui :in-progress] [state]
    (-> state
        core/take-turn
        board/evaluate-board
        core/change-player
        persistence/save-game
        ))

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(def board-options
  {:3x3 3, :4x4 4, :3x3x3 [3 3 3]})

(defn initialize-state [{:keys [interface]}]
  (let [player-x     (console/get-player-type "X" player-options)
        difficulty-x (when (= :computer player-x) (console/get-difficulty "X" difficulty-options))
        player-o     (console/get-player-type "O" player-options)
        difficulty-o (when (= :computer player-o) (console/get-difficulty "O" difficulty-options))
        board-size   (console/get-board-size board-options)]
    {:interface           interface
     :board               (board/new-board board-size)
     :active-player-index 0
     :status              :in-progress
     :players             [{:character "X" :play-type player-x :difficulty difficulty-x}
                           {:character "O" :play-type player-o :difficulty difficulty-o}]}))

(defmethod core/update-state [:tui :found-save] [state]
  (if (console/resume?)
    (assoc state :status :in-progress :interface :tui)
    (core/initialize-state state)))


(defn end-game [state]
  (persistence/delete-save)
  (if (console/play-again?)
    (enter-loop (core/initialize-state state))
    (System/exit 0)))

(defn enter-loop [state]
  (loop [state state]
    (console/display-board (:board state))
    (if (= :game-over (:status state))
      (end-game state)
      (recur (core/update-state state)))))

(defmethod core/start-game :tui [state]
  (console/welcome-message)
  (let [saved-game      (persistence/load-game)
        game (if (nil? saved-game)
               (initialize-state state)
               (core/update-state (assoc saved-game :status :found-save)))]
    (prn "game:" game)
    #_(loop [game game]
      (console/display-board (:board game))
      (recur (core/update-state game)))))
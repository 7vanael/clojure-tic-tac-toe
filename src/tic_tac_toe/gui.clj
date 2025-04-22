(ns tic-tac-toe.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.board :as board]))


(def screens
  {:config     0
   :play       1
   :play-again 2})

;Want a handle-click per screen?

(defn mouse-clicked [state event]
  #_(handle-click state))

(defn setup []
  {:current-screen      (:config screens)
   :board-size          nil
   :board               nil
   :active-player-index 0
   :status              "config"
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

(defn update-state [state]
  )

(defn draw-player-config [state]
  )

(defn draw-board-size [state]
  )

(defn draw-game [state]
  )

(defn draw-state [state]
  (case (:current-screen state)
    0 (draw-player-config state)
    1 (draw-board-size state)
    2 (draw-game state)))


(defn create-sketch []
  (q/defsketch tic-tac-toe
               :title "Tic-Tac-Toe"
               :size [500 500]
               :setup setup
               :update update-state
               :draw draw-state
               :mouse-clicked mouse-clicked
               :middleware [m/fun-mode]))
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
   :board               nil
   :active-player-index 0
   :status              "config"
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

;Can I work this into actually just being a call to game/play?
;I don't think so, that's already a loop. All I need is for the board to listen
;for a mouse-click on the human player's turn, then check the validity on the board
;then take it if it's valid and return the new state.
(defn update-state [state]
  )

(defn draw-player-config [state]
  )

(defn draw-board-size [state]
  )
(defn draw-play-again [state]
  )

;defmulti here? route to different draw functions
;based on status?
(defn draw-state [state]
  )


(defn create-sketch []
  (q/defsketch tic-tac-toe
               :title "Tic-Tac-Toe"
               :size [500 500]
               :setup setup
               :update update-state
               :draw draw-state
               :mouse-clicked mouse-clicked
               :middleware [m/fun-mode]))
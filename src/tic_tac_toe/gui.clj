(ns tic-tac-toe.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.user-prompt :as next-play]
            ))

(def width 36)
(def height 36)
#_(def x-img (q/load-image "resources/redX.jpg"))
#_(def o-img (q/load-image "resources/blackO.jpg"))

(def screens
  {:config     0
   :play       1
   :winner     2
   :draw       3
   :play-again 4})

;Want a handle-click per screen?

(defmulti mouse-clicked:status)
(defn mouse-clicked [state]
  state)
#_(defmethod mouse-clicked :player-1-selection [state event]
    (if (in-bounds?)
      (assoc-in state [:players 0 :play-type] :human))

    )

(defn setup []
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :config
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]})

;Can I work this into actually just being a call to game/play?
;I don't think so, that's already a loop. All I need is for the board to listen
;for a mouse-click on the human player's turn, then check the validity on the board
;then take it if it's valid and return the new state.

;(defmulti update-state :status [state])
(defn update-state [state]
  state)
#_(defmethod update-state :player-1-selection [state]
    (assoc state :status :player-2-selection))

#_(defmethod next-play/get-next-play :gui [state play-options]
    ;; GUI implementation here
    )

(defmulti display-board (fn [state & _] (:status state)))




(defmethod display-board :config [state board]
  (q/background 240)
  (let [cell-size (/ width (count board))]
    (doseq [i (range (inc width))]
      (q/line (* i cell-size) 0 (* i cell-size) (* cell-size height)))
    (doseq [i (range (inc width))]
      (q/line 0 (* i cell-size) (* cell-size width) (* i cell-size)))
    (doseq [row (range (count board))
            col (range (count board))]
      (let [cell-value (get-in board [row col])
            x-position (* col cell-size)
            y-position (* row cell-size)
            padding    (* cell-size 0.2)
            center-x   (+ x-position (/ cell-size 2))
            center-y   (+ y-position (/ cell-size 2))
            radius     (- (/ cell-size 2) padding)]
        (cond (= "X" cell-value) (do
                                   (q/stroke 255 0 0)
                                   (q/stroke-weight 3)
                                   (q/line (+ x-position padding) (+ y-position padding)
                                           (+ x-position cell-size (- padding)) (+ y-position cell-size (- padding)))
                                   (q/line (+ x-position padding) (+ y-position cell-size (- padding))
                                           (+ x-position cell-size (- padding)) (+ y-position padding))
                                   (q/stroke 0)
                                   (q/stroke-weight 1))
              (= "O" cell-value) (do
                                   (q/stroke 0)
                                   (q/stroke-weight 3)
                                   (q/no-fill)
                                   (q/ellipse center-x center-y (* radius 2) (* radius 2))
                                   (q/fill 0)
                                   (q/stroke-weight 1))
              :else nil)
        #_(cond (= "X" cell-value) (q/image x-img x-position y-position cell-size cell-size)
                (= "O" cell-value) (q/image o-img x-position y-position cell-size cell-size)
                :else nil)))))


(defn draw-player-config [state]
  )

(defn draw-board-size [state]
  )
(defn draw-play-again [state]
  )

(defmulti draw-player-setup (fn [state] (get-in state [:players :play-type])))

#_(defmethod draw-player-setup :computer [state]
    (draw-player-selection-screen state)
    )

#_(defmethod draw-player-setup :human [state] (assoc state :status :choose-board-size))

#_(defmethod draw-state :interface [state]
    (draw-player-setup state))
(defn draw-state [state]
  (draw-player-setup state))

(defn create-sketch [state]
  (q/defsketch tic-tac-toe
    :title "Tic-Tac-Toe"
    :size [500 500]
    :setup setup
    :update update-state
    :draw draw-state
    :mouse-clicked mouse-clicked
    :middleware [m/fun-mode]))

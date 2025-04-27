(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.user-prompt :as next-play]
            [tic-tac-toe.gui.multis :as multis]
            [tic-tac-toe.gui.config-players]
            [tic-tac-toe.gui.welcome]
            [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.gui.select-board]
            [tic-tac-toe.gui.game-over]))

#_(def x-img (ref nil))
#_(def o-img (ref-nil))


#_(def screens
    {:config     0
     :play       1
     :winner     2
     :draw       3
     :play-again 4})

;Want a handle-click per screen?

#_(dosync (q/ref-set x-img (q/load-image "resources/redX.jpg")))
#_(dosync (ref-set o-img (q/load-image "resources/blackO.jpg")))

(defn setup []
  {:interface           :gui
   :board               nil
   :active-player-index 0
   :status              :welcome
   :players             [{:character "X" :play-type nil :difficulty nil}
                         {:character "O" :play-type nil :difficulty nil}]
   :board-size          nil})

#_(defmethod next-play/get-next-play :gui [state play-options]
    ;; GUI implementation here
    )



#_(defmethod draw-state :in-progress [{:keys [board] :as state}]
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








(defn mouse-clicked-core [state event]
  (multis/mouse-clicked state event))

(defn draw-state-core [state]
  (multis/draw-state state))

(defn update-state-core [state]
  (multis/update-state state))

(defn create-sketch [state]
  (q/defsketch tic-tac-toe
    :title "Tic-Tac-Toe"
    :size [util/screen-size util/screen-size]
    :setup setup
    :update update-state-core
    :draw draw-state-core
    :mouse-clicked mouse-clicked-core
    :middleware [m/fun-mode]))

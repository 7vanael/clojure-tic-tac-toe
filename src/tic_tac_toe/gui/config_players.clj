(ns tic-tac-toe.gui.config-players
  (:require [tic-tac-toe.gui.gui-util :as util]
            [tic-tac-toe.core :as core]))

(defn human-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-2-rect))
(defn computer-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-2-rect))
(defn easy-clicked? [x y] (util/button-clicked? [x y] util/opt1-of-3-rect))
(defn medium-clicked? [x y] (util/button-clicked? [x y] util/opt2-of-3-rect))
(defn hard-clicked? [x y] (util/button-clicked? [x y] util/opt3-of-3-rect))

(defmethod core/mouse-clicked :config-x-type [state {:keys [x y]}]
  (cond (human-clicked? x y) (-> state
                                 (assoc-in [:players 0 :play-type] :human)
                                 (assoc :status :config-o-type))
        (computer-clicked? x y) (-> state
                                    (assoc-in [:players 0 :play-type] :computer)
                                    (assoc :status :config-x-difficulty))
        :else state))

(defmethod core/mouse-clicked :config-x-difficulty [state {:keys [x y]}]
  (cond (easy-clicked? x y) (-> state
                                (assoc-in [:players 0 :difficulty] :easy)
                                (assoc :status :config-o-type))
        (medium-clicked? x y) (-> state
                                  (assoc-in [:players 0 :difficulty] :medium)
                                  (assoc :status :config-o-type))
        (hard-clicked? x y) (-> state
                                (assoc-in [:players 0 :difficulty] :hard)
                                (assoc :status :config-o-type))
        :else state))

(defmethod core/mouse-clicked :config-o-type [state {:keys [x y]}]
  (cond (human-clicked? x y) (-> state
                                 (assoc-in [:players 1 :play-type] :human)
                                 (assoc :status :select-board))
        (computer-clicked? x y) (-> state
                                    (assoc-in [:players 1 :play-type] :computer)
                                    (assoc :status :config-o-difficulty))
        :else state))

(defmethod core/mouse-clicked :config-o-difficulty [state {:keys [x y]}]
  (cond (easy-clicked? x y) (-> state
                                (assoc-in [:players 1 :difficulty] :easy)
                                (assoc :status :select-board))
        (medium-clicked? x y) (-> state
                                  (assoc-in [:players 1 :difficulty] :medium)
                                  (assoc :status :select-board))
        (hard-clicked? x y) (-> state
                                (assoc-in [:players 1 :difficulty] :hard)
                                (assoc :status :select-board))
        :else state))
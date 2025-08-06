(ns tic-tac-toe.wrappers
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as dom]
            [c3kit.wire.js :as wjs]))

(def state (r/atom (core/initial-state {:interface :static :save :ratom :status :welcome})))
(def status-cursor (r/cursor state [:status]))

(defn play-again []
  (let [new-state (core/fresh-start @state)]
    (reset! state new-state)))

(defmethod core/take-human-turn :static [state]
  (core/do-take-human-turn state))

(defn maybe-take-computer-turn []
  ;;TODO clean this function?  are both whens doing the same thing? :
  (when (and (= :in-progress @status-cursor)
             (not (core/currently-human? @state)))
    (reset! state (core/play-turn! @state))
    (when (and (= :in-progress (:status @state))
               (not (core/currently-human? @state)))
      (js/setTimeout maybe-take-computer-turn 200))))

(defn configure-board-size [option]
  (let [current-state (assoc @state :response option)
        new-state     (core/select-board current-state)]
    (reset! state new-state)
    (reset! status-cursor :in-progress)
    (js/setTimeout maybe-take-computer-turn 10)))

(defn configure-o-difficulty [option]
  (let [current-state (assoc @state :response option)
        new-state     (core/config-o-difficulty current-state)]
    (reset! state new-state)))

(defn configure-x-difficulty [option]
  (let [current-state (assoc @state :response option)
        new-state     (core/config-x-difficulty current-state)]
    (reset! state new-state)))

(defn configure-o-type [option]
  (let [current-state (assoc @state :response option)
        new-state     (core/config-o-type current-state)]
    (reset! state new-state)))

(defn configure-x-type [option]
  (let [current-state (assoc @state :response option)
        new-state     (core/config-x-type current-state)]
    (reset! state new-state)))

(defn make-move [value]
  (let [current-state (assoc @state :response value)
        new-state     (dissoc (core/play-turn! current-state) :response)]
    (reset! state new-state)
    (maybe-take-computer-turn)))

(ns tic-tac-toe.wrappers
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as dom]
            [c3kit.wire.js :as wjs]))

(def state (r/atom (core/initial-state {:interface :static :save :ratom :status :welcome})))
(def status-cursor (r/cursor state [:status]))

(defn play-again []
    (swap! state #(core/fresh-start @state)))

(defmethod core/take-human-turn :static [state]
  (core/do-take-human-turn state))

(defn computer-turn? [state]
  (and (= :in-progress (:status state))
       (not (core/currently-human? state))))

(defn maybe-take-computer-turn []
  (when (computer-turn? @state)
    (swap! state #(core/play-turn! @state))
    (js/setTimeout maybe-take-computer-turn 20)))

(defn configure-board-size [option]
  (let [current-state (assoc @state :response option)]
    (swap! state #(core/select-board current-state))
    (js/setTimeout maybe-take-computer-turn 10)))

(defn configure-o-difficulty [option]
  (let [current-state (assoc @state :response option)]
    (swap! state #(core/config-o-difficulty current-state))))

(defn configure-x-difficulty [option]
  (let [current-state (assoc @state :response option)]
    (swap! state #(core/config-x-difficulty current-state))))

(defn configure-o-type [option]
  (let [current-state (assoc @state :response option)]
    (swap! state #(core/config-o-type current-state))))

(defn configure-x-type [option]
  (let [current-state (assoc @state :response option)]
    (swap! state #(core/config-x-type current-state))))

(defn make-move [value]
  (let [current-state (assoc @state :response value)]
    (swap! state #(dissoc (core/play-turn! current-state) :response))
    (maybe-take-computer-turn)))

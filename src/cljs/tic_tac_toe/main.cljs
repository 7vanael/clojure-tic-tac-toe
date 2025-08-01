(ns tic-tac-toe.main
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as dom]
            [reagent.dom.client :as rdomc]
            [c3kit.wire.js :as wjs]))

(defonce state (r/atom (core/initial-state {:interface :static :save :ratom :status :welcome})))
(def status-cursor (r/cursor state [:status]))

(defmethod core/save-game :ratom [state] state)
(defmethod core/load-game :ratom [state] state)
(defmethod core/delete-save :ratom [state] state)

(defn draw-config-x-type []
  [:div.config-x-type
   [:h2 "Choose X Player Type"]
   [:div.options
    (for [option core/player-options]
      ^{:key option}
      [:button.option
       {:class (when (= (:response state) option) "selected")
        :on-click #(swap! state assoc :response option)}
       (name option)])]
   [:button.action-button
    {:disabled (not (:response state))
     :on-click #(handle-input (:response @state))}
    "Next"]])


(defn draw-welcome []
  [:div.welcome
   [:h1 {:id "welcome"} "Welcome to Tic-Tac-Toe!"]
   [:button.action-button {:on-click #(reset! status-cursor :config-x-type)} "Start Game"]])

(defn game-component []
  [:div.tic-tac-toe-app
   (if (= @status-cursor :welcome)
     (draw-welcome)
     (draw-config-x-type))])

(defn ^:export init []
  #_(reset! state (core/initial-state {:interface :static :save :ratom}))
  (dom/render [game-component] (wjs/element-by-id "app")))

;(dom/render [layout] (.getElementById js/document "app-root"))





;
;(def time-cursor (r/cursor state [:time]))
;
;(defn small-component []
;  (let [time @time-cursor]
;    [:h3 "time:" time]))
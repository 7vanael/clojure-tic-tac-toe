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

(defn configure-board-size [starting-option]
  (let [option (first starting-option)
        current-state (assoc @state :response option)
        new-state (core/select-board current-state)]
    (reset! state new-state)))

(defn configure-o-difficulty [option]
  (let [current-state (assoc @state :response option)
        new-state (core/config-o-difficulty current-state)]
    (reset! state new-state)))

(defn configure-x-difficulty [option]
  (let [current-state (assoc @state :response option)
        new-state (core/config-x-difficulty current-state)]
    (reset! state new-state)))

(defn configure-o-type [option]
  (let [current-state (assoc @state :response option)
        new-state (core/config-o-type current-state)]
    (reset! state new-state)))

(defn configure-x-type [option]
  (let [current-state (assoc @state :response option)
        new-state (core/config-x-type current-state)]
    (reset! state new-state)))

(defn draw-select-board []
  [:div.select-board
   [:h2 "Select Board Size"]
   [:div.options
    (for [[option-key option-value] (take 2 core/difficulty-options)]
      [:button.option
       {:class [option-key :board-option]
        :on-click (configure-board-size option-value)}
       (name option-key)])]])

(defn draw-config-o-difficulty []
  [:div.config-o-difficulty
   [:h2 "Choose O's Difficulty"]
   [:div.options
    (for [option core/difficulty-options]
      [:button.option
       {:class [option :o-difficulty]
        :on-click (configure-o-difficulty option)}
       (name option)])]])

(defn draw-config-x-difficulty []
  [:div.config-x-difficulty
   [:h2 "Choose X's Difficulty"]
   [:div.options
    (for [option core/difficulty-options]
      [:button.option
       {:class [option :x-difficulty]
        :on-click (configure-x-difficulty option)}
       (name option)])]])

(defn draw-config-o-type []
  [:div.config-o-type
   [:h2 "Choose O Player Type"]
   [:div.options
    (for [option core/player-options]
      [:button.option
       {:class [option :o-type]
        :on-click (configure-o-type option)}
       (name option)])]])

(defn draw-config-x-type []
  [:div.config-x-type
   [:h2 "Choose X Player Type"]
   [:div.options
    (for [option core/player-options]
      [:button.option
       {:class [option :x-type]
        :on-click (configure-x-type option)}
       (name option)])]])


(defn draw-welcome []
  [:div.welcome
   [:h1 {:id "welcome"} "Welcome to Tic-Tac-Toe!"]
   [:button.action-button {:on-click #(reset! status-cursor :config-x-type)} "Start Game"]])

(defn game-component []
  [:div.tic-tac-toe-app
   (cond (= @status-cursor :welcome) (draw-welcome)
         (= @status-cursor :config-x-type)(draw-config-x-type)
         (= @status-cursor :config-o-type) (draw-config-o-type)
         (= @status-cursor :config-x-difficulty)(draw-config-x-difficulty)
         (= @status-cursor :config-o-difficulty) (draw-config-o-difficulty)
         (= @status-cursor :select-board) (draw-select-board)
         )])

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
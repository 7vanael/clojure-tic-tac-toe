(ns tic-tac-toe.main
  (:require [tic-tac-toe.core :as core]
            [reagent.core :as r]
            [reagent.dom :as dom]
            [c3kit.wire.js :as wjs]
            [tic-tac-toe.computer.hard]
            [tic-tac-toe.computer.easy]
            [tic-tac-toe.computer.medium]))

(def state (r/atom (core/initial-state {:interface :static :save :ratom :status :welcome})))
(def status-cursor (r/cursor state [:status]))

(defmethod core/save-game :ratom [state] state)
(defmethod core/load-game :ratom [state] state)
(defmethod core/delete-save :ratom [state] state)

(defn play-again []
  (let [new-state (core/fresh-start @state)]
    (reset! state new-state)))

(defmethod core/take-human-turn :static [state]
  (core/do-take-human-turn state))

(defn maybe-take-computer-turn []
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

(defn render-cell [cell cell-index is-active? row-index]
  (let [cell-number (+ (* (count (:board @state)) (dec row-index)) cell-index)
        key         (str "cell-" cell-number)
        class       (if (or (= cell "X") (= cell "O")) "occupied" "empty")]
    [:td {:class class :key key}
     [:button.move-button {:id       key
                           :disabled (not is-active?)
                           :on-click #(make-move cell)}
      cell]]))

(defn render-row [row row-index is-active?]
  [:tr {:key row-index} (doall (map-indexed #(render-cell %2 (inc %1) is-active? row-index) row))])


(defn render-board-table []
  (let [is-active? (and (= :in-progress @status-cursor) (core/currently-human? @state))
        board      (:board @state)]
    [:table [:tbody (doall (map-indexed #(render-row %2 (inc %1) is-active?) board))]]))

(defn render-game-announcement [{:keys [status active-player-index players]}]
  (let [current-char (get-in players [active-player-index :character])]
    (case status
      :in-progress [:h2.current-player (str "Player " current-char "'s turn")]
      :tie [:h2.game-over "It's a tie! Good game"]
      :winner [:h2.game-over (str "Player " current-char " wins! Good game")])))

(defn draw-end []
  [:div.game-over
   (render-game-announcement @state)
   (render-board-table)
   [:button.action-button {:id "new-game" :class "new-game" :on-click #(play-again)}
    "Play Again?"]])

(defn draw-board []
  [:div.in-progress
   (render-game-announcement @state)
   (render-board-table)])

(defn draw-select-board []
  [:div.select-board
   [:h2 "Select Board Size"]
   [:div.options
    [:button.action-button
     {:id       "board-3x3"
      :class    ["board-3x3" :board-option]
      :on-click #(configure-board-size 3)}
     "3 x 3"]
    [:button.action-button
     {:id       "board-4x4"
      :class    ["board-4x4" :board-option ]
      :on-click #(configure-board-size 4)}
     "4 x 4"]]])

(defn draw-config-o-difficulty []
  [:div.config-o-difficulty
   [:h2 "Choose O's Difficulty"]
   [:div.options
    (doall
      (for [option core/difficulty-options] ^{:key (name option)}
                                            [:button.action-button
                                             {:id       (name option)
                                              :class    [option :o-difficulty]
                                              :on-click #(configure-o-difficulty option)}
                                             (name option)]))]])

(defn draw-config-x-difficulty []
  [:div.config-x-difficulty
   [:h2 "Choose X's Difficulty"]
   [:div.options
    (doall
      (for [option core/difficulty-options] ^{:key (name option)}
                                            [:button.action-button
                                             {:id       (name option)
                                              :class    [option :x-difficulty]
                                              :on-click #(configure-x-difficulty option)}
                                             (name option)]))]])

(defn draw-config-o-type []
  [:div.config-o-type
   [:h2 "Choose O Player Type"]
   [:div.options
    (do
      (for [option core/player-options] ^{:key (name option)}
                                        [:button.action-button
                                         {:id       (name option)
                                          :class    [option :o-type]
                                          :on-click #(configure-o-type option)}
                                         (name option)]))]])

(defn draw-config-x-type []
  [:div.config-x-type
   [:h2 "Choose X Player Type"]
   [:div.options
    (do
      (for [option core/player-options] ^{:key (name option)}
                                        [:button.action-button
                                         {:id       (name option)
                                          :class    [option :x-type]
                                          :on-click #(configure-x-type option)}
                                         (name option)]))]])


(defn draw-welcome []
  [:div.welcome
   [:h1 {:id "welcome"} "Welcome to Tic-Tac-Toe!"]
   [:button.action-button {:on-click #(reset! status-cursor :config-x-type)} "Start Game"]])

(defn game-component []
  [:div.tic-tac-toe-app
   (cond (= @status-cursor :welcome) (draw-welcome)
         (= @status-cursor :config-x-type) (draw-config-x-type)
         (= @status-cursor :config-o-type) (draw-config-o-type)
         (= @status-cursor :config-x-difficulty) (draw-config-x-difficulty)
         (= @status-cursor :config-o-difficulty) (draw-config-o-difficulty)
         (= @status-cursor :select-board) (draw-select-board)
         (= @status-cursor :in-progress) (draw-board)
         (= @status-cursor :tie) (draw-end)
         (= @status-cursor :winner) (draw-end))])

(defn ^:export init []
  (dom/render [game-component] (wjs/element-by-id "app")))




;
;(def time-cursor (r/cursor state [:time]))
;
;(defn small-component []
;  (let [time @time-cursor]
;    [:h3 "time:" time]))
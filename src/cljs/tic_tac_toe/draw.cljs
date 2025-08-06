(ns tic-tac-toe.draw
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.main :as m]))


(defn render-cell [cell cell-index is-active? row-index]
  (let [cell-number (+ (* (count (:board @m/state)) (dec row-index)) cell-index)
        key         (str "cell-" cell-number)
        class       (if (or (= cell "X") (= cell "O")) "occupied" "empty")]
    [:td {:class class :key key}
     [:button.move-button {:id       key
                           :disabled (not is-active?)
                           :on-click #(m/make-move cell)}
      cell]]))

(defn render-row [row row-index is-active?]
  [:tr {:key row-index} (doall (map-indexed #(render-cell %2 (inc %1) is-active? row-index) row))])

(defn render-board-table []
  (let [is-active? (and (= :in-progress @m/status-cursor) (core/currently-human? @m/state))
        board      (:board @m/state)]
    [:table [:tbody (doall (map-indexed #(render-row %2 (inc %1) is-active?) board))]]))

(defn render-game-announcement [{:keys [status active-player-index players]}]
  (let [current-char (get-in players [active-player-index :character])]
    (case status
      :in-progress [:h2.current-player (str "Player " current-char "'s turn")]
      :tie [:h2.game-over "It's a tie! Good game"]
      :winner [:h2.game-over (str "Player " current-char " wins! Good game")])))

(defn draw-end []
  [:div.game-over
   (render-game-announcement @m/state)
   (render-board-table)
   [:button.action-button {:id "new-game" :class "new-game" :on-click #(m/play-again)}
    "Play Again?"]])

(defmethod core/draw-state [:static :winner] [_] (draw-end))

(defmethod core/draw-state [:static :tie] [_] (draw-end))

(defmethod core/draw-state [:static :in-progress] [_]
  [:div.in-progress
   (render-game-announcement @m/state)
   (render-board-table)])

(defmethod core/draw-state [:static :select-board] [_]
  [:div.select-board
   [:h2 "Select Board Size"]
   [:div.options
    [:button.action-button
     {:id       "board-3x3"
      :class    ["board-3x3" :board-option]
      :on-click #(m/configure-board-size 3)}
     "3 x 3"]
    [:button.action-button
     {:id       "board-4x4"
      :class    ["board-4x4" :board-option ]
      :on-click #(m/configure-board-size 4)}
     "4 x 4"]]])

(defmethod core/draw-state [:static :config-o-difficulty] [_]
  [:div.config-o-difficulty
   [:h2 "Choose O's Difficulty"]
   [:div.options
    (doall
      (for [option core/difficulty-options]
        ^{:key (name option)}
                                            [:button.action-button
                                             {:id       (name option)
                                              :class    [option :o-difficulty]
                                              :on-click #(m/configure-o-difficulty option)}
                                             (name option)]))]])

(defmethod core/draw-state [:static :config-x-difficulty] [_]
  [:div.config-x-difficulty
   [:h2 "Choose X's Difficulty"]
   [:div.options
    (doall
      (for [option core/difficulty-options] ^{:key (name option)}
                                            [:button.action-button
                                             {:id       (name option)
                                              :class    [option :x-difficulty]
                                              :on-click #(m/configure-x-difficulty option)}
                                             (name option)]))]])

(defmethod core/draw-state [:static :config-o-type] [_]
  [:div.config-o-type
   [:h2 "Choose O Player Type"]
   [:div.options
    (do
      (for [option core/player-options] ^{:key (name option)}
                                        [:button.action-button
                                         {:id       (name option)
                                          :class    [option :o-type]
                                          :on-click #(m/configure-o-type option)}
                                         (name option)]))]])

(defmethod core/draw-state [:static :config-x-type] [_]
  [:div.config-x-type
   [:h2 "Choose X Player Type"]
   [:div.options
    (do
      (for [option core/player-options] ^{:key (name option)}
                                        [:button.action-button
                                         {:id       (name option)
                                          :class    [option :x-type]
                                          :on-click #(m/configure-x-type option)}
                                         (name option)]))]])


(defmethod core/draw-state [:static :welcome] [_]
  [:div.welcome
   [:h1 {:id "welcome"} "Welcome to Tic-Tac-Toe!"]
   [:button.action-button {:on-click #(reset! m/status-cursor :config-x-type)} "Start Game"]])
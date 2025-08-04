(ns tic-tac-toe.hiccup
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(defmethod core/draw-state [:static :welcome] [state]
  [:div.welcome
   [:h1 {:id "welcome"} "Welcome to Tic-Tac-Toe!"]
   [:button.action-button {:on-click #(core/start-game (assoc state :result :start))} "Start Game"]])

(defmethod core/draw-state [:static :found-save] [state]
  [:div.found-save
   [:h2 "Found saved game!"]
   [:button.action-button {:on-click #(handle-input :load)} "Resume"]
   [:button.action-button {:on-click #(handle-input :fresh)} "New Game"]])

(defmethod core/draw-state [:static :config-x-type] [state]
  [:div.config
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

(defmethod core/draw-state [:static :config-x-difficulty] [state]
  [:div.config
   [:h2 "Configure Player X Difficulty"]
   [:p "Choose difficulty:"]
   (for [option core/difficulty-options]
     ^{:key option}
     [:button.option
      {:on-click #(swap! state assoc :response option)}
      (name option)])
   [:button.confirm
    {:disabled (not (:response state))
     :on-click #(swap! state core/config-x-difficulty)}
    "Confirm"]])

(defmethod core/draw-state [:static :config-o-type] [state]
  [:div.config
   [:h2 "Configure Player O"]
   [:p "Choose player type:"]
   (for [option core/player-options]
     ^{:key option}
     [:button.option
      {:on-click #(swap! state assoc :response option)}
      (name option)])
   [:button.confirm
    {:disabled (not (:response state))
     :on-click #(swap! state core/config-o-type)}
    "Confirm"]])

(defmethod core/draw-state [:static :config-o-difficulty] [state]
  [:div.config
   [:h2 "Configure Player O Difficulty"]
   [:p "Choose difficulty:"]
   (for [option core/difficulty-options]
     ^{:key option}
     [:button.option
      {:on-click #(swap! state assoc :response option)}
      (name option)])
   [:button.confirm
    {:disabled (not (:response state))
     :on-click #(swap! state core/config-o-difficulty)}
    "Confirm"]])

(defmethod core/draw-state [:static :select-board] [state]
  [:div.config
   [:h2 "Select Board Size"]
   [:p "Choose board size:"]
   (for [[label size] core/board-options]
     ^{:key label}
     [:button.option
      {:on-click #(swap! state assoc :response size)}
      (name label)])
   [:button.confirm
    {:disabled (not (:response state))
     :on-click #(swap! state core/select-board)}
    "Confirm"]])

(defn render-board-cell [state row col]
  (let [{:keys [board players active-player-index]} state
        cell-value (board/get-square board [row col])
        is-empty? (nil? cell-value)
        space-number (+ (* row (board/board-size board)) col)]
    [:button.cell
     {:class (when-not is-empty? "occupied")
      :disabled (or (not is-empty?) (core/game-over? state) (not (core/currently-human? state)))
      :on-click #(when (and is-empty? (core/currently-human? state))
                   (handle-input space-number))}
     (or cell-value space-number)]))

(defn render-board [state]
  (let [{:keys [board]} state
        size (board/board-size board)]
    [:div.board
     (for [row (range size)]
       ^{:key row}
       [:div.board-row
        (for [col (range size)]
          ^{:key [row col]}
          [render-board-cell state row col])])]))

(defmethod core/draw-state [:static :in-progress] [state]
  (let [{:keys [players active-player-index status]} state
        current-player (get-in players [active-player-index :character])
        player-type (core/active-player-type state)]
    [:div.game
     (if (core/currently-human? state)
       [:div.current-player (str "Player " current-player "'s turn")]
       [:div.current-player (str "Player " current-player " (Computer) thinking...")])
     [render-board state]
     [:div.game-actions
      [:button.action-button {:on-click #(reset! state (core/fresh-start @state))} "New Game"]]]))

(defmethod core/draw-state [:static :winner] [state]
  [:div.game-over-screen
   [:div.game-over (str "Player " (board/winner (:board state)) " wins!")]
   [render-board state]
   [:div.game-actions
    [:div.play-again
     [:p "Play again?"]
     [:button.option
      {:class (when (= (:response state) :play-again) "selected")
       :on-click #(swap! state assoc :response :play-again)} "Yes"]
     [:button.option
      {:class (when (= (:response state) :exit) "selected")
       :on-click #(swap! state assoc :response :exit)} "No"]]
    [:button.action-button
     {:disabled (nil? (:response state))
      :on-click #(handle-input (:response @state))}
     "Confirm"]]])

(defmethod core/draw-state [:static :tie] [state]
  [:div.game-over-screen
   [:div.game-over "It's a tie!"]
   [render-board state]
   [:div.game-actions
    [:div.play-again
     [:p "Play again?"]
     [:button.option
      {:class (when (= (:response state) :play-again) "selected")
       :on-click #(swap! state assoc :response :play-again)} "Yes"]
     [:button.option
      {:class (when (= (:response state) :exit) "selected")
       :on-click #(swap! state assoc :response :exit)} "No"]]
    [:button.action-button
     {:disabled (nil? (:response state))
      :on-click #(handle-input (:response @state))}
     "Confirm"]]])
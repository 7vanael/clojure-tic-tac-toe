(ns tic-tac-toe.tui.console
  (:require [clojure.string :as str]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.game :as game]))

(defmethod core/welcome-message :tui [_]
  (println "Welcome to tic-tac-toe!"))

(defn horizontal-line [width]
  (let [spacer (apply str (repeat 4 "-"))]
    (str "\n" (str/join "|" (repeat width spacer)) "\n")))

(defn add-space [space]
  (let [content (str space)]
    (if (= 1 (count content))
      (str " " content)
      content)))

(defn row-string [row]
  (let [row (str " " (str/join " | " (map add-space row)))]
    (str row " \n")))

(defn process-board [board]
  (apply str (map row-string board)))

(defmethod core/display-board :tui [_ board]
  (println (str/join (horizontal-line (count board))
                     (str/split-lines (process-board board)))))

(defn validate-number [play-options input]
  (contains? (set play-options) input))

(defn print-number-prompt []
  (println "Please enter the number for the space you'd like to take"))

(defmethod core/get-next-play :tui [state play-options]
  (core/announce-player state)
  (print-number-prompt)
  (let [input (read-string (read-line))]
    (if (validate-number play-options input)
      input
      (core/get-next-play state play-options))))

(defn invalid-selection []
  (println "That isn't a valid play, please try again"))

(defmethod core/announce-player :tui [{:keys [active-player-index players]}]
  (let [character (get-in players [active-player-index :character])]
    (println (str "Player " character "'s turn"))))

(defmethod core/announce-draw :tui [_]
  (println "It's a draw! Good game!"))

(defmethod core/announce-winner :tui [_ character]
  (println (str (str/capitalize character) " wins! Good game!")))

(defn display-play-type-options [character options]
  (println "Who will play " character "?")
  (run! println (map name options)))

(defn validate-selection [options selection]
  (contains? (set options) (keyword selection)))

(defn get-selection [character options]
  (let [input (keyword (read-string (read-line)))]
    (if (validate-selection options input)
      input
      (get-selection character options))))

(defmethod core/get-player-type :tui [_ character options]
  (display-play-type-options character options)
  (get-selection character options))

(defn play-again-prompt []
  (println "Would you like to play again? (y/n)"))

(def valid-yes-no-responses
  #{"yes" "y" "no" "n"})

(defn validate-play-again [input]
  (contains? valid-yes-no-responses input))

(defn get-play-again-selection []
  (play-again-prompt)
  (let [input (str/trim (str/lower-case (read-line)))]
    (if (validate-play-again input)
      input
      (get-play-again-selection))))

(defmethod core/play-again? :tui [_]
  (str/includes? (get-play-again-selection) "y"))

(defn format-size-option-display [size]
  (str size ") " size "x" size))

(defn board-size-prompt [size-options]
  (println "What size board do you want to play on?")
  (doseq [size size-options]
    (println (format-size-option-display size))))

(defmethod core/get-board-size :tui [_ size-options]
  (board-size-prompt size-options)
  (let [input (read-line)
        size-selection (try (read-string input)
                            (catch Exception _
                              nil))]
    (if (some #{size-selection} size-options)
      size-selection
      (core/get-board-size {:interface :tui} size-options))))

(defn display-difficulty-options [char options]
  (println "What difficulty setting should" char "use?")
  (run! println (map name options)))

(defmethod core/get-difficulty :tui [_ character options]
  (display-difficulty-options character options)
  (get-selection character options))

(defn initialize-state [{:keys [type-x type-o difficulty-x difficulty-o board-size interface]}]
  {:interface           interface
   :board               (board/new-board board-size)
   :active-player-index 1
   :status              :in-progress
   :players             [{:character "X" :play-type type-x :difficulty difficulty-x}
                         {:character "O" :play-type type-o :difficulty difficulty-o}]})

(def player-options
  [:human :computer])

(def difficulty-options
  [:easy :medium :hard])

(defmethod core/start-game :tui [state]
  (core/welcome-message state)
  (let [player-x      (core/get-player-type state "X" player-options)
        difficulty-x  (when (= :computer player-x) (core/get-difficulty state "X" difficulty-options))
        player-o      (core/get-player-type state "O" player-options)
        difficulty-o  (when (= :computer player-o) (core/get-difficulty state "O" difficulty-options))
        board-size    (core/get-board-size state [3 4])
        configuration {:type-x     player-x :type-o player-o :difficulty-x difficulty-x :difficulty-o difficulty-o
                       :board-size board-size :interface :tui}]
    (game/start (initialize-state configuration))
    (when (core/play-again? state) (recur state))))
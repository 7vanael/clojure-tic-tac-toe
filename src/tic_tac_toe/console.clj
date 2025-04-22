(ns tic-tac-toe.console
  (:require [clojure.string :as str]
            [tic-tac-toe.user-prompt :as user-prompt]))

(defmethod user-prompt/welcome-message :tui [_]
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

(defmethod user-prompt/display-board :tui [_ board]
  (println (str/join (horizontal-line (count board))
                     (str/split-lines (process-board board)))))

(defn validate-number [play-options input]
  (contains? (set play-options) input))

(defn print-number-prompt []
  (println "Please enter the number for the space you'd like to take"))

(defmethod user-prompt/get-next-play :tui [state play-options]
  (print-number-prompt)
  (let [input (read-string (read-line))]
    (if (validate-number play-options input)
      input
      (user-prompt/get-next-play state play-options))))

(defn invalid-selection []
  (println "That isn't a valid play, please try again"))

(defmethod user-prompt/announce-player :tui [_ character]
  (println (str "Player " character "'s turn")))

(defmethod user-prompt/announce-draw :tui [_]
  (println "It's a draw! Good game!"))

(defmethod user-prompt/announce-winner :tui [_ character]
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

(defmethod user-prompt/get-player-type :tui [_ character options]
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

(defmethod user-prompt/play-again? :tui [_]
  (str/includes? (get-play-again-selection) "y"))

(defn format-size-option-display [size]
  (str size ") " size "x" size))

(defn board-size-prompt [size-options]
  (println "What size board do you want to play on?")
  (doseq [size size-options]
    (println (format-size-option-display size))))

(defmethod user-prompt/get-board-size :tui [_ size-options]
  (board-size-prompt size-options)
  (let [size-selection (read-string (read-line))]
    (if (some #{size-selection} size-options)
      size-selection
      (user-prompt/get-board-size {:interface :tui} size-options))))

(defn display-difficulty-options [char options]
  (println "What difficulty setting should" char "use?")
  (run! println (map name options)))

(defmethod user-prompt/get-difficulty :tui [_ character options]
  (display-difficulty-options character options)
  (get-selection character options))
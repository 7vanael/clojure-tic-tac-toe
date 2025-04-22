(ns tic-tac-toe.console
  (:require [clojure.string :as str]
            [tic-tac-toe.next-play :as next-play]))

(defn welcome []
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

(defn display-board [board]
  (println (str/join (horizontal-line (count board))
                     (str/split-lines (process-board board)))))

(defn validate-number [play-options input]
  (contains? (set play-options) input))

(defn print-number-prompt []
  (println "Please enter the number for the space you'd like to take"))

(defmethod next-play/get-next-play :tui [state play-options]
  (print-number-prompt)
  (let [input (read-string (read-line))]
    (if (validate-number play-options input)
      input
      (next-play/get-next-play state play-options))))

(defn invalid-selection []
  (println "That isn't a valid play, please try again"))

(defn announce-player [character]
  (println (str "Player " character "'s turn")))

(defn draw []
  (println "It's a draw! Good game!"))

(defn announce-winner [character]
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

(defn get-player-type [character options]
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

(defn play-again? []
  (str/includes? (get-play-again-selection) "y"))

(defn format-size-option-display [size]
  (str size ") " size "x" size))

(defn board-size-prompt [size-options]
  (println "What size board do you want to play on?")
  (doseq [size size-options]
    (println (format-size-option-display size))))

(defn get-board-size [size-options]
  (board-size-prompt size-options)
  (let [size-selection (read-string (read-line))]
    (if (some #{size-selection} size-options)
      size-selection
      (get-board-size size-options))))

(defn display-difficulty-options [char options]
  (println "What difficulty setting should" char "use?")
  (run! println (map name options)))

(defn get-difficulty [character options]
  (display-difficulty-options character options)
  (get-selection character options))
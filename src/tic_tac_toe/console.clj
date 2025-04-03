(ns tic-tac-toe.console
  (:require [clojure.string :as str]))

(defn welcome []
  (println "Welcome to tic-tac-toe!"))

(def horizontal-line
  "\n--|---|---\n")

(defn row-string [row]
  (let [row (str (str/join " | " row))]
    (str row "\n")))

(defn replace-nils [board]
  (map (fn [row] (map #(if (nil? %) " " %) row)) board))

(defn process-board [board]
  (apply str (map row-string (replace-nils board))))

(defn display-board [board]
  (println (str/join horizontal-line (str/split-lines (process-board board)))))

(defn validate-number [n]
  (and (number? n) (< n 4) (> n 0)))

(defn print-number-prompt [message]
  (println (str "Which " message " do you want to play in?"))
  (println "Please enter a number (1-3)"))

(defn get-input [message]
  (print-number-prompt message)
  (let [input (read-string (read-line))]
    (if (validate-number input)
      (- input 1)
      (get-input message))))

(defn get-next-play []
  (let [row (get-input "row")
        col (get-input "column")
        position [row col]]
    position))

(defn occupied []
  (println "That isn't a valid play, please try again"))

(defn announce-player [character]
  (println (str "Player " character "'s turn")))

(defn draw []
  (println "It's a draw! Good game!"))

(defn announce-winner [character]
  (println (str (str/capitalize character) " wins! Good game!")))

(defn display-options [character options]
  (println "Who will play " character "?")
  (run! println (map name options)))

(defn validate-selection [options selection]
  (contains? (set options) (keyword selection)))

(defn get-selection [character options]
  (display-options character options)
    (let [input (keyword (read-string (read-line)))]
      (if (validate-selection options input)
        input
        (get-selection character options))))

(defn get-player-type [character options]
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
  (str size  ") " size "x" size))

(defn board-size-prompt [size-options]
  (println "What size board do you want to play on?")
  (doseq [size size-options]
    (println (format-size-option-display size))))

(defn get-board-size [size-options]
  (board-size-prompt size-options)
  (let [size-selection (read-string (read-line))]
    (if (some #{size-selection} size-options)
      size-selection
      (get-board-size size-options)
    ))
  )
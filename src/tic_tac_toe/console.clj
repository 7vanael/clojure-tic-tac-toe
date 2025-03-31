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
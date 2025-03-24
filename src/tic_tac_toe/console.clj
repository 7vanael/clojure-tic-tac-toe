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

(defn get-input []
  (println "Please enter a number (1-3)")
  (let [input (read-string (read-line))]
    (if (validate-number input)
      (- input 1)
      (get-input))))

(defn get-row []
  (println "Which row do you want to play in?")
  (get-input))

(defn get-column []
  (println "Which column do you want to play in?")
  (get-input))


(defn get-next-play []
  (let [row (get-row)
        col (get-column)
        position [row col]]
    position))

(defn occupied [state]
  (println "That isn't a valid play, please try again")
  state)

(defn announce-player [character]
  (println (str "Player " character "'s turn"))
  character)

(defn draw []
  (println "It's a draw! Good game!"))

(defn announce-winner [character]
  (println (str (str/capitalize character) " wins! Good game!")))
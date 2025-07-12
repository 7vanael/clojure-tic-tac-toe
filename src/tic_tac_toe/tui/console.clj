(ns tic-tac-toe.tui.console
  (:require [clojure.string :as str]
            [tic-tac-toe.core :as core]))

(defn welcome-message []
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

(defn process-2d-board [board]
  (apply str (map row-string board)))

(defn display-2d-board [board]
  (println)
  (println (str/join (horizontal-line (count board))
                     (str/split-lines (process-2d-board board))))
  (println))

(defn format-layer [idx layer size]
  (str "\nLayer " (inc idx) ":\n"
       (str/join (horizontal-line size)
                 (str/split-lines (process-2d-board layer)))
       "\n"))

(defn display-3d-board [board]
  (let [size (count board)]
    (println)
    (println (apply str (map-indexed #(format-layer %1 %2 size) board)))
    (println)))

(defn display-board [board]
  (if (vector? (get-in board [0 0] nil))
    (display-3d-board board)
    (display-2d-board board)))

(defn validate-number [play-options input]
  (contains? (set play-options) input))

(defn print-number-prompt []
  (println "Please enter the number for the space you'd like to take"))

(defn announce-player [{:keys [active-player-index players] :as state}]
  (let [character (get-in players [active-player-index :character])]
    (println (str "Player " character "'s turn"))
    state))

(defn get-next-play [state play-options]
  (announce-player state)
  (print-number-prompt)
  (let [input (read-string (read-line))]
    (if (validate-number play-options input)
      input
      (recur state play-options))))

(defn invalid-selection []
  (println "That isn't a valid play, please try again"))

(defn announce-winner [character]
  (println (str (str/capitalize character) " wins! Good game!")))

(defn announce-draw []
  (println "It's a draw! Good game!"))

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

(defn save-found-prompt []
  (println "A saved game was found, would you like to resume it? (y/n)"))

(defn play-again-prompt []
  (println "Would you like to play again? (y/n)"))

(def valid-yes-no-responses
  #{"yes" "y" "no" "n"})

(defn validate-yes-no-entry [input]
  (contains? valid-yes-no-responses input))

(defn get-yes-no-response []
  (let [input (str/trim (str/lower-case (read-line)))]
    (if (validate-yes-no-entry input)
      input
      (get-yes-no-response))))

(defn yes-or-no? []
  (str/includes? (get-yes-no-response) "y"))

(defn board-size-prompt [size-options]
  (println "What size board do you want to play on?")
  (doseq [[idx key] (map-indexed vector (keys size-options))]
    (println (str (inc idx) ") " key))))

(defn get-board-size [size-options]
  (let [input          (read-line)
        size-selection (try (Integer/parseInt input)
                            (catch Exception _
                              nil))
        option-keys    (vec (keys size-options))]
    (if (and size-selection (>= size-selection 1) (<= size-selection (count size-options)))
      (get size-options (get option-keys (dec size-selection)))
      (get-board-size size-options))))

(defn display-difficulty-options [char options]
  (println "What difficulty setting should" char "use?")
  (run! println (map name options)))

(defn get-difficulty [character options]
  (display-difficulty-options character options)
  (get-selection character options))

(defn exit-message []
  (println "Thanks for playing!"))

(defmethod core/draw-state [:tui :game-over] [state]
  (exit-message)
  state)

(defmethod core/draw-state [:tui :winner] [{:keys [active-player-index players board] :as state}]
  (let [character (get-in players [active-player-index :character])]
    (display-board board)
    (announce-winner character)
    (play-again-prompt)
    state))

(defmethod core/draw-state [:tui :tie] [state]
  (display-board (:board state))
  (announce-draw)
  (play-again-prompt)
  state)

(defmethod core/draw-state [:tui :in-progress] [{:keys [board] :as state}]
  (announce-player state)
  (display-board board)
  state)

(defmethod core/draw-state [:tui :select-board] [state]
  (board-size-prompt core/board-options)
  state)

(defmethod core/draw-state [:tui :config-o-difficulty] [state]
  (display-difficulty-options "O" core/difficulty-options)
  state)

(defmethod core/draw-state [:tui :config-x-difficulty] [state]
  (display-difficulty-options "X" core/difficulty-options)
  state)

(defmethod core/draw-state [:tui :config-o-type] [state]
  (display-play-type-options "O" core/player-options)
  state)

(defmethod core/draw-state [:tui :config-x-type] [state]
  (display-play-type-options "X" core/player-options)
  state)

(defmethod core/draw-state [:tui :found-save] [state]
  (save-found-prompt)
  state)

(defmethod core/draw-state [:tui :welcome] [state]
  (welcome-message)
  state)
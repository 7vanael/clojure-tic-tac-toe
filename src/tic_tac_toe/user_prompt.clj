(ns tic-tac-toe.user-prompt)

(defn get-interface [state _] (:interface state))

(defmulti welcome-message :interface)
(defmulti display-board get-interface)
(defmulti get-next-play get-interface)
(defmulti announce-player get-interface)
(defmulti announce-draw :interface)
(defmulti announce-winner get-interface)
(defmulti get-player-type (fn [state & _] (:interface state)))
(defmulti play-again? :interface)
(defmulti get-board-size get-interface)
(defmulti get-difficulty (fn [state & _] (:interface state)))
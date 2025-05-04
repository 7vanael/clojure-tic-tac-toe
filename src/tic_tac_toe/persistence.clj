(ns tic-tac-toe.persistence
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.io FileNotFoundException)))

(def savefile "game-save.edn")

(defn save-game [state]
  (spit savefile (pr-str state))
  state)

(defn load-game []
  (try (with-open [reader (io/reader savefile)]
         (edn/read-string (slurp reader)))
       (catch FileNotFoundException _
         nil)))
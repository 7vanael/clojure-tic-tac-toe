(ns tic-tac-toe.persistence
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.io FileNotFoundException)))

(def savefile "game-save.edn")

(defn save-game [state]
  (spit savefile (dissoc state :interface))
  state)

(defn load-game []
  (try (edn/read-string (slurp savefile))
       (catch FileNotFoundException _
         nil)))

(defn delete-save []
  (io/delete-file savefile true))
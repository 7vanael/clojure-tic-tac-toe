(ns tic-tac-toe.persistence.file
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [tic-tac-toe.core :as core])
  (:import (java.io FileNotFoundException)))

(def savefile "game-save.edn")

(defmethod core/save-game :edn [state]
  (spit savefile (dissoc state :interface))
  state)

(defmethod core/load-game :edn [_]
  (try (edn/read-string (slurp savefile))
       (catch FileNotFoundException _
         nil)))

(defmethod core/delete-save :edn [_]
  (io/delete-file savefile true))
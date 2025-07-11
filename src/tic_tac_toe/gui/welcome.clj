(ns tic-tac-toe.gui.welcome
  (:require [tic-tac-toe.core :as core]))


(defmethod core/mouse-clicked :welcome [state _]
  (let [saved-game (core/load-game state)]
    (if (= saved-game state)
      (assoc state :status :config-x-type)
      (assoc saved-game :status :found-save))))
(ns tic-tac-toe.main
  (:require [tic-tac-toe.tui.console]
            [tic-tac-toe.gui.gui]
            [tic-tac-toe.core :as core]
            tic-tac-toe.tui.human))

(defn -main [& args]
  (let [interface-type (keyword (first args))
        initial-state  {:status :config :interface (or interface-type :tui)}]
    (core/start-game initial-state)))

(ns tic-tac-toe.main
  (:require [tic-tac-toe.tui.console]
            [tic-tac-toe.gui.gui]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.tui.in-progress]
            [clojure.tools.cli :as cli]))

(defn print-option-info []
  (println "")
  (println "Flags for interface and save method can be used individually or together")
  (println "")
  (println "  lein run               Use default settings of text interface and save to sql")
  (println "  lein run --gui         Use graphical interface and save to default sql")
  (println "  lein run --edn         Use default text interface and save to text file")
  (println "  lein run --edn --gui   Use graphical interface and save to text file")
  (println ""))

(defn exit [code]
  (System/exit code))

(defn display-errors [errors summary]
  (println "Error with arguments: ")
  (doseq [error errors]
    (println error))
  (println summary)
  (print-option-info)
  (exit 1))

(def cli-options
  [[nil "--gui" "Use GUI interface"]
   [nil "--tui" "Use TUI interface (default)"]
   [nil "--edn" "Save to EDN files"]
   [nil "--sql" "Save to SQL database (default)"]])

(defn -main [& args]
  (let [{:keys [options errors summary]} (cli/parse-opts args cli-options)
        interface (cond (:gui options) :gui
                        (:tui options) :tui
                        :else :tui)
        save (cond (:edn options) :edn
                   (:sql options) :sql
                   :else :sql)
        initial-state  {:status :config :interface interface :save save}]
    (if (seq errors)
      (display-errors errors summary)
      (core/start-game initial-state))))

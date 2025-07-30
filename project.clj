(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "TicTacToe in a variety of ways"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tic-tac-toe.main
  :source-paths ["src/clj" "src/cljc" "src/cljs" "resources"]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [quil "4.3.1323"]
                 [com.github.seancorfield/next.jdbc "1.3.1002"]
                 [org.postgresql/postgresql "42.6.0"]
                 [org.clojure/tools.cli "1.1.230"]
                 [reagent "1.2.0"]
                 [org.clojure/clojurescript "1.11.60"]
                 [cljsjs/react "18.2.0-1"]
                 [cljsjs/react-dom "18.2.0-1"]]
  :profiles {:dev {:dependencies [[speclj "3.4.3"]]}}
  :plugins [[speclj "3.3.2"]
            [lein-cljsbuild "1.1.8"]]
  :cljsbuild {:builds [{:source-paths ["src/clj" "src/cljs" "src/cljc" "resources"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :optimizations :simple
                                   :main tic-tac-toe.main}}]}
  :test-paths ["spec/clj" "spec/cljc" "spec/cljs"]
  :aot [tic-tac-toe.main])

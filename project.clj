(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "TicTacToe in a variety of ways"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tic-tac-toe.main
  :source-paths ["src/clj" "src/cljc" "src/cljs" "resources"]
  :dependencies [[org.clojure/clojure "1.12.1"]
                 [quil "4.3.1323"]
                 [com.github.seancorfield/next.jdbc "1.3.1002"]
                 [org.postgresql/postgresql "42.6.0"]
                 [org.clojure/tools.cli "1.1.230"]
                 [reagent "1.2.0"]
                 [org.clojure/clojurescript "1.12.42"]
                 [com.cleancoders.c3kit/wire "2.5.0"]]
  :profiles {:dev {:dependencies [[speclj "3.10.0"]
                                  [com.cleancoders.c3kit/scaffold "2.0.3"]]}}
  :plugins [[speclj "3.10.0"]]
  :cljsbuild {:builds [{:source-paths ["spec/cljs" "src/cljs" "src/cljc" "resources"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :optimizations :simple
                                   :main tic-tac-toe.main}}]}
  :test-paths ["spec/clj" "spec/cljc" "spec/cljs"]
  :aliases {"cljs" ["with-profile" "dev" "run" "-m" "c3kit.scaffold.cljs"]
            ;"spec" ["-m" "speclj.main" "-c"] ;How can I not have this as plugin? need correct alias
            }
  :aot [tic-tac-toe.main])

(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tic-tac-toe.main
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [quil "4.3.1323"]
                 [com.github.seancorfield/next.jdbc "1.3.1002"]
                 [org.postgresql/postgresql "42.6.0"]
                 [org.clojure/tools.cli "1.1.230"]]
  :profiles {:dev {:dependencies [[speclj "3.4.3"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"]
  :aot [tic-tac-toe.main])

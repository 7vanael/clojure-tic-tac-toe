(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.main :refer :all]))

(describe "main"
  (with-stubs)

  (it "starts a new game with default settings"
    (with-redefs [core/start-game (stub :core/start)
                  println         (stub :print-dup)]
      (-main)
      (should-have-invoked :core/start)))

  (it "uses the console interface if launched with tui and saves to sql as default"
    (with-redefs [core/start-game (stub :launch-cli)
                  println         (stub :print-dup)]
      (-main "--tui")
      (should-have-invoked :launch-cli {:with [{:status :config :interface :tui :save :sql}]})))

  (it "uses the quil interface if launched with gui"
    (with-redefs [core/start-game (stub :launch-quil)
                  println         (stub :print-dup)]
      (-main "--gui")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui :save :sql}]})))


  (it "uses the console interface and saves to file if launched with tui edn"
    (with-redefs [core/start-game (stub :launch-cli)
                  println         (stub :print-dup)]
      (-main "--tui" "--edn")
      (should-have-invoked :launch-cli {:with [{:status :config :interface :tui :save :edn}]})))

  (it "uses the quil interface if launched with gui and saves to default sql"
    (with-redefs [core/start-game (stub :launch-quil)
                  println         (stub :print-dup)]
      (-main "--gui")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui :save :sql}]})))

  (it "uses the quil interface and saves to file if launched with gui and edn"
    (with-redefs [core/start-game (stub :launch-quil)
                  println         (stub :print-dup)]
      (-main "--gui" "--edn")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui :save :edn}]})))

  (it "displays an error and the viable options if an invalid entry is used "
    (with-redefs [core/start-game (stub :launch-quil)
                  exit            (stub :exit)]
      (should= "Error with arguments: \nUnknown option: \"--fake-flag\"\n      --gui  Use GUI interface\n      --tui  Use TUI interface (default)\n      --edn  Save to EDN files\n      --sql  Save to SQL database (default)\n\nFlags for interface and save method can be used individually or together\n\n  lein run               Use default settings of text interface and save to sql\n  lein run --gui         Use graphical interface and save to default sql\n  lein run --edn         Use default text interface and save to text file\n  lein run --edn --gui   Use graphical interface and save to text file\n\n"
               (with-out-str (-main "--fake-flag")))))

  (it "exits if an invalid entry is used "
    (with-redefs [core/start-game (stub :launch-quil)
                  println         (stub :print-dup)
                  exit            (stub :exit)]
      (with-out-str (-main "--fake-flag"))
      (should-have-invoked :exit)))

  )
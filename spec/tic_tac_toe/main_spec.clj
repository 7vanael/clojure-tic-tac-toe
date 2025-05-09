(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.main :refer :all]))

(describe "main"
  (with-stubs)

  (it "starts a new game"
    (with-redefs [core/start-game (stub :core/start)
                  println         (stub :print-dup)]
      (with-in-str "human\nhuman\n1\nno\n" (-main "tui"))
      (should-have-invoked :core/start)))

  (it "uses the console interface if launched with tui"
    (with-redefs [core/start-game           (stub :launch-cli)
                  println         (stub :print-dup)]
      (with-in-str "human\ncomputer\nmedium\n1\nn\n" (-main "tui"))
      (should-have-invoked :launch-cli {:with [{:status :config :interface :tui}]})))

  (it "uses the quil interface if launched with gui"
    (with-redefs [core/start-game           (stub :launch-quil)
                  println         (stub :print-dup)]
      (-main "gui")
      (should-have-invoked :launch-quil {:with [{:status :config :interface :gui}]})))
  )
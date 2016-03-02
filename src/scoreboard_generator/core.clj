(ns scoreboard-generator.core
  (:gen-class)
  (:require [scoreboard-generator.fileParser :as fileParser]
            [scoreboard-generator.tree :as tree]))


(defn -main
  "I don't do a whole lot ... yet."
  []
  (let [test-file "resources/test_input.txt"
        invitations (fileParser/parse-invitations-from-file test-file)
        tree (tree/tree-from-invitations invitations)]
  tree))

(-main)
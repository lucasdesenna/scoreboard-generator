(ns scoreboard-generator.core
  (:gen-class)
  (:require [scoreboard-generator.file-parser :as file-parser]
            [scoreboard-generator.invitation-parser :as inv-parser]
            [scoreboard-generator.node-map-generator :as node-map-generator]
            [scoreboard-generator.scoreboard :as scoreboard]))


(defn -main
  "I don't do a whole lot ... yet."
  []
  (let [test-file "resources/input.txt"
        invitations (file-parser/parse-file test-file)
        proto-node-map (inv-parser/parse-invitations invitations)
        customer-map (node-map-generator/create-node-map proto-node-map)
        scoreboard (scoreboard/get-scoreboard customer-map)]
    (print scoreboard)))

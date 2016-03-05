(ns scoreboard-generator.core
  (:gen-class)
  (:require [scoreboard-generator.fileParser :as fileParser]
            [scoreboard-generator.invitationParser :as invParser]
            [scoreboard-generator.treeNode :as tree-node]
            [scoreboard-generator.scoreboard :as scoreboard]))


(defn- main
  "I don't do a whole lot ... yet."
  []
  (let [test-file "resources/input.txt"
        invitations (fileParser/parse-file test-file)
        proto-node-map (invParser/parse-invitations invitations)
        customer-map (tree-node/create-node-map proto-node-map)
        scoreboard (scoreboard/get-scoreboard customer-map)]
  scoreboard))
  
(main)
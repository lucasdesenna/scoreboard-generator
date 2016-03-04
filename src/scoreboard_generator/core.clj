(ns scoreboard-generator.core
  (:gen-class)
  (:require [scoreboard-generator.fileParser :as fileParser]
            [scoreboard-generator.forest :as forest]
            [scoreboard-generator.scoreboard :as scoreboard]))


(defn- main
  "I don't do a whole lot ... yet."
  []
  (let [test-file "resources/test_input2.txt"
        invitations (fileParser/parse-invitations-from-file test-file)
        customer-forest (forest/create-forest invitations)
        scoreboard (scoreboard/get-scoreboard customer-forest)]
  scoreboard))
  
(main)
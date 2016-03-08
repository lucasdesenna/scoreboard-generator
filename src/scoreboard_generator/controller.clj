(ns scoreboard-generator.controller
  (:require [scoreboard-generator.invitation-parser :as inv-parser]
            [scoreboard-generator.node-map-factory :as node-map-factory]
            [scoreboard-generator.scoreboard-factory :as scoreboard-factory]))

(defn create-scoreboard 
  "Returns a scoreboard from a list of invitations."
  [invitations]
  
  (let [dirty-customer-map (inv-parser/parse-invitations invitations)
        customer-map (node-map-factory/create-node-map dirty-customer-map)
        scoreboard (scoreboard-factory/create-scoreboard customer-map)]
    scoreboard))
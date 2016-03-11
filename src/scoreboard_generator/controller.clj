(ns scoreboard-generator.controller
  (:require [scoreboard-generator.input-parser :as input-parser]
            [scoreboard-generator.invitation-parser :as inv-parser]
            [scoreboard-generator.node-map-factory :as node-map-factory]
            [scoreboard-generator.scoreboard-factory :as scoreboard-factory]))

(defn parse-file
  "Returns list of invitations from a properly formatted file."
  [file]
  
  (input-parser/parse-file file))

(defn create-scoreboard 
  "Returns a scoreboard from a list of invitations."
  [invitations]
  
  (let [dirty-customer-map (inv-parser/parse-invitations invitations)
        customer-map (node-map-factory/create-node-map dirty-customer-map)
        scoreboard (scoreboard-factory/create-scoreboard customer-map)]
    scoreboard))

(defn- concat-invitations 
  "Concatenates two invitation lists."
  [invitations1 invitations2]
  
  (concat invitations1 invitations2))

(defn add-invitation 
  "Returns a new invitation list from a given list an inviter and an invitee."
  [invitations inviter invitee]
  
  (if-let [invitation (input-parser/parse-inviter-invitee (Integer. inviter) (Integer. invitee))]
    (let [concatenated-invitations (concat-invitations invitations invitation)]
      
      (println "\nInvitation added: " invitation)
      concatenated-invitations)
    invitations))
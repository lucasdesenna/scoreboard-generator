(ns scoreboard-generator.core
  (:require [scoreboard-generator.client :as client]
            [scoreboard-generator.server :as server]
            [scoreboard-generator.controller :as controller])
  (:gen-class))

(defn -main
  "Parses a properly formatted invitation list file (.txt) into a customer scoreboard and exposes it through a HTTP endpoit. Another HTTP endpoint allows for the addition of new invitations to the loaded list.
  
  !IMPORTANT!: Invitation lists should be text files containing a single 
  '$inviter-id $invitee-id' pair per line. IDs must be integers."
  []
    
    (server/run)
    (client/run))
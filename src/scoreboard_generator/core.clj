(ns scoreboard-generator.core
  (:gen-class)
  (:require [scoreboard-generator.user-interface :as ui]
            [scoreboard-generator.file-parser :as file-parser]
            [scoreboard-generator.invitation-parser :as inv-parser]
            [scoreboard-generator.node-map-factory :as node-map-factory]
            [scoreboard-generator.scoreboard-factory :as scoreboard-factory]))


(defn -main
  "Parses a properly formatted invitation list into a customer scoreboard and uploads it as a JSON object to the specified URL. The user is then given the option to pick a new file for submission or end the program.
  
  !IMPORTANT!: Invitation lists should be text files containing a single '$inviter-id $invitee-id' pair per line. Ids must be integers."
  []
  
  (ui/hello)
  
  (defn main-loop [] 
    (let [input-file (ui/select-input-file)
          invitations (file-parser/parse-file input-file)
          dirty-customer-map (inv-parser/parse-invitations invitations)
          customer-map (node-map-factory/create-node-map dirty-customer-map)
          scoreboard (scoreboard-factory/create-scoreboard customer-map)]
      
      (ui/print-scoreboard-as-table scoreboard)
      (ui/upload-scoreboard scoreboard)
      (ui/generate-another? main-loop)))
  
  (main-loop)
  
  (ui/quit))
(ns scoreboard-generator.core
  (:require [scoreboard-generator.user-interface :as ui]
            [scoreboard-generator.controller :as controller])
  (:gen-class))

  (def current-invitations nil)
  
  (def current-scoreboard nil)

(defn- main-loop 
  "Displays scoreboard and stores relevant information in global variables."
  [invitations]
  
  (alter-var-root #'current-invitations 
                  (constantly invitations))

  (alter-var-root #'current-scoreboard 
                  (constantly (controller/create-scoreboard invitations)))
  
  (ui/print-scoreboard-as-table current-scoreboard))

(defn -main
  "Parses a properly formatted invitation list into a customer scoreboard. 
  The user is then given the option to add new invitations to the scoreboard 
  or upload it as a JSON string.
  
  !IMPORTANT!: Invitation lists should be text files containing a single 
  '$inviter-id $invitee-id' pair per line. Ids must be integers."
  []
  
  (ui/hello)
  (main-loop (ui/wait-invitations))  
  (dorun (repeatedly 
      #(case (ui/add-invitation-or-upload?)
        "Add" (main-loop (ui/add-invitations-to current-invitations)) 
        "Upload" (ui/try-upload current-scoreboard)
        "Quit" (ui/quit)))))
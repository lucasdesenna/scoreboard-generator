(ns scoreboard-generator.user-interface
  (:require [scoreboard-generator.file-parser :as file-parser]
            [scoreboard-generator.uploader :as uploader]
            [clojure.pprint :as pprint]))

(import '(javax.swing JFileChooser)
        '(javax.swing.filechooser FileNameExtensionFilter))

(defn hello 
  "Greets user."
  []
  
  (println "\nRunning Scoreboard Generator...\n"))


(defn- repeat-action 
  "Prints a message before executing the provided action. If provided, arguments are applyed to action."
  ([message action & args]
    
    (println message)
    (action args))
  
  ([message action]
    
    (println message)
    (action)))

(defn- use-file-picker 
  "Summons OS file picker and returns the selected file."
  []
  
  (let [ extFilter (FileNameExtensionFilter. "Text File" (into-array  ["txt"]))
        filechooser (JFileChooser. "./sample inputs")
        dummy (.setFileFilter filechooser extFilter)
        retval (.showOpenDialog filechooser nil) ]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (do 
        (.getSelectedFile filechooser))
      "")))

(defn- ask-for-file 
  "Prompts user to choose an input file with the help of a file picker."
  []
  
  (println "Choose a file (.txt) containing an invitation list.\n")
  (let [intput-file (use-file-picker)]
    (if intput-file
      (str intput-file)
      (ask-for-file))))

(defn wait-invitations
  "Requests an invitation file and returns a list of invitations. Repeats itself until provided with a valid file."
  []
  
  (let [file (ask-for-file)
       invitations (file-parser/parse-file file)]
    
    (if invitations
        invitations
        (repeat-action "This file contains no valid invitation. Please choose another file.\n" wait-invitations))))

(defn- format-scoreboard-as-table
  "Formats a given scoreboard as a table."
  [scoreboard]
  
  (let [ranking (take (count scoreboard) (iterate inc 1))
        table-rows-values (map #(cons %1 %2) ranking scoreboard)
        table-rows (map #(zipmap ["#" "Customer" "Score"] %) table-rows-values)]
    table-rows))

(defn print-scoreboard-as-table
  "Displays a properly formatted scoreboard as a table."
  [scoreboard]
  
  (println "\nScoreboard successfully generated!\n")
    
  (pprint/print-table 
    ["#" "Customer" "Score"] 
    (format-scoreboard-as-table scoreboard)))

(defn prompt-read
  "Displays a prompt with custom character."
  [prompt]
  
  (print (format "%s " prompt))
  (flush)
  (read-line))

(defn- ask-for-target-uri 
  "Prompts the player to provide a target URI."
  []
  
  (println "\nEnter target URI: (Example: 'http//hostname:port/service')\n(If left blank, defaults to 'http//localhost:8090/json'.\nTest server and service are automatically created.)\n")
  (let [target-uri (prompt-read "URI >>")]
      target-uri))

(defn- upload-scoreboard 
  "Tries to upload the provided scoreboard to the target URI. If no URI is provided, the scoreboard is uploaded to a local test server."
  [scoreboard target-uri]
    
    (if (not= target-uri "") 
      (uploader/upload scoreboard target-uri) 
      (uploader/upload-test scoreboard)))

(defn try-upload 
  "Requests a target URI, tries to upload the provided scoreboard to it and waits for a status report. Repeats itself until the upload is successful."
  [scoreboard]
  
  (let [target-uri (ask-for-target-uri)
        upload-successful? (upload-scoreboard scoreboard target-uri)
        success-message "Upload complete.\n"
        fail-message "Please check your internet connection or try another URI.\n"]
    
    (if upload-successful?
      (println success-message)
      (repeat-action fail-message try-upload scoreboard))))

(defn- extract-answer 
  "Returns one out of four possible answers from a input line."
  [input-line]
  
  (let [addlike? (not-empty (re-find #"(?i)^a" input-line))
        uploadlike? (not-empty (re-find #"(?i)^u" input-line))
        quitlike? (not-empty (re-find #"(?i)^q" input-line))]
    
    (if addlike?
      "Add"
      (if uploadlike?
        "Upload"
        (if quitlike?
          "Quit"
          "invalid")))))

(defn add-invitation-or-upload? 
  "Prompts the user to decide between adding new invitations to the current scoreboard or uploading it."
  []
  
  (println "\nAdd new invitation(s) or upload current scoreboard?\n('Add', 'Upload' or 'Quit')\n")
  (let [answer (extract-answer(prompt-read ">>"))
        fail-message "\nSorry, I did not get it.\n"]
    (if (not= answer "invalid")
          answer
          (repeat-action fail-message add-invitation-or-upload?))))

(defn- ask-for-invitations 
  "Prompts the user to provide a csv string to be parsed into new invitations."
  []
  
  (println "Specify new invitation(s).\n(Format: '$id1 $id2; $id3 $id4;...' Obs.: Ids can only be integers.)\nExample: '1 2; 2 3; 2 4;'\n")
  
  (prompt-read ">>"))

(defn- merge-invitations 
  "Merges current invitations and new ones."
  [current-invitations new-invitations]
  
  (concat current-invitations new-invitations))

(defn add-invitations-to 
  "Requests a csv string, parses it into new invitations and returns the provided invitations concatenated with the new ones."
  [invitations]
  
  (let [csv (ask-for-invitations)]
    
    (if (not-empty csv)
      (let [new-invitations (file-parser/parse-csv csv)
            updated-invitations (file-parser/concat-invitations invitations new-invitations)]
        updated-invitations)
      invitations)))

(defn quit 
  "Quits the program"
  []
  
  (println "\nQuitting Scoreboard Generator...")
  (println "\nGoodbye!\n")
  (System/exit 0))

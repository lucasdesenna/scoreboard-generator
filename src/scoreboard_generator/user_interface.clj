(ns scoreboard-generator.user-interface
  (:gen-class)
  (:require [scoreboard-generator.uploader :as uploader]
            [clojure.pprint :as pprint]))

(import '(javax.swing JFileChooser)
        '(javax.swing.filechooser FileNameExtensionFilter))

(defn hello 
  "doc-string"
  []
  
  (println "\nRunning Scoreboard Generator...\n"))

(defn- use-file-picker 
  "doc-string"
  []
  
  (let [ extFilter (FileNameExtensionFilter. "Text File" (into-array  ["txt"]))
        filechooser (JFileChooser. "./resources")
        dummy (.setFileFilter filechooser extFilter)
        retval (.showOpenDialog filechooser nil) ]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (do 
        (println (.getSelectedFile filechooser))
        (.getSelectedFile filechooser))
      "")))

(defn select-input-file 
  "doc-string"
  []
  
  (println "Choose an invitation list.\n(defaults to './resources/input.txt')\n")
  (if-let [intput-file (use-file-picker)] 
    (str intput-file)
    "resources/input.txt"))

(defn print-scoreboard-as-table
  "doc-string"
  [scoreboard]
  
  (println "\nScoreboard generated successfully!\n")
  
  (let [ranking (take (count scoreboard) (iterate inc 1))
        table-rows-values (map #(cons %1 %2) ranking scoreboard)
        table-rows (map #(zipmap ["#" "Customer" "Score"] %) table-rows-values)]
    
    (pprint/print-table 
      ["#" "Customer" "Score"] 
      table-rows)))

(defn upload-scoreboard 
  "doc-string"
  [scoreboard]
  
  (println "\nEnter url for scoreboard submission:\n(defaults to 'http//localhost:8090/' - test server and service are automatically created)\n")
  (let [upload-url (read-line)]
    
    (if (not-empty upload-url)
      (uploader/upload upload-url scoreboard)
      (uploader/upload-test scoreboard))))

(defn quit 
  "doc-string"
  []
  
  (println "\nQuitting Scoreboard Generator...")
  (println "\nGoodbye!\n")
  (System/exit 0))

(defn- repeat-question 
  "doc-string"
  [question & question-args]
  
  (println "\nSorry, I did not get it.\n")
  (question question-args))

(defn- yes? 
  "doc-string"
  [yes-or-no]
  
  (let [first-letter (first yes-or-no)]
    (case first-letter
      (\Y \y) true
      (\N \n) false
      "invalid")))

(defn generate-another? 
  "doc-string"
  [action-to-be-repeated]
  
  (println "\nUpload another scoreboard?\n('Yes' or 'No')\n")
  (let [answer (read-line)]
    (case (yes? answer)
          true (action-to-be-repeated)
          false (quit)
          "invalid" (repeat-question generate-another? action-to-be-repeated))))


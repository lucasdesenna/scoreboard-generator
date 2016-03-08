(ns scoreboard-generator.uploader
  (:require [scoreboard-generator.test-server :as test-server]
            [org.httpkit.client :as http]
            [cheshire.core :refer :all]))

(defn- print-echo 
  "Prints a given echo."
  [echo]
  (println "Echo received from server: \n")
  (println  echo "\n"))

(defn- upload-successful 
  "Returns true, prints a warning and prints an echo of the body of the message received, if provided."
  [body]
  
  (println "Upload successful.\n")
  (when body (print-echo body))
  true)

(defn upload-failed 
  "Returns false and prints a warning."
  [error]
  
  (println "Upload failed because of " error "\n")
  false)

(defn generate-report 
  "Returns true or false and prints warnings based on the provided response"
  [response]
  
  (let [body (:body response)
        error (:error response)]
    
    (if (not error)
      (upload-successful body)
      (upload-failed error))))

(defn upload 
  "Uploads a given scoreboard to a given URI. Returns a report based on the bounce-back received."
  [scoreboard target-uri]
  
  (println "\nConnecting to" target-uri "\n")
  (let [options {:header {"Content-Type" "application/json; charset=utf-8"}
                 :body (encode scoreboard)
                 :as :text}
      response @(http/get target-uri options)]
    
    (generate-report response)))

(defn upload-test 
  "Uploads a given scoreboard to a local test server. Returns a report based on the bounce-back received."
  [scoreboard]
  
  (test-server/run)
  (let [upload-successful? (upload scoreboard "http://127.0.0.1:8090/json")]
    (test-server/die-if-running)
    upload-successful?))

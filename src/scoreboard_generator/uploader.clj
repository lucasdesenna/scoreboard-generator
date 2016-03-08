(ns scoreboard-generator.uploader
  (:require [scoreboard-generator.test-server :as test-server]
            [org.httpkit.client :as http]
            [cheshire.core :refer :all]))

(defn- print-echo 
  "doc-string"
  [echo]
  (println "Echo received from server: \n")
  (println  echo "\n"))

(defn- upload-successful 
  "doc-string"
  [body]
  
  (println "Upload successful.\n")
  (when body (print-echo body))
  true)

(defn upload-failed 
  "doc-string"
  [error]
  
  (println "Upload failed because of " error "\n")
  false)

(defn generate-report 
  "doc-string"
  [body error]
  
  (if (not error)
    (upload-successful body)
    (upload-failed error)))

(defn upload 
  "doc-string"
  [upload-uri scoreboard]
  
  (println "\nConnecting to" upload-uri "\n")
  (let [options {:header {"Content-Type" "application/json; charset=utf-8"}
                 :body (encode scoreboard)
                 :as :text}
      {:keys [status body error]} @(http/get upload-uri options)]
    
    (generate-report body error)))

(defn upload-test 
  "doc-string"
  [scoreboard]
  
  (test-server/run)
  (let [upload-successful? (upload "http://127.0.0.1:8090/json" scoreboard)]
    (test-server/die-if-running)
    upload-successful?))

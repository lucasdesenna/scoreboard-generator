(ns scoreboard-generator.uploader
  (:gen-class)
  (:require [scoreboard-generator.test-server :as test-server]
            [org.httpkit.client :as http]
            [cheshire.core :refer :all]))

(defn- print-echo 
  "doc-string"
  [echo]
  (println "Echo of the information sent: \n")
  (println  echo))

(defn upload 
  "doc-string"
  [upload-url scoreboard]
  
  (println "Connecting to" upload-url "\n")
  (let [options {:header {"Content-Type" "application/json; charset=utf-8"}
                 :body (encode scoreboard)
                 :as :text}
      {:keys [status body error]} @(http/get upload-url options)]
    
    (if error
      (println "Failed, exception is " error "\n")
      (print-echo body))))

(defn upload-test 
  "doc-string"
  [scoreboard]
  
  (test-server/run)
  (upload "http://127.0.0.1:8090/json" scoreboard)
  (test-server/die-if-running))

(ns scoreboard-generator.test-server
  (:require [org.httpkit.server :refer [run-server with-channel on-close send!]])
  (:use [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]))

(defonce test-server (atom {}))

(defn json-handler 
  "Handles requests sent to the 'json' service. Echoes the body of the message received back to the sender."
  [request]
  
  (let [echo (-> request :body)]
    
    (with-channel request channel
        (println "Test Server channel open.\n")
        (on-close channel (fn [status]
                            (println "Test Server channel closed.\n")))
        
        (println "Data received.\n")
        
        (send! channel {:status 200
                        :headers {"Content-Type" "text/plain"}
                        :body echo}))))


(defn show-landing-page 
  "Renders a landing page."
  [request] 
  
  "<h1 style='font-family: Arial;'>Test Server is running...<h1>")

(defroutes all-routes
  (GET "/" [] show-landing-page)
  (GET "/json" [] json-handler))

(defn die-if-running
  "Kills the test server if it is running."
  []
  
  (when-not (nil? @test-server)
    (@test-server :timeout 1000)
    (reset! test-server nil)))

(defn run 
  "Runs test server."
  []
  
  (die-if-running)  
  (reset! test-server (run-server #'all-routes {:port 8090})))
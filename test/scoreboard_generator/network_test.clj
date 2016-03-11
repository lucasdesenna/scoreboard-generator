(ns scoreboard-generator.network-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.session :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.multipart-params :refer :all]
            [cheshire.core :as json]
            [scoreboard-generator.test-resources :as tr]
            [scoreboard-generator.server :as server]))

(defonce test-server (future (jetty/run-jetty
                                        (-> server/public-routes
                                            wrap-session
                                            wrap-params 
                                            wrap-multipart-params)
                                        {:port server/port})))

(deftest scoreboard-serving
  (testing 
    "Tests capacity to serve a scoreboard as a json string through an HTTP connection when supplied with a valid file."
    
    (let [test-input tr/basic-input
          options {:multipart [{:name "inputFile" :content test-input}]}
          response (client/post (str server/irl "parse_file") options)
          json (:body response)]
      
      (is (= json tr/basic-json)))))

(deftest invitation-addition
  (testing 
    "Tests capacity to serve a scoreboard as a json string through an HTTP connection when supplied with an invitation list, inviter and invitee."
    
    (let [test-invitations (json/encode tr/basic-invitations)
          test-inviter 1
          test-invitee 10
          options {:query-params {:current-invitations test-invitations :inviter test-inviter :invitee test-invitee}}
          response (client/get (str server/irl "add_invitation") options)
          json (:body response)]
      
      (is (= json tr/basic-json)))))

(alter-var-root #'test-server (constantly nil))

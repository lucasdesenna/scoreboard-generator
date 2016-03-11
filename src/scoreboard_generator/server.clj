(ns scoreboard-generator.server
  (:require [scoreboard-generator.controller :as controller]
            [scoreboard-generator.page-builder :as page-builder]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [ring.middleware.session :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.multipart-params :refer :all]
            [compojure.route :refer :all]
            [compojure.core :refer :all]
            [cheshire.core :as json]))

(defonce server nil)

(defonce irl "http://localhost:8090/")  

(defonce port 8090)


(defn- serve-main-page 
  "Renders landing page."
  ([] 
   (page-builder/build-landing-page))

  ([{session :session}] 
      
      (if-let [scoreboard-json (:scoreboard session)]
        (page-builder/build-results-page scoreboard-json)
        (page-builder/build-landing-page))))

(defn- update-session 
  "doc-string"
  [session updated-invitations-json updated-scoreboard-json]
  
  (let [updated-session (assoc session :invitations updated-invitations-json
                                        :scoreboard updated-scoreboard-json)]
      
      (println "\nInvitations added to session: " updated-invitations-json)
      (println "\nScoreboard added to session: " updated-scoreboard-json)
      updated-session))

(defn- import-file 
  "Handles requests sent to the 'json' service. Echoes the body of the message 
  received back to the sender."
  [request]

  (let [inputFile (get-in request [:params "inputFile"])
        tempfile (:tempfile inputFile)
        session (get request :session)
        invitations (controller/parse-file tempfile)
        scoreboard (controller/parse-scoreboard invitations)
        updated-session (update-session session (json/encode invitations)
                                                (json/encode scoreboard))
        results-page (page-builder/build-results-page invitations
                                                      scoreboard)]

    (-> (resp/response results-page)
        (resp/content-type "text/html")
        (assoc :session updated-session))))

(defn- add_invitation 
  "Handles requests sent to the 'json' service. Echoes the body of the message 
  received back to the sender."
  [request]

  (let [inviter (get-in request [:params "inviter"])
        invitee (get-in request [:params "invitee"])
        session (get request :session)
        current-invitations-json (get session :invitations)
        current-invitations (json/decode current-invitations-json true)
        updated-invitations (controller/add-invitation current-invitations inviter invitee)
        updated-scoreboard (controller/parse-scoreboard updated-invitations)
        updated-session (update-session session (json/encode updated-invitations)
                                                (json/encode updated-scoreboard))
        results-page (page-builder/build-results-page updated-invitations 
                                                      updated-scoreboard)]        
   
   (-> (resp/response results-page)
        (resp/content-type "text/html")
        (assoc :session updated-session))))


(defn- goobye 
  "Wishes farewell"
  []
  
  (println "\nQuitting Scoreboard Generator...")
  (println "\nGoodbye!\n"))


(defn- quit 
  "Renders goobye page, stops the server and quits the program"
  [request]
  
  (future (Thread/sleep 2000) (System/exit 0))
  (goobye)
  (page-builder/build-goodbye-page))
  
(defroutes public-routes
  (GET "/" [] serve-main-page)
  (POST "/import_file" [] import-file)
  (GET "/add_invitation" [] add_invitation)
  (ANY "/quit" [] quit)
  (not-found "<p>Page not found.</p>"))

(alter-var-root #'server 
                    (constantly (-> public-routes
                                    wrap-session
                                    wrap-params 
                                    wrap-multipart-params)))
(defn stop
  "Stops server."
  []
  {:pre [server]}
  
  (.stop server))

(defn run 
  "Runs server."
  []
  (stop)  
  (future (jetty/run-jetty server {:port port})))
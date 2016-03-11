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

(defn- serve-main-page 
  "Renders landing page."
  ([] 
   (page-builder/build-landing-page))

  ([{session :session}] 
      
    (if-let [scoreboard-json (:scoreboard session)]
      (page-builder/build-results-page scoreboard-json)
      (page-builder/build-landing-page))))

(defn- update-session 
  "Returns a new session map with new values for the 'invitations' and 'scoreboard' keys."
  [session updated-invitations-json updated-scoreboard-json]
  
  (let [updated-session (assoc session :invitations updated-invitations-json
                                        :scoreboard updated-scoreboard-json)]
      
      (println "\nInvitations added to session: " updated-invitations-json)
      (println "\nScoreboard added to session: " updated-scoreboard-json)
      updated-session))

(defn- parse-file 
  "Returns a scoreboard as a json-string from a properly formatted file."
  [request]
    
  (let [inputFile (get-in request [:params "inputFile"])
        tempfile (:tempfile inputFile)
        invitations (controller/parse-file tempfile)
        scoreboard (controller/parse-scoreboard invitations)
        json-scoreboard (json/encode scoreboard)]
    
    (-> (resp/response json-scoreboard)
        (resp/content-type "application/json"))))

(defn- parse-file-html 
  "Parses file into scoreboard, stores it in the current session and returns an html detailing the former."
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

(defn- add-invitation 
  "Returns a json-encoded scoreboard containing the new invitation."
  [request]

  (let [current-invitations-json (get-in request [:params "current-invitations"])
        inviter (get-in request [:params "inviter"])
        invitee (get-in request [:params "invitee"])
        session (get request :session)
        current-invitations (json/decode current-invitations-json true)
        updated-invitations (controller/add-invitation current-invitations inviter invitee)
        scoreboard (controller/parse-scoreboard updated-invitations)
        json-scoreboard (json/encode scoreboard)]        
   
   (-> (resp/response json-scoreboard)
        (resp/content-type "application/json"))))

(defn- add-invitation-html 
  "Extracts current invitations from running session, adds new invitation, parses it into a scoreboard and returns an html detailing the former. Also updates current session."
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


(defn- goodbye 
  "Prints farewell message."
  []
  
  (println "\nQuitting Scoreboard Generator...")
  (println "\nGoodbye!\n"))


(defn- quit 
  "Renders goodbye page and quits the program."
  [request]
  
  (future (Thread/sleep 2000) (System/exit 0))
  (goodbye)
  (page-builder/build-goodbye-page))
  
(defroutes public-routes
  (GET "/" [] serve-main-page)
  (POST "/parse_file" [] parse-file)
  (POST "/parse_file_html" [] parse-file-html)
  (GET "/add_invitation" [] add-invitation)
  (GET "/add_invitation_html" [] add-invitation-html)
  (ANY "/quit" [] quit)
  (not-found "<p>Page not found.</p>"))

(defonce server nil)

(defonce irl "http://localhost:8090/")  

(defonce port 8090)

(defn stop
  "Stops server."
  [server]
  
  (when server
      (.stop server)))

(defn run 
  "Runs server."
  []
  
  (stop server)
  (alter-var-root #'server 
                    (constantly (future (jetty/run-jetty
                                        (-> public-routes
                                            wrap-session
                                            wrap-params 
                                            wrap-multipart-params)
                                        {:port port})))))


(ns scoreboard-generator.client
  (:require [clojure.java.browse :refer [browse-url]]
            [scoreboard-generator.server :as server]
            [cheshire.core :refer [generate-string]]))


(defn- hello 
  "Greets user."
  []
  
  (println "\nRunning Scoreboard Generator...\n"))

(defn run 
  "Opens browser and loads the server landing page."
  []
  
  (hello)
  (browse-url server/irl))

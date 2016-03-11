(ns scoreboard-generator.page-builder
  (:require [scoreboard-generator.resources :as resources]
            [hiccup.core :refer :all]
            [cheshire.core :as json]))

(defn build-landing-page
  "Returns an html page with no results."
  []
  
  (let [head resources/head
        title resources/title
        toolbar resources/toolbar-init
        foot resources/foot
        landing-page (str title head toolbar foot)]
    landing-page))

(defn- format-scoreboard-as-table
  "Formats a given scoreboard as a table."
  [scoreboard]

    (let [ranking (take (count scoreboard) (iterate inc 1))
        table-rows-values (map #(cons %1 %2) ranking scoreboard)
        table-rows (map #(zipmap ["#" "Customer" "Score"] %) table-rows-values)]
    table-rows))

(defn- render-scoreboard-as-table 
  "Returns an html component detailing a scoreboard as a table."
  [scoreboard]

  (let [formated-scoreboard (format-scoreboard-as-table scoreboard)]
    (html [:h2 "Scoreboard"]
          [:table.table.table-striped.table-bordered.table-hover
           [:thead 
            [:tr
             [:th "#"] [:th "Customer"] [:th "Score"]]]
           [:tbody
            (for [entry formated-scoreboard
                  :let [ranking (get entry "#")
                        customer-id (get entry "Customer")
                        score (get entry "Score")]]
              (html [:tr 
                     [:td ranking] [:td customer-id] [:td score]]))]])))

(defn- render-scoreboard-as-string 
  "Returns an html component detailing a scoreboard."
  [scoreboard]
  
  (let [scoreboard-string (json/encode scoreboard)]
    (html [:h2 "JSON " [:small "(" (count scoreboard) ")"]]
          [:code scoreboard-string])))

(defn- render-invitations-as-string 
  "Returns an html component detailing a list of invitations."
  [invitations]
  
  (let [invitations-string (json/encode invitations)]
    (html [:h2 "Invitations " [:small "(" (count invitations) ")"]]
          [:code invitations-string])))
    
(defn- build-results-section 
  "Returns an html component detailing the given invitations and scoreboard."
  [invitations scoreboard]
  
  (let [invitations-as-string (render-invitations-as-string invitations)
        scoreboard-table (render-scoreboard-as-table scoreboard)
        scoreboard-as-string (render-scoreboard-as-string scoreboard)]
    
    (html [:div.row 
           [:div.col-xs-4 scoreboard-table]
           [:div.col-xs-6 
              [:div.row scoreboard-as-string]
              [:div.row invitations-as-string]]])))

(defn build-results-page
  "Returns an html page with a populated result section."
  [invitations scoreboard]
  
  (let [head resources/head
        title resources/title
        toolbar resources/toolbar
        results-section (build-results-section invitations scoreboard)
        foot resources/foot
        results-page (str head title toolbar results-section foot)]
    
    results-page))

(defn build-goodbye-page
  "Returns an html page with no results."
  []
  
  (let [head resources/head
        goodbye resources/goodbye
        foot resources/foot
        goodby-page (str head goodbye foot)]
    goodby-page))
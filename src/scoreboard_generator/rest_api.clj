(ns scoreboard-generator.rest-api
  (:require [liberator.core :refer [defresource]]))

(defroutes app
  (ANY "/foo" 
       [] 
       (resource :available-media-types ["text/html"]
                 :handle-ok (fn [ctx] 
                              (format "<html>It's %d milliseconds since the 
                                      beginning of the epoch." 
                                      (System/currentTimeMillis))))))

(def handler 
  (-> app 
      wrap-params))
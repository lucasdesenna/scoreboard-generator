(ns scoreboard-generator.invitation
  (:gen-class)
  (:require [clojure.string :as string]))

(defrecord Invitation [^Integer inviter ^Integer invitee])

(defn- extract-inviter-and-invitee
  "Extracts inviter and invitee pair from a single line string."
  [^String line]
  (map #(Integer. %) (string/split line #"\s")))

(defn create-invitation 
  ([^Integer inviter ^Integer invitee]
   
    (->Invitation inviter invitee))
  
  ([^String line]
    (let [inviter-and-invitee (extract-inviter-and-invitee line)]
      
      (if (= (count inviter-and-invitee) 2) 
        (create-invitation (first inviter-and-invitee) (second inviter-and-invitee))
        (throw (IllegalArgumentException. (str "The line '" line "' does not describe an invitation")))))))


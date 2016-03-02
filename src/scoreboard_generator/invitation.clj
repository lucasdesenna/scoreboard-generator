(ns scoreboard-generator.invitation
  (:gen-class)
  (:require [clojure.string :as string]))

(defrecord Invitation [^String inviter ^String invitee])

(defn- extract-inviter-and-invitee
  "Extracts inviter and invitee pair from a single line string."
  [^String line]
  (string/split line #"\s"))

(defn invitation 
  ([^String inviter ^String invitee]
   
    (->Invitation inviter invitee))
  
  ([^String line]
    (let [inviter-and-invitee (extract-inviter-and-invitee line)]
      
      (if (= (count inviter-and-invitee) 2) 
        (->Invitation (first inviter-and-invitee) (second inviter-and-invitee))
        (throw (IllegalArgumentException. (str "The line '" line "' does not describe an invitation")))))))

(defn- conflicts-with? 
  "doc-string"
  [invitation]
  
  (fn [_invitation] 
    (or (= (:invitee _invitation) "1")
      (and 
        (not= (:inviter invitation) (:inviter _invitation))
        (= (:invitee invitation) (:invitee _invitation))))))


(defn- remove-conflicting-invitations 
  "Removes all invitations to previously invited invitees from a collection."
  [invitations]
  
  (if (first invitations)
    (let [resolved (remove (conflicts-with? (first invitations)) invitations)]
      (cons
        (first resolved)
        (remove-conflicting-invitations (rest resolved))))
    (list)))

(defn- group-by-inviter 
  "doc-string"
  [invitations]
  
  (group-by :inviter invitations))

(defn- format-for-node-extraction 
  "doc-string"
  [invitations]
  
  (reduce conj
    (map 
      (fn [invitation]
        
        {(key invitation) (map #(:invitee %) (val invitation))}) 
      invitations)))

(defn prepare-invitations-for-node-extraction
  "doc string"
  [invitations]
  
    (let [unconflicting-invitations (remove-conflicting-invitations invitations) 
          invitations-by-inviter (group-by-inviter unconflicting-invitations)
          ready-for-node-extraction (format-for-node-extraction invitations-by-inviter)]
      ready-for-node-extraction))
(ns scoreboard-generator.treeNode
  (:gen-class)
  (:require [scoreboard-generator.invitation :as inv]))

(defrecord Node
  [^String id children-nodes-ids])
  ; "A Node is an abstract data type that models the behaviour of a node inside a Tree data type."

(defn node 
  "doc-string"
  
  ([^String id children-nodes-ids] 
   (->Node id children-nodes-ids))
  
  ([formated-invitation] 
   (->Node (key formated-invitation) (val formated-invitation))))

(defn nodes-from-nodes-ids 
  "docs string"
  [nodes-ids node-map]
  
  (if (first nodes-ids)
    (let [target-node (get node-map (first nodes-ids))]
      
      (cons
        target-node
        (nodes-from-nodes-ids (rest nodes-ids) node-map)))
    (list)))


(defn- extract-node-map
  "doc-string"
  [extraction-ready-invitations]
  (into 
    (sorted-map) 
    (map 
      #(vector (key %) (node %)) 
      extraction-ready-invitations)))

(defn parse-node-map-from-invitations
  "doc-string"
  [invitations]
  (let [ready-for-node-extraction (inv/prepare-invitations-for-node-extraction invitations)
        node-map (extract-node-map ready-for-node-extraction)] 
    node-map))
(ns scoreboard-generator.tree
  (:gen-class)
  (:require [scoreboard-generator.treeNode :as node]))

(defrecord Tree [^clojure.lang.PersistentHashSet nodes])

(defn- tree-from-nodes-and-node-map
  "doc-string"
  ([nodes node-map]
   
   (if (first nodes)
     (let [node (first nodes) 
           node-id (:id node) 
           children-nodes-ids (:children-nodes-ids node)]
        
       (concat 
         (list  
           node-id  
           (tree-from-nodes-and-node-map (node/nodes-from-nodes-ids children-nodes-ids node-map) node-map)) 
         (tree-from-nodes-and-node-map (rest nodes) node-map))) 
     (list))))

(defn tree-from-invitations 
  "doc-string" 
  [invitations]
  
  (let [node-map (node/parse-node-map-from-invitations invitations)
        root-node (val (first node-map))]
    
  (tree-from-nodes-and-node-map (list root-node) node-map)))

(defn subtree-from-tree-and-node-id 
  "doc-string"
  [tree node-id]
  
  )
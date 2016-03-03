(ns scoreboard-generator.tree
  (:gen-class)
  (:require [scoreboard-generator.treeNode :as node]))

(defrecord Tree [nodes])

(defn tree-from-nodes-and-node-map
  "doc-string"
  ([nodes node-map]
   
   (if (first nodes)
     (let [node (first nodes) 
           node-id (:id node) 
           children-nodes-ids (:children-nodes-ids node)]
        
       (cons 
         (list  
           node-id  
           (tree-from-nodes-and-node-map (node/nodes-from-nodes-ids children-nodes-ids node-map) node-map)) 
         (tree-from-nodes-and-node-map (rest nodes) node-map))) 
     (list))))

; (defn tree-from-root-and-node-map 
;   "doc-string" 
;   [node-map]
  
;   (let [root-node (val (first node-map))]
    
;   (tree-from-nodes-and-node-map (list root-node) node-map)))

(defn branch-from-tree-and-node-id 
  "doc-string"
  [tree node-id]
  
  (let [branches-and-nodes (tree-seq seq? identity tree)
        branch-map (apply array-map (rest branches-and-nodes))
        desired-branch (get branch-map node-id)]
    desired-branch))

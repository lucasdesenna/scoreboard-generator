(ns scoreboard-generator.forest
  (:gen-class)
  (:require [scoreboard-generator.treeNode :as node]
            [scoreboard-generator.tree :as tree]))

(defn forest-from-invitations 
  "doc-string" 
  [invitations]
  
  (let [node-map (node/parse-node-map-from-invitations invitations)
        node-list (vals node-map)
        forest (tree/tree-from-nodes-and-node-map node-list node-map)]
    
  forest))

(defn ordered-forest-from-invitations 
  "doc-string" 
  [invitations]
  
  (let [forest (forest-from-invitations invitations)
        ordered-forest (zipmap (take (count forest) (iterate inc 1)) forest)]
    
  ordered-forest))


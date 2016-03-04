(ns scoreboard-generator.tree
  (:gen-class)
  (:require [scoreboard-generator.treeNode :as node]))

(defn create-tree
  "doc-string"
  ([node-map node-list]
    
    (if-let [validity (:validity (first node-list))]
      (let [target-node (first node-list)
            target-node-id (:id target-node) 
            children-nodes-ids (:children-nodes-ids target-node)
            validity (:validity target-node)]
        (cons 
          (list  
            target-node-id  
            (create-tree node-map (node/nodes-from-nodes-ids children-nodes-ids node-map))) 
          (create-tree node-map (rest node-list)))) 
      (list)))
  
  ([invitations]
    
   (let [node-map (node/parse-node-map-from-invitations invitations)
         node-list (vals node-map)]
     (create-tree node-map node-list))))

(defn trim-leafs 
  "doc-string"
  ([branches remove-root?]
   
   (if-let [target-branch (first branches)]
    (let [root-node-id (first target-branch)
          target-children-branches (second target-branch)
          leaf? (= (count target-children-branches) 0)]
      (if (or 
            (= leaf? false)
            (= remove-root? false))
        (cons
          (list root-node-id (trim-leafs target-children-branches true))
          (trim-leafs (rest branches) remove-root?))
        (trim-leafs (rest branches) remove-root?)))
    (list)))

  ([tree]
   
   (trim-leafs (list tree) false)))

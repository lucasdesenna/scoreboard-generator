(ns scoreboard-generator.treeNode
  (:gen-class)
  (:require [scoreboard-generator.invitation :as inv]))

(defrecord Node
  [^String id children-nodes-ids])
  ; "A Node is an abstract data type that models the behaviour of a node inside a Tree data type."

(defn node 
  "doc-string"
  
  ([^Integer id children-nodes-ids] 
   (->Node id children-nodes-ids))
  
  ([invitation]
   (let [node-id (:inviter invitation)
         children-nodes-ids (:invitee invitation)]
   (->Node node-id children-nodes-ids))))
  
(defn- nodes-from-invitations 
  "doc-string"
  [invitations]
  
  (map #(node %) invitations))

(defn- conflict? 
  "doc-string"
  [node node2]
  
  (if (and 
        (not= (:id node) (:id node2))
        (= (:children-nodes-ids node) (:children-nodes-ids node2))
        (not= (:children-nodes-ids node) "")
        (not= (:children-nodes-ids node2) ""))
    true
    false))


(defn- remove-conflicting-nodes-from-node-list 
  "doc-string"
  [node-list]
  
  (if (first node-list)
    (let [target-node (first node-list)
          child-node-id (:children-nodes-ids target-node)
          conflict-free-node-list (remove #(conflict? target-node %) node-list)]
      (cons 
        target-node
        (remove-conflicting-nodes-from-node-list (rest conflict-free-node-list))))
    (list)))

(defn- childless-nodes-from-node-list 
  "doc-string"
  [node-list]
  
  (if (first node-list)
    (let [target-node (first node-list)
          child-node-id (:children-nodes-ids target-node)]
      (cons 
        (node child-node-id "")
        (childless-nodes-from-node-list (rest node-list))))
    (list)))
    
(defn- group-by-id 
  "doc-string"
  [node-list]
  
  (group-by #(:id %) node-list))

(defn- merge-node-group 
  "doc-string"
  [node-group]
  
  (let [group (val node-group)
        commom-id (key node-group)]
    
    [(Integer. commom-id) (node commom-id
              (for [node group
                    :let [children-nodes-ids (:children-nodes-ids node)]
                    :when (not= children-nodes-ids "")]
                children-nodes-ids))]))
    
(defn- merge-related-nodes 
  "doc-string"
  [node-list]
  
  (let [node-groups (group-by-id node-list)
        merged-nodes (into (sorted-map) (map merge-node-group node-groups))]
        ; sorted-merged-nodes (sort-by #(< ) merged-nodes)]
    merged-nodes))
    
(defn parse-node-map-from-invitations
  "doc-string"
  [invitations]
  
  (let [base-node-list (nodes-from-invitations invitations)
        conflict-free-node-list (remove-conflicting-nodes-from-node-list base-node-list)
        childless-nodes-list (childless-nodes-from-node-list base-node-list)
        complete-node-list (concat conflict-free-node-list childless-nodes-list)
        clean-node-list (merge-related-nodes complete-node-list)]
    clean-node-list))

(defn nodes-from-nodes-ids 
  "docs string"
  [nodes-ids node-map]
  
  (if (first nodes-ids)
    (let [target-node (get node-map (first nodes-ids))]
      
      (cons
        target-node
        (nodes-from-nodes-ids (rest nodes-ids) node-map)))
    (list)))
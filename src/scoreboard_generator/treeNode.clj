(ns scoreboard-generator.treeNode
  (:gen-class)
  (:require [scoreboard-generator.invitation :as inv]))

(defn- create-node 
  "doc-string"
  
  ([^Integer id children-nodes-ids ^Boolean validity] 
   
   {:id id :children-nodes-ids children-nodes-ids :validity validity})
  
  ([invitation]
   
   (let [inviter (:inviter invitation)
         invitee (:invitee invitation)]
     
     (list (create-node inviter invitee false)
      (create-node invitee nil false)))))

(defn- create-node-list 
  "doc-string"
  [invitations]
  
  (if-let [target-invitation (first invitations)]
    (concat
      (create-node target-invitation)
      (create-node-list (rest invitations)))))

(defn- resolve-duplicates 
  "doc-string"
  [node-list]
  
  (distinct node-list))
    
(defn- validate 
  "doc-string"
  [node]
  
  (assoc node :validity true))

(defn- valid? 
  "doc-string"
  [node]
  
  (if (not= (:children-nodes-ids node) nil)
    true
    false))

(defn- create-validity-map 
  "doc-string"
  [node-list]
    (into 
      (sorted-map) 
      (for [target-node node-list
            :let [target-node-id (:id target-node)
                  validity (valid? target-node)]]
        [target-node-id {:validity validity}])))
  
(defn- conflicting-claims?
  "doc-string"
  [claimer1 claimer2]
  (if (and 
        (not= (:id claimer1) (:id claimer2))
        (= (:children-nodes-ids claimer1) (:children-nodes-ids claimer2))
        (not= (:children-nodes-ids claimer1) nil))
    true
    false))

(defn- resolve-conflicting-claims 
  "doc-string"
  [node-list]
  
  (if-let [target-node (first node-list)]
    (let [child-node-id (:children-nodes-ids target-node)
          resolved-list (remove #(conflicting-claims? target-node %) node-list)]
      (cons 
        target-node
        (resolve-conflicting-claims (rest resolved-list))))
    (list)))
    
(defn- group-by-id 
  "doc-string"
  [node-list]
  
  (group-by #(:id %) node-list))

(defn- merge-node-group 
  "doc-string"
  [node-group]
  
  (let [group (val node-group)
        commom-id (key node-group)
        commom-validity nil]
    
    [commom-id (create-node commom-id
              (for [node group
                    :let [children-nodes-ids (:children-nodes-ids node)]
                    :when (not= children-nodes-ids nil)]
                children-nodes-ids) commom-validity)]))
    
(defn- merge-related-nodes 
  "doc-string"
  [node-list]
  
  (let [node-groups (group-by-id node-list)
        merged-nodes (into (sorted-map) (map merge-node-group node-groups))]
    merged-nodes))

(defn- apply-validity-map 
  "doc-string"
  [validity-map node-map]
  
  (zipmap
    (keys node-map)
    (map #(merge (val %) (get validity-map (key %))) node-map)))
  
(defn parse-node-map-from-invitations
  "doc-string"
  [invitations]
  
  (let [base-node-list (create-node-list invitations)
        duplicate-free-node-list (resolve-duplicates base-node-list)
        validity-map (create-validity-map duplicate-free-node-list)
        conflict-free-node-list (resolve-conflicting-claims duplicate-free-node-list)
        consolidated-node-map (merge-related-nodes conflict-free-node-list)
        validated-node-map (apply-validity-map validity-map consolidated-node-map)]
    
    validated-node-map))

(defn nodes-from-nodes-ids 
  "docs string"
  [nodes-ids node-map]
  
  (if (first nodes-ids)
    (let [target-node (get node-map (first nodes-ids))]
      
      (cons
        target-node
        (nodes-from-nodes-ids (rest nodes-ids) node-map)))
    (list)))
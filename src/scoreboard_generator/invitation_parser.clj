(ns scoreboard-generator.invitation-parser)

(defn- validate 
  "Makrs a proto-node as being valid based on a given invitation. A proto-node is marked as valid if it has invited at least one other proto-node."
  [invitation]
  
  (let [proto-node (:inviter invitation)]
       
    [proto-node {:valid? true}]))

(defn- map-validity 
  "Maps the validity of all proto-nodes in given invitation list."
  [invitations]
  
  (into
    (sorted-map)
    (map validate invitations)))

(defn- get-firstborn 
  "Returns the first proto-node described by a given invitation list."
  [invitations]
  
  (:inviter (first invitations)))

(defn- remove-invitations-to-firstborn 
  "Removes all invitations to the firstborn proto-node in a fiven invitation list."
  [invitations]
  
  (let [firstborn (get-firstborn invitations)]
    (remove #(= (:invitee %) firstborn) invitations)))

(defn- conflicts?
  "Returns true if both invitations are distinct but have the same envitee. Otherwise returns false."
  [invitation1 invitation2]
  
  (if (and 
        (= (:invitee invitation1) (:invitee invitation2))
        (not= (:inviter invitation1) (:inviter invitation2)))
    true
    false))

(defn- remove-conflicting-invitations 
  "Removes all invitations to already invited nodes in a given invitation list."
  [invitations]
  
  (if-let [claimer (first invitations)]
    (let [conflict-free-invitations (remove #(conflicts? claimer %) invitations)]
      (cons 
        claimer
        (remove-conflicting-invitations (rest conflict-free-invitations))))
    (list)))

(defn- remove-invitations-to-invalid-proto-nodes 
  "Removes all invitations to invalid proto-nodes in a given invitation list)"
  [conflict-free-invitations validity-map]
  
  (filter #(contains? validity-map (:invitee %)) conflict-free-invitations))

(defn- filter-relevant-invitations 
  "Returns a list of invitations free of conflicts and invitations invalid proto-nodes from a given invitation list."
  [invitations]
  
  (let [validity-map (map-validity invitations)
        loop-free-invitations (remove-invitations-to-firstborn invitations)
        conflict-free-invitations (remove-conflicting-invitations loop-free-invitations)
        relevant-invitations (remove-invitations-to-invalid-proto-nodes conflict-free-invitations validity-map)]
  relevant-invitations))

(defn- get-parent 
  "Returns a vector containing the proto-node->parent pair described by the given invitation."
  [invitation]
  
  (let [proto-node (:invitee invitation)
        parent (:inviter invitation)]
    
      [proto-node {:parent parent}]))

(defn- map-parents 
  "Returns a map containing all proto-node->parent pairs described by a given invitation list."
  [conflict-free-invitations]
  
  (let [firstborn (get-firstborn conflict-free-invitations)]
    (into
      (sorted-map)
      (map get-parent conflict-free-invitations))))

(defn- get-child 
  "Returns a vector containing the proto-node->child pair described by the given invitation"
  [invitation]
  
  (let [proto-node (:inviter invitation)
        child (:invitee invitation)]
    
      [proto-node child]))

(defn- map-children 
  "Returns a map containing all proto-node->children pairs described by a given invitation list."
  [conflict-free-invitations]
  
  (let [children-by-proto-node (group-by #(first %) (map get-child conflict-free-invitations))]
    (into
      (sorted-map)
      (for [group children-by-proto-node
            :let [proto-node (key group)]]
        [proto-node {:children (mapcat (fn [x] (list (second x))) (val group))}]))))

(defn- merge-maps 
  "Returns a single merged map from two given maps of maps. Entries with the same key are combined."
  [map1 map2]
  
  (merge-with merge map1 map2))

(defn parse-invitations 
  "Returns a map of all proto-nodes, their corresponding parents and children, described by a given invitation list."
  [invitations]
  
  (let [relevant-invitations (filter-relevant-invitations invitations)
        parent-map (map-parents relevant-invitations)
        children-map (map-children relevant-invitations)
        proto-node-map (merge-maps parent-map children-map)]
    proto-node-map))
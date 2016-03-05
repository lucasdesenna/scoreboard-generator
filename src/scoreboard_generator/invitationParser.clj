(ns scoreboard-generator.invitationParser
  (:gen-class))

(defn- validate 
  "doc-string"
  [invitation]
  
  (let [proto-node (:inviter invitation)]
       
    [proto-node {:valid? true}]))

(defn- map-validity 
  "doc-string"
  [invitations]
  
  (into
    (sorted-map)
    (map validate invitations)))

(defn- get-first-born 
  "doc-string"
  [invitations]
  
  (:inviter (first invitations)))

(defn- remove-invitations-to-first-born 
  "doc-string"
  [invitations]
  
  (let [first-born (get-first-born invitations)]
    (remove #(= (:invitee %) first-born) invitations)))

(defn- conflicts?
  "doc-string"
  [claimer contender]
  
  (if (and 
        (= (:invitee claimer) (:invitee contender))
        (not= (:inviter claimer) (:inviter contender)))
    true
    false))

(defn- remove-conflicting-invitations 
  "doc-string"
  [invitations]
  
  (if-let [claimer (first invitations)]
    (let [conflict-free-invitations (remove #(conflicts? claimer %) invitations)]
      (cons 
        claimer
        (remove-conflicting-invitations (rest conflict-free-invitations))))
    (list)))

(defn- remove-invitations-to-invalid-proto-nodes 
  "doc-string"
  [conflict-free-invitations validity-map]
  
  (filter #(contains? validity-map (:invitee %)) conflict-free-invitations))

(defn- filter-relevant-invitations 
  "doc-string"
  [invitations]
  
  (let [validity-map (map-validity invitations)
        loop-free-invitations (remove-invitations-to-first-born invitations)
        conflict-free-invitations (remove-conflicting-invitations loop-free-invitations)
        relevant-invitations (remove-invitations-to-invalid-proto-nodes conflict-free-invitations validity-map)]
  relevant-invitations))

(defn- get-parent 
  "doc-string"
  [invitation]
  
  (let [proto-node (:invitee invitation)
        parent (:inviter invitation)]
    
      [proto-node {:parent parent}]))

(defn- map-parents 
  "doc-string"
  [conflict-free-invitations]
  
  (let [first-born (get-first-born conflict-free-invitations)]
    (into
      (sorted-map)
      (map get-parent conflict-free-invitations))))

(defn- get-child 
  "doc-string"
  [invitation]
  
  (let [proto-node (:inviter invitation)
        child (:invitee invitation)]
    
      [proto-node child]))

(defn- map-children 
  "doc-string"
  [conflict-free-invitations]
  
  (let [children-by-proto-node (group-by #(first %) (map get-child conflict-free-invitations))]
    (into
      (sorted-map)
      (for [group children-by-proto-node
            :let [proto-node (key group)]]
        [proto-node {:children (mapcat (fn [x] (list (second x))) (val group))}]))))

(defn- merge-maps 
  "doc-string"
  [parent-map children-map]
  
  (merge-with merge parent-map children-map))

(defn parse-invitations 
  "doc-string"
  [invitations]
  
  (let [relevant-invitations (filter-relevant-invitations invitations)
        parent-map (map-parents relevant-invitations)
        children-map (map-children relevant-invitations)
        proto-node-map (merge-maps parent-map children-map)]
    proto-node-map))
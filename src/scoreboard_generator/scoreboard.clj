(ns scoreboard-generator.scoreboard
  (:gen-class))

(defn- exp [x n]
  (reduce * (repeat n x)))

(defn- award-points 
  "doc-string"
  [children-count level]
  
  (if (>= level 0)
    (* children-count (exp 0.5 level))
    0))

(defn- get-score 
  "doc-string"
  [node-map customer-id]
  
  (loop [score 0
         level 0
         current-node-children (:children (get node-map customer-id))]
    (if (not-empty current-node-children)
      (recur 
        (+ score (award-points (count current-node-children) level))
        (inc level)
        (doall (mapcat #(:children (get node-map %)) current-node-children)))
      score)))
    
(defn get-scoreboard 
  "doc-string"
  [customer-map]
  
  (into
    (sorted-map)
    (for [customer customer-map
          :let [customer-id (key customer)]]
      [customer-id (get-score customer-map customer-id)])))
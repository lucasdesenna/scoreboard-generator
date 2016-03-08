(ns scoreboard-generator.scoreboard-factory  
  (:require [flatland.ordered.map :as fl]))

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
    
(defn- create-score-map 
  "doc-string"
  [customer-map]
  
  (into 
    (sorted-map)
    (for [customer customer-map
          :let [customer-id (key customer)]]
      [customer-id (get-score customer-map customer-id)])))

(defn- sort-score-map 
  "doc-string"
  [scoreboard]
  (into
    (fl/ordered-map)
    (sort-by val > scoreboard)))
  
(defn create-scoreboard 
  "doc-string"
  [customer-map]
  
  (let [scoreboard (create-score-map customer-map)
        sorted-scoreboard (sort-score-map scoreboard)]
    sorted-scoreboard))
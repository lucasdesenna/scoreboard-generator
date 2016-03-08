(ns scoreboard-generator.scoreboard-factory  
  (:require [flatland.ordered.map :as fl]))

(defn- exp [x n]
  "Returns x to the power of n"
  (reduce * (repeat n x)))

(defn- calculate-points 
  "Returns the total number of points awarded by having a given number of children at a given level."
  [children-count level]
  
  (if (>= level 0)
    (* children-count (exp 0.5 level))
    0))

(defn- get-score 
  "Returns the total score for a node given it's key and container node-map."
  [node-key node-map]
  
  (loop [score 0
         level 0
         current-node-children (:children (get node-map node-key))]
    (if (not-empty current-node-children)
      (recur 
        (+ score (calculate-points (count current-node-children) level))
        (inc level)
        (doall (mapcat #(:children (get node-map %)) current-node-children)))
      score)))
    
(defn- create-score-map 
  "Returns a score map containing the total score of all nodes in a given node-map."
  [node-map]
  
  (into 
    (sorted-map)
    (for [node node-map
          :let [node-key (key node)]]
      [node-key (get-score node-key node-map)])))

(defn- sort-score-map 
  "Returns a scoreboard by sorting the score of each entry in a score-map (high scores first)."
  [score-map]
  
  (into
    (fl/ordered-map)
    (sort-by val > score-map)))
  
(defn create-scoreboard 
  "Returns a scoreboard from a node-map."
  [node-map]
  
  (let [score-map (create-score-map node-map)
        scoreboard (sort-score-map score-map)]
    scoreboard))
(ns scoreboard-generator.scoreboard
  (:gen-class)
  (:require [scoreboard-generator.forest :as forest]
            [clojure.tools.trace :as trace]))

(defn- exp [x n]
  (reduce * (repeat n x)))

(defn- ^:dynamic award-points 
  "doc-string"
  [level]
  
  (if (>= level 0)
    (exp 0.5 level)
    0))

(defn- remove-invalid-customers 
  "doc-string"
  [customer-forest]
  
  (forest/trim-leafs customer-forest))

(defn- ^:dynamic  get-score 
  "doc-string"
  
  ([customer-forest]
    
    (if-let [customer-tree (first customer-forest)]
      (let [label (first customer-tree)]
        (merge
          {label (get-score (list customer-tree) -1)}
          (get-score (rest customer-forest))))
      {}))
  
  ([customer-tree-or-branches level]
    
  (if-let [target-tree-or-branch (first customer-tree-or-branches)]
    (let [target-children (second target-tree-or-branch)]
        (+
          (award-points level)
          (get-score (rest customer-tree-or-branches) level)
          (get-score target-children (inc level))))
    0)))
    
(defn ^:dynamic  get-scoreboard 
  "doc-string"
  [customer-forest]
  
  (let [scoreboard (get-score customer-forest)]
    scoreboard))
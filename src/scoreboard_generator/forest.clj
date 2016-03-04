(ns scoreboard-generator.forest
  (:gen-class)
  (:require [scoreboard-generator.tree :as tree]))

(defn create-forest
  "doc-string" 
  [invitations]
  
  (let [forest (tree/create-tree invitations)]
    
  forest))

(defn trim-leafs 
  "doc-string"
  [forest]
  
  (let [trimmed-forest (tree/trim-leafs forest false)]
    trimmed-forest))
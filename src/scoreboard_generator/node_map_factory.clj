(ns scoreboard-generator.node-map-factory)

(defrecord Node [^Integer id ^Integer parent ^clojure.lang.PersistentList children])

(defn- create-node 
  "doc-string"
  
  ([^Integer id ^Integer parent ^clojure.lang.PersistentList children] 
   
   (->Node id parent children))
  
  ([proto-node]
   
   (let [id (key proto-node)
         parent (:parent (val proto-node))
         children (:children (val proto-node))]
     
     [id (create-node id parent children)])))

(defn create-node-map 
  "doc-string"
  [proto-node-map]
  (into
    (sorted-map)  
    (map create-node proto-node-map)))
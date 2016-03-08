(ns scoreboard-generator.node-map-factory)

(defrecord Node [^Integer id ^Integer parent ^clojure.lang.PersistentList children])

(defn- create-node 
  "Returns a Node record from either a series of parameters or a proto-node."
  
  ([^Integer id ^Integer parent ^clojure.lang.PersistentList children] 
   
   (->Node id parent children))
  
  ([proto-node]
   
   (let [id (key proto-node)
         parent (:parent (val proto-node))
         children (:children (val proto-node))]
     
     [id (create-node id parent children)])))

(defn create-node-map 
  "Returns a node-map from a proto-node-map."
  [proto-node-map]
  
  (into
    (sorted-map)  
    (map create-node proto-node-map)))
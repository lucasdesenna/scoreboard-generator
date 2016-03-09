(ns scoreboard-generator.input-parser
  (:require [scoreboard-generator.invitation :as inv]
            [clojure.string :as string]))

(defn- extract-lines
  "Extracts all lines from file and returns them as a list of single line strings."
  [file]
    
    (let [reader clojure.java.io/reader 
          stream (reader file) 
          lines (with-open [line-sequence stream] (doall (line-seq line-sequence)))]
      lines))

(defn- line-valid? 
  "Returns true if line contains two integers and is not a self-invitation. Returns false otherwise."
  [line]
  
  (let [integers (re-seq #"\d+" line)]
    (if (and 
          (= (count integers) 2)
          (not= (first integers) (second integers)))
      true
      false)))

(defn- ignore-line 
  "Warns that a line was ignored and explains why it was so. Returns nil."
  [line line-number]
  
  (let [warning (format "\nWarning: Line #%d '%s' was deemed invalid and was ignored." line-number line)]
    (println warning)
    nil))

(defn- validate-line 
  "Returns valid lines. Triggers a warning and returns nil, when provided with a invalid line."
  [line line-number]
  
  (if (line-valid? line)
    line
    (ignore-line line line-number)))

(defn- remove-invalid-lines 
  "Removes invalid lines from line list. Everytime a line is removed a warning is triggered."
  [lines]
    
  (let [line-vector (vec lines)
        valid-lines (for [line line-vector
                                :let [line-number (inc (.indexOf lines line))
                                      valid-line (validate-line line line-number)]
                                :when valid-line] 
                            line)]
  
  valid-lines))

(defn- parse-lines
  "Parses all valid lines into a list of invitations."
  [lines]
  
  (for [line lines 
        :let [invitation (inv/create-invitation line)]
        :when invitation] 
    invitation))

(defn parse-file 
  "Parses file it into a list of invitations. Returns nil if no valid lines are found"
  [file]
  
  (let [lines (extract-lines file)
        valid-lines (remove-invalid-lines lines)]
    
    (if (not-empty valid-lines)
      (parse-lines valid-lines)
      nil)))

(defn- extract-lines-from-csv 
  "Extracts all lines from a csv string and returns them as a list of strings."
  [csv]
  
  (string/split csv #";\s?"))

(defn parse-csv 
  "Parses a csv string into a list of invitations. Returns nil if no valid invitations are found"
  [csv]
  
  (let [lines (extract-lines-from-csv csv)
        valid-lines (remove-invalid-lines lines)]
    
    (if (not-empty valid-lines)
      (parse-lines valid-lines)
      nil)))

(defn concat-invitations 
  "Concatenates two invitation lists."
  [invitation-list1 invitation-list2]
  
  (concat invitation-list1 invitation-list2))
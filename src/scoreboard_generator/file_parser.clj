(ns scoreboard-generator.file-parser
  (:gen-class)
  (:require [scoreboard-generator.invitation :as inv]))

(defn- extract-lines-from-file
  "Extracts all lines from the input file and returns them as a list of single line strings."
  [file-path]
  (let [reader clojure.java.io/reader 
        stream (reader file-path) 
        lines (with-open [line-sequence stream] (doall (line-seq line-sequence)))]
    lines))

(defn- parse-invitations-from-lines
  "Parses all valid lines from the input collection into a list of invitations."
  [lines]
  (for [line lines 
        :let [invite (inv/create-invitation line)]
        :when invite] 
    invite))

(defn parse-file 
  "Parses the file designated by the path into a list of invitations."
  [file-path]
  (let [lines (extract-lines-from-file file-path) 
        invitations (parse-invitations-from-lines lines)] 
    invitations))
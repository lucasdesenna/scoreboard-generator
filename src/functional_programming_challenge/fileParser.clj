(ns functional-programming-challenge.fileParser
  (:gen-class))
  
(defn -extract-lines 
  "Extracts all lines from the input file and returns them as a list of single line strings."
  [file-path]
  (let [
    reader clojure.java.io/reader
    stream (reader file-path)
    lines (with-open [line-sequence stream]
      (doall (line-seq line-sequence)))]
  
    lines))

(defn -extract-inviter-and-invitee
  "Extracts inviter and invitee pair from a single line string."
  [line]
  (let [
    pattern #"(\d+) (\d+)"
    inviter-and-invitee (rest (re-matches pattern line))]
  
    inviter-and-invitee))

(defn -parse-into-invitation
  "Parses a single line input string into an invitation."
  [line]
  (let [
    inviter-and-invitee (-extract-inviter-and-invitee line)
    invitation {:inviter (first inviter-and-invitee) :invitee(second inviter-and-invitee)}]
    
    invitation))

(defn -invitation-valid?
  "Checks if the input invitation is valid."
  [invitation]
  (let [condition (and
    (= (type (invitation :inviter)) java.lang.String)
    (= (type (invitation :invitee)) java.lang.String)
    (> (count (invitation :inviter)) 0)
    (> (count (invitation :invitee)) 0))]
    
  (if condition
    true
    ((print (str "Invitation " invitation " was deemed invalid and was ignored"))
     false))))

(defn -parse-valid-lines
  "Parses all valid lines from input list into a list of invitations."
  [lines]
  (for [line lines
    :let [invitation (-parse-into-invitation line)]
    :when (-invitation-valid? invitation)]
  invitation))

(defn parse-file 
  "Parses the file designated by the path into a list of invitations."
  [file-path]
  (let [
    lines (-extract-lines file-path)
    invitations (-parse-valid-lines lines)]
    
    invitations))
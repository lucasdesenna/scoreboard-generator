(ns functional-programming-challenge.core
  (:gen-class))

(require 'functional-programming-challenge.fileParser)
(refer 'functional-programming-challenge.fileParser)

(defn -main
  "I don't do a whole lot ... yet."
  []
  (def test-file "resources/input.txt")
  (parse-file test-file))

(-main)
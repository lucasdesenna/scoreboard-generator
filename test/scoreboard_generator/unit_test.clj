(ns scoreboard-generator.unit-test
  (:require [clojure.test :refer :all]
            [scoreboard-generator.test-resources :as tr]
            [scoreboard-generator.input-parser :as input-parser]
            [scoreboard-generator.invitation-parser :as inv-parser]
            [scoreboard-generator.scoreboard-factory :as scrbd-factory]))

(deftest basic-file-parsing
  (testing 
    "Tests capacity to parse a short and properly formated input file into an invitation list."
    
    (let [test-input tr/basic-input
          invitations (input-parser/parse-file test-input)]
      (is (= invitations
             tr/basic-invitations)))))

(deftest basic-node-mapping
  (testing 
    "Tests capacity to parse a short list of invitations into an accurate node map."
    
    (let [test-invitations tr/basic-invitations
          node-map (inv-parser/parse-invitations test-invitations)]
      (is (= node-map
             tr/basic-node-map)))))

(deftest basic-scorekeeping
  (testing 
    "Tests capacity to parse a short node map into a scoreboard while complying with all scoring rules."
    
    (let [test-node-map tr/basic-node-map
          scoreboard (scrbd-factory/create-scoreboard test-node-map)]
      (is (= scoreboard
             tr/basic-scoreboard)))))

(deftest standard-file-parsing
  (testing 
    "Tests capacity to parse an input file of standard size and complexity into an invitation list."
    
    (let [test-input tr/standard-input
          invitations (input-parser/parse-file test-input)]
      (is (= invitations
             tr/standard-invitations)))))

(deftest standard-node-mapping
  (testing 
    "Tests capacity to parse an invitation list of standard size and complexity into an accurate node map."
    
    (let [test-invitations tr/standard-invitations
          node-map (inv-parser/parse-invitations test-invitations)]
      (is (= node-map
             tr/standard-node-map)))))

(deftest standard-scorekeeping
  (testing 
    "Tests capacity to parse a node map of standard size and complexity into a scoreboard while complying with all scoring rules."
    
    (let [test-node-map tr/standard-node-map
          scoreboard (scrbd-factory/create-scoreboard test-node-map)]
      (is (= scoreboard
             tr/standard-scoreboard)))))

(deftest dirty-file-parsing
  (testing 
    "Tests capacity to parse a file containing some invalid lines into an invitation list."
    
    (let [test-input tr/dirty-input
          invitations (input-parser/parse-file test-input)]
      (is (= (count invitations)
             162)))))

(deftest long-file-parsing
  (testing 
    "Tests capacity to parse a long (5,000 entries), but properly formatted, input file into an invitation list."
    
    (let [test-input tr/long-input
          invitations (input-parser/parse-file test-input)]
      (is (= (count invitations)
             5000)))))

(deftest invalid-file-parsing
  (testing 
    "Tests capacity to deal with a file containing no valid input."
    
    (let [test-input tr/invalid-input
          invitations (input-parser/parse-file test-input)]
      (is (= (count invitations)
             0)))))

(deftest corrupt-file-parsing
  (testing 
    "Tests capacity to deal with a corrupted file."
    
    (let [test-input tr/corrupt-input
          invitations (input-parser/parse-file test-input)]
      (is (= (count invitations)
             0)))))
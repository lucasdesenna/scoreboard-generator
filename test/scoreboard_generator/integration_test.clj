(ns scoreboard-generator.integration-test
  (:require [clojure.test :refer :all]
            [scoreboard-generator.test-resources :as tr]
            [scoreboard-generator.controller :as controller]))

(deftest basic-file-to-scoreboard
  (testing 
    "Tests capacity to parse a short and properly formated input file into an accurate scoreboard."
    
    (let [test-input tr/basic-input
          invitations (controller/parse-file test-input)
          scoreboard (controller/create-scoreboard invitations)]
      (is (= scoreboard
             tr/basic-scoreboard)))))

(deftest standard-file-to-scoreboard
  (testing 
    "Tests capacity to parse an input file of standard size and complexity into an accurate scoreboard."
    
    (let [test-input tr/standard-input
          invitations (controller/parse-file test-input)
          scoreboard (controller/create-scoreboard invitations)]
      (is (= scoreboard
             tr/standard-scoreboard)))))

(deftest basic-invitation-addition
  (testing 
    "Tests capacity to add a new invitation to an existing short size invitation list."
    
    (let [test-invitations tr/basic-invitations
          test-inviter 1
          test-invitee 250
          updated-invitations (controller/add-invitation test-invitations test-inviter test-invitee)]
      (is (and (= (:inviter (last updated-invitations)) test-inviter)
               (= (:invitee (last updated-invitations)) test-invitee))))))

(deftest standard-invitation-addition
  (testing 
    "Tests capacity to add a new invitation to an existing standard size invitation list and parse the new list into an accurate scoreboard."
    
    (let [test-invitations tr/standard-invitations
          test-inviter 1
          test-invitee 500
          updated-invitations (controller/add-invitation test-invitations test-inviter test-invitee)]
      (is (and (= (:inviter (last updated-invitations)) test-inviter)
               (= (:invitee (last updated-invitations)) test-invitee))))))
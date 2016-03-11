(ns scoreboard-generator.resources
  (:require [clojure.java.io :as io]))

(defonce head (slurp 
                     (io/file 
                       (io/resource "views/head.html"))))

(defonce foot (slurp 
                     (io/file 
                       (io/resource "views/foot.html"))))

(defonce title (slurp 
                     (io/file 
                       (io/resource "views/title.html"))))

(defonce toolbar-init (slurp 
                     (io/file 
                       (io/resource "views/toolbar_init.html"))))

(defonce toolbar (slurp 
                     (io/file 
                       (io/resource "views/toolbar.html"))))


(defonce goodbye (slurp 
                     (io/file 
                       (io/resource "views/goodbye.html"))))
(ns scoreboard-generator.resources
  (:require [clojure.java.io :as io]))

(defonce head (slurp (io/resource "views/head.html")))

(defonce foot (slurp (io/resource "views/foot.html")))

(defonce title (slurp (io/resource "views/title.html")))

(defonce toolbar-init (slurp (io/resource "views/toolbar_init.html")))

(defonce toolbar (slurp (io/resource "views/toolbar.html")))

(defonce goodbye (slurp (io/resource "views/goodbye.html")))
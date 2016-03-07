(defproject scoreboard-generator "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/lucasdesenna/viral-campaign-scoreboard"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.flatland/ordered "1.5.3"]
                 [clj-http "2.1.0"]
                 [cheshire "5.5.0"]
                 [http-kit "2.1.18"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.4.0"]]
  :main ^:skip-aot scoreboard-generator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

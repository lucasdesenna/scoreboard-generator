(defproject scoreboard-generator "0.1.0"
  :description "Parses a properly formatted invitation list into a customer scoreboard and uploads it as a JSON object to the specified URI. The user is then given the option to pick a new file for submission or end the program."
  :url "https://github.com/lucasdesenna/viral-campaign-scoreboard"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.5.0"]
                 [org.flatland/ordered "1.5.3"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.4.0"]
                 [liberator "0.13"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.2.1"]
                 [hiccup "1.0.5"]]
  :main scoreboard-generator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot [scoreboard-generator.core]}})

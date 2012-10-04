(defproject exchange "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [compojure "1.1.3"]
                           [enlive "1.0.1" :exclusions [org.clojure/clojure]]
                           [com.cemerick/friend "0.1.0" :exclusions [ring/ring-core]]
                           [korma "0.3.0-beta11"]
                           [postgresql "9.1-901.jdbc4"]
                           [ring-anti-forgery "0.2.0"]
                           [environ "0.3.0"]
                           [useful "0.8.4"]
                           [clj-time "0.4.4"]
                           [valip "0.2.0"]
                           [clj-http "0.5.3"]
                           [lobos "1.0.0-SNAPSHOT"]]
            :plugins [[lein-ring "0.7.3"]]
            :ring {:handler exchange.handler/app}
            :profiles
            {:dev {:dependencies [[ring-mock "0.1.3"]]}})

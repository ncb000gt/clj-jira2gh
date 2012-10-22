(defproject clj-jira2gh "0.1.0-SNAPSHOT"
  :description "Convert from Jira to GH Issues"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "http://ncb000gt.mit-license.org/"}
  :dependencies [
                 [org.clojure/clojure "1.4.0"]
                 [http.async.client "0.5.0-SNAPSHOT"]
                 [cheshire "4.0.3"]
                 [tentacles "0.2.0-beta1"]]
  :dev-dependencies [
                     [clj-stacktrace "0.2.4"]]
  :plugins [
            [lein-marginalia "0.7.1"]]
  :source-paths ["src"]
  :main clj-jira2gh.core)

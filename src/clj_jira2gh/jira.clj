(ns clj-jira2gh.jira
  (:require [clojure.string :as str])
  (:require [http.async.client :as c])
  (:use [clojure.pprint]
        [cheshire.core])
  (:gen-class))

(def jira-api-path "/rest/api/latest/")

(defn getr 
  [url cookies]
  (with-open [client (c/create-client)]
    (let [response (c/GET client url :cookies cookies)]
      (c/await response)
      (c/string response))))

(defn login
  [prefix username password]
  (with-open [client (c/create-client)]
    (let [response (c/POST client (str/join "" [prefix jira-api-path "session"])
                           :headers {:Content-Type "application/json"}
                           :body (generate-string {:username username :password password}))]
      (c/await response)
      (c/cookies response))))

(defn projects
  [prefix cookies]
  (parse-string (getr (str/join "" [prefix jira-api-path "project"]) cookies)) true)

(defn project
  [prefix cookies project]
  (parse-string (getr (str/join "" [prefix jira-api-path "project/" project]) cookies)) true)

(defn issues
  [prefix cookies project n]
  (:issues (parse-string (getr (str/join "" [prefix jira-api-path "search/?jql=project=" project "&maxResults=" n]) cookies) true)))

(ns clj-jira2gh.core
  (:require [clj-jira2gh.jira :as jira]
            [clj-jira2gh.gh :as gh])
  (:use [clojure.pprint])
  (:gen-class))

(defn close
  [gh_repo gh_un gh_pw]
  (pprint (str "Closing all issues at https://github.com/" gh_un "/" gh_repo))
  (gh/close-all-issues gh_repo gh_un gh_pw)
  (pprint "Done."))

(defn copy
  [jira_prefix jira_un jira_pw jira_project n gh_un gh_pw gh_repo & args]
  (pprint (str "Copying " n " issues from " jira_prefix " to https://github.com/" gh_un "/" gh_repo))
  (gh/close-issues gh_repo gh_un gh_pw
    (map
      #(:number %) 
      (filter
        #(not= (:resolutiondate (:issue %)) nil)
        (gh/add-issues gh_repo gh_un gh_pw
                       (map 
                         (fn [issue] 
                           (into (into (into {:url (str jira_prefix "/browse/" (:key issue))} 
                                             (select-keys issue [:key]))
                                       (select-keys (issue :fields) [:description :summary :resolutiondate]))
                                 (select-keys ((issue :fields) :reporter) [:emailAddress])))
                         (jira/issues jira_prefix (jira/login jira_prefix jira_un jira_pw) jira_project n)) []))) [])
  (pprint "Done."))

(defn -main
  [opt jira_prefix jira_un jira_pw jira_project n gh_un gh_pw gh_repo & args]
  (if (= opt "close")
    (close gh_repo gh_un gh_pw))
  (if (= opt "copy")
    (copy jira_prefix jira_un jira_pw jira_project n gh_un gh_pw gh_repo)))



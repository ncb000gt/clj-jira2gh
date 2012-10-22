(ns clj-jira2gh.gh
  (:use [clojure.pprint]
        [tentacles.issues :only [create-issue edit-issue issues]])
  (:gen-class))

(defn add-issue [gh_repo gh_un gh_pw issue]
  (if-not (= issue nil)
    (into 
      (create-issue gh_un gh_repo (:summary issue) {
                                                    :auth (str gh_un ":" gh_pw)
                                                    :assignee gh_un
                                                    :body (str (:description issue) "\n\n\nCopied from: " (:url issue))
                                                    }) {:issue issue})))

(defn add-issues [gh_repo gh_un gh_pw iz a]
  (if (= (seq iz) nil)
    a
    (add-issues gh_repo gh_un gh_pw (rest iz) (conj a (add-issue gh_repo gh_un gh_pw (first iz))))))

(defn close-issue [gh_repo gh_un gh_pw id]
  (edit-issue gh_un gh_repo id {
                                :auth (str gh_un ":" gh_pw)
                                :state "closed"
                                }))

(defn close-issues [gh_repo gh_un gh_pw k a]
  (if (= (seq k) nil)
    a
    (close-issues gh_repo gh_un gh_pw (rest k) (conj a (close-issue gh_repo gh_un gh_pw (first k))))))

(defn close-all-issues [gh_repo gh_un gh_pw]
  (def page (close-issues gh_repo gh_un gh_pw
                (map
                  (fn [issue]
                    (:number issue))
                  (issues gh_un gh_repo)) []))
  (if (not= (count page) 0)
    (close-all-issues gh_repo gh_un gh_pw)))

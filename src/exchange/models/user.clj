(ns exchange.models.user
  (:require valip.core
            [valip.predicates :refer :all]
            [clj-time [core :as ctime] [coerce :as coerce]]
            [korma.core :refer :all]
            [cemerick.friend :as friend]
            [exchange.entities :refer :all]))

(defn now-sql []
  (coerce/to-timestamp (ctime/now)))

(defn create! [attributes]
  (let [now (now-sql)
        final (into
                (select-keys attributes [:email
                                         :first_name
                                         :last_name
                                         :password])
                {:join_date now
                 :last_login now
                 :role "user"})]
    (insert user (values final))))

(defn find-by-email [email]
  (first (select user (where {:email (when email
                                       (clojure.string/lower-case email))}))))

(defn load-from-session []
  (find-by-email (:username (friend/current-authentication))))

(defn validate [u]
  (valip.core/validate u
    [:email email-address? "Please provide a valid email address."]
    [:email #(empty? (find-by-email %))
            "The supplied email address has already been registered."]
    [:first_name present? "Please provide a first name."]
    [:password present? "Please provide a password."]))

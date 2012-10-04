(ns lobos.config
  (:require [lobos.connectivity :refer :all]
            [environ.core :refer :all]))

(defn subname [{:keys [host port db]}]
  (str "//" host ":" port "/" db))

(def db-conn
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user (get (env :pgsql) :user)
   :password (get (env :pgsql) :password)
   :subname (subname (env :pgsql))})

(open-global db-conn)

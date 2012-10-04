(ns lobos.helpers
  (:refer-clojure :exclude [bigint boolean char double float time])
  (:require useful.string
            [lobos.schema :refer :all]))

(defn pk [table]
  (keyword (str (useful.string/underscore (name table)) "_id")))

(defn surrogate-key [table]
  (integer
    table
    (pk (:name table))
    :auto-inc
    :primary-key))

(defn timestamps [table]
  (-> table
      (timestamp :updated_on :time-zone)
      (timestamp :created_on :time-zone (default (now)))))

(defn refer-to [table ptable]
  (let [cname (pk ptable)]
    (integer table cname [:refer ptable cname :on-delete :set-null])))

(defmacro tbl [name & elements]
  `(-> (table ~name)
       (timestamps)
       ~@(reverse elements)
       (surrogate-key)))

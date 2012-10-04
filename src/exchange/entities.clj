(ns exchange.entities
  (:require [cemerick.friend.credentials :as creds]
            [korma.core :refer :all]
            [exchange.databases.postgresql :refer :all]
            clojure.string))

(defentity author
  (table :Author)
  (database pgsql)
  ; TODO: When many-to-many is supported
  ; (many-to-many book :Author__Book {:lfk :author_id :rfk :book_id)
  )

(defentity author-book
  (table :Author__Book)
  (database pgsql))

(defentity book
  (table :Book)
  (database pgsql)
  ; TODO: When many-to-many is supported
  ; (many-to-many author :Author__Book {:lfk :book_id :rfk :author_id)
  )

(declare listing)
(declare offer)
(declare login-token)
(declare notification)
(defentity user
  (table :User)
  (database pgsql)
  (prepare #(update-in % [:email] clojure.string/lower-case))
  (prepare #(update-in % [:password] creds/hash-bcrypt))
  (has-many listing)
  (has-many offer)
  (has-many login-token)
  (has-many notification))

(defentity listing
  (table :Listing)
  (database pgsql)
  (belongs-to book)
  (belongs-to user)
  (has-many offer))

(defentity login-token
  (table :Login_token)
  (database pgsql)
  (belongs-to user))

(defentity notification
  (table :Notification)
  (database pgsql)
  (belongs-to user))

(defentity offer
  (table :Offer)
  (database pgsql)
  (belongs-to listing)
  (belongs-to user))

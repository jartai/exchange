(ns lobos.migrations
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])
  (:require (lobos [migration :refer [defmigration]]
                   [core :refer :all]
                   [schema :refer :all]
                   [config :refer :all]
                   [helpers :refer :all])))

(defmigration add-book-table
  (up [] (create
           (tbl :Book
              (varchar :authors 255)
              (varchar :cover_large 255)
              (varchar :cover_medium 255)
              (varchar :cover_small 255)
              (integer :number_of_pages)
              (varchar :publish_date 255)
              (varchar :publishers 255)
              (varchar :subtitle 255)
              (varchar :title 255)
              (varchar :url 255)
              (varchar :weight 255)
              (decimal :external_average_price 19 4)
              (decimal :internal_average_price 19 4)
              (varchar :isbn_10 255)
              (varchar :isbn_13 255))))
  (down [] (drop (table :Book))))

(defmigration add-user-table
  (up [] (create
          (tbl :User
             (varchar :email 255 :unique :not-null)
             (varchar :first_name 255 :not-null)
             (varchar :last_name 255 :not-null)
             (varchar :password 255)
             (timestamp :join_date :time-zone)
             (timestamp :last_login :time-zone))))
  (down [] (drop (table :User))))

(defmigration add-listing-table
  (up [] (create
           (tbl :Listing
              (varchar :state 255 (default "active"))
              (varchar :condition 255)
              (text :description)
              (decimal :ideal_price 19 4)
              (decimal :minimum_price 19 4)
              (refer-to :Book)
              (refer-to :User))))
  (down [] (drop (table :Listing))))

(defmigration add-login-token-table
  (up [] (create
           (tbl :LoginToken
              (varchar :token 255 :not-null)
              (refer-to :User))))
  (down [] (drop (table :LoginToken))))

(defmigration add-notification-table
  (up [] (create
           (tbl :Notification
              (varchar :type 255)
              (text :message 255)
              (refer-to :User))))
  (down [] (drop (table :Notification))))

(defmigration add-offer-table
  (up [] (create
           (tbl :Offer
              (decimal :price 19 4)
              (boolean :won (default false))
              (refer-to :Listing)
              (refer-to :User))))
  (down [] (drop (table :Offer))))

(defmigration add-roles-column
  (up [] (alter :add (table :User (varchar :role 255))))
  (down [] (alter :drop (table :User (column :role)))))

(defmigration add-author-table
  (up [] (create
           (tbl :Author
              (varchar :name 255)))
         (create
           (table :Author__Book
             (integer :author_id [:refer :Author :author_id :on-delete :set-null])
             (integer :book_id [:refer :Book :book_id :on-delete :set-null])
             (primary-key [:author_id :book_id])))
         (alter :drop (table :Book (column :authors))))
  (down [] (drop (table :Author))
           (drop (table :Author__Book))
           (alter :add (table :Book (varchar :authors 255)))))

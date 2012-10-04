(ns exchange.models.book
  (:require [korma.core :refer :all]
            [exchange.entities :refer :all]
            [clj-http.client :as client]))

(defn to-delimited-list [d m k]
  (clojure.walk/walk #(get % k) #(apply str (interpose d %)) m))

(defn pull-from-open-library [isbn]
  (let [resp (client/get (str "http://openlibrary.org/api/books?bibkeys=ISBN:"
                              isbn
                              "&jscmd=data&format=json") {:as :json})
        top-key (keyword (str "ISBN:" isbn))
        book-map (get (:body resp) top-key)
        cover (:cover book-map)
        identifiers (:identifiers book-map)]
    (-> (select-keys book-map [:publish_date
                               :number_of_pages
                               :weight
                               :subtitle
                               :title
                               :url])
        (assoc :authors (vec (for [{:keys [name]} (:authors book-map)]
                               (identity name)))
               :publishers (to-delimited-list "/" (:publishers book-map) :name)
               :cover_large (:large cover)
               :cover_medium (:medium cover)
               :cover_small (:small cover)
               :isbn_10 (first (:isbn_10 identifiers))
               :isbn_13 (first (:isbn_13 identifiers))))))

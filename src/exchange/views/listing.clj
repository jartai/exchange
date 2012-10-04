(ns exchange.views.listing
  (:require [net.cgrand.enlive-html :as html]
            [exchange.views.common :as common]
            [exchange.auth :as auth]
            [exchange.models.user :as user]
            [ring.middleware.anti-forgery :refer :all]
            [cemerick.friend :as friend]))

(html/deftemplate create-new-tpl "templates/listing/create_new.html"
  [u]
  [:#header] (html/substitute (common/header u)))

(defn create-new []
  (let [u (user/load-from-session)]
    (create-new-tpl u)))

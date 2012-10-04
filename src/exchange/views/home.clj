(ns exchange.views.home
  (:require [net.cgrand.enlive-html :as html]
            [cemerick.friend :as friend]
            [exchange.views.common :as common]
            [exchange.models.user :as user]))

(html/deftemplate index-tpl "templates/home/index.html"
  [u]
  [:#header] (html/substitute (common/header u))
  [:#foo] (html/content (friend/current-authentication)))

(defn index []
  (let [u (user/load-from-session)]
    (index-tpl u)))

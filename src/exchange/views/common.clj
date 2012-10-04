(ns exchange.views.common
  (:require [net.cgrand.enlive-html :as html]))

(html/defsnippet header-logged-in "templates/partial/header.html"
  [:.logged-in] [first-name]
  [:#first-name] (html/content first-name))

(html/defsnippet header-logged-out "templates/partial/header.html"
  [:.logged-out] [])

(defn header [user]
  (if user
    (header-logged-in (:first_name user))
    (header-logged-out)))

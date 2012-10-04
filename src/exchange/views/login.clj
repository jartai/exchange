(ns exchange.views.login
  (:require [net.cgrand.enlive-html :as html]
            [exchange.views.common :as common]
            [exchange.auth :as auth]
            [ring.util.response :refer [redirect]]))

(html/deftemplate index-tpl "templates/login/index.html"
  [username login_failed anti-forgery-token]
  [:#login-username] (html/set-attr :value username)
  [:#errors] #(when login_failed %)
  [:#anti-forgery-token] (html/set-attr :value anti-forgery-token)
  [:#header] (html/substitute (common/header nil)))

(defn index [username login_failed anti-forgery-token]
  (if (auth/user?)
    (redirect "/")
    (index-tpl username login_failed anti-forgery-token)))

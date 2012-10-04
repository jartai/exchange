(ns exchange.views.signup
  (:require [net.cgrand.enlive-html :as html]
            [cemerick.friend.workflows :as workflow]
            [exchange.views.common :as common]
            [exchange.models.user :as user]
            [exchange.auth :as auth]
            [ring.util.response :refer [response]]
            [ring.util.response :refer [redirect redirect-after-post]]))

(html/deftemplate index-tpl "templates/signup/index.html"
  [{:keys [first_name email password]} u anti-forgery-token]

  [:#anti-forgery-token] (html/set-attr :value anti-forgery-token)

  [:#first-name-error] (html/content first_name)
  [:#first-name-group] (html/add-class (when first_name "error"))
  [:#first-name] (html/set-attr "value" (:first_name u))

  [:#last-name] (html/set-attr "value" (:last_name u))

  [:#email-error] (html/content email)
  [:#email-group] (html/add-class (when email "error"))
  [:#email] (html/set-attr "value" (:email u))

  [:#password-error] (html/content password)
  [:#password-group] (html/add-class (when password "error"))

  [:#header] (html/substitute (common/header nil)))

(defn index [errors anti-forgery-token]
  (if (auth/user?)
    (redirect "/")
    (index-tpl errors nil anti-forgery-token)))

(defn create [u]
  (if-let [errors (user/validate u)]
    (response (index-tpl errors u (:__anti-forgery-token u)))
    (do
      (user/create! u)
      (workflow/make-auth (dissoc (auth/load-user-record (:email u)) :password)))))

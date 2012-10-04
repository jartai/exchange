(ns exchange.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            (exchange.views [home]
                            [signup]
                            [login]
                            [listing])
            [exchange.auth :as auth]
            ring.util.response
            ring.middleware.anti-forgery
            clojure.set))

(defn signup-workflow [{:keys [uri request-method params]}]
  (when (and (= uri "/signup") (= request-method :post))
    (exchange.views.signup/create params)))

(defroutes app-routes
  (GET "/" [] (exchange.views.home/index))

  (GET "/signup" [] (exchange.views.signup/index
                      nil
                      ;*anti-forgery-token*
                      "TODO: UNCOMMENT ABOVE"))

  (GET "/listing/new" [] (exchange.views.listing/create-new))

  (GET "/login" [username login_failed]
       (exchange.views.login/index username login_failed
                                   ;*anti-forgery-token*
                                   "TODO: UNCOMMENT ABOVE"))

  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site
    (friend/authenticate
      app-routes
      {:credential-fn (partial creds/bcrypt-credential-fn auth/load-user-record)
       :workflows [(workflows/interactive-form)
                   signup-workflow]
       :login-uri "/login"
       :unauthorized-redirect-uri "/login"
       :default-landing-uri "/"})))

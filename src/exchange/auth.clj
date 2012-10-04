(ns exchange.auth
  (:require [cemerick.friend :as friend]
            [exchange.models.user :as user]))

(defn user? []
  (some #{:exchange.handler/user} (:roles (friend/current-authentication))))

(defn load-user-record [email]
  (let [user-map (user/find-by-email email)
        roles #{(keyword (str "exchange.auth/" (:role user-map)))}]
    (when (seq user-map)
      (-> (select-keys user-map [:email :password])
          (clojure.set/rename-keys {:email :username})
          (assoc :roles roles)))))

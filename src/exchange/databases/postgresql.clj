(ns exchange.databases.postgresql
  (:require [korma.db :refer :all]
            [environ.core :refer :all]))

(defdb pgsql (postgres (env :pgsql)))

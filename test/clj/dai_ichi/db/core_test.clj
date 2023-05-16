(ns dai-ichi.db.core-test
  (:require
   [dai-ichi.db.core :refer [*db*] :as db]
   [java-time.pre-java8]
   [luminus-migrations.core :as migrations]
   [clojure.test :refer [deftest is use-fixtures]]
   [next.jdbc :as jdbc]
   [dai-ichi.config :refer [env]]
   [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'dai-ichi.config/env
     #'dai-ichi.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-transaction [t-conn *db* {:rollback-only true}]
    (is (= 1 (db/create-user!
              t-conn
              {:id         1000
               :first_name "Sam"
               :last_name  "Smith"}
              {})))
    (is (= {:id         1000
            :first_name "Sam"
            :last_name  "Smith"}
           (db/get-user t-conn {:id 1000} {})))))

(ns dai-ichi.qa.db.bootstrap
  (:require
   [clojure.tools.logging :as log]
   [next.jdbc :as jdbc])
  (:gen-class))

(defn -main [& _args]
  (if-let [database-url (System/getenv "DATABASE_URL")]
    (let [ds (jdbc/get-datasource (str "jdbc:" database-url))]
      (log/info "Starting bootstrap")
      (log/info
       (jdbc/execute! ds ["insert into users (id, first_name, last_name)
                        values (10000, 'Al', 'Pacino'),
                               (10001, 'Robert', 'De Niro')"]))
      (log/info "Bootstrap completed!"))
    (log/error "Database configuration not found, :database-url environment variable must be set before running")))

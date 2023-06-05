(ns dai-ichi.qa.db.bootstrap
  (:require
   [clojure.tools.logging :as log]
   [clojure.tools.cli :refer [parse-opts]]
   [dai-ichi.config :refer [env]]
   [dai-ichi.core]
   [luminus-migrations.util :refer [to-jdbc-uri]]
   [next.jdbc :as jdbc]
   [mount.core :as mount])
  (:gen-class))

#_
(defn- parse-url
  ([opts] (parse-url opts identity))
  ([{:keys [database-url] :as opts} transformation]
   (if database-url
     (-> opts
         (dissoc :database-url)
         (assoc-in [:db :connection-uri]
                   (to-jdbc-uri (transformation database-url))))
     opts)))



(defn -main [& args]
  (-> args
      (parse-opts dai-ichi.core/cli-options)
      (mount/start-with-args #'dai-ichi.config/env))

  (log/info (select-keys env [:database-url]))

  (if-let [;; database-url (System/getenv "DATABASE_URL")
           database-url (get-in env [:database-url])]
    (do
      (log/info "Starting bootstrap")
      (log/info "Obtaining datasource from" (to-jdbc-uri database-url))
      (let [ ;; ds (jdbc/get-datasource (str "jdbc:" database-url))
            ds (jdbc/get-datasource (to-jdbc-uri database-url))]
        (log/info
         (jdbc/execute! ds ["insert into users (id, first_name, last_name)
                               values (10000, 'Al', 'Pacino'),
                                      (10001, 'Robert', 'De Niro')"]))
        (log/info "Bootstrap completed!")))
    (log/error "Database configuration not found, :database-url environment variable must be set before running")))

(comment

  (-> nil
      (parse-opts dai-ichi.core/cli-options)
      (mount/start-with-args #'dai-ichi.config/env))

  (parse-url (select-keys env [:database-url]))

  (log/info "Obtaining datasource from" (to-jdbc-uri (get-in env [:database-url])))

  (jdbc/get-datasource (to-jdbc-uri (get-in env [:database-url])))

  (jdbc/execute! (jdbc/get-datasource (to-jdbc-uri (get-in env [:database-url])))
                 [(format
                   "insert into users (id, first_name, last_name) values (%d, 'Al', 'Pacino')"
                   (rand-int 100000))])




  )

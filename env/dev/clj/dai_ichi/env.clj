(ns dai-ichi.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [dai-ichi.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[dai-ichi started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[dai-ichi has shut down successfully]=-"))
   :middleware wrap-dev})

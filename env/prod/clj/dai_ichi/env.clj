(ns dai-ichi.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dai-ichi started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[dai-ichi has shut down successfully]=-"))
   :middleware identity})

(ns dai-ichi.app
  (:require [dai-ichi.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)

(ns dai-ichi.build
  (:require
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [shadow.cljs.devtools.config :as shadow-config]
   [clojure.string :as str]))

(defn replace-output-name
  "Replaces the filename of the script in `home.html` from `/js/app.js`
  to the name stored in `manifest.edn` under the key `app.js`"
  []
  (let [home        (io/resource "html/home.html")
        manifest    (-> (io/file (:output-dir (shadow-config/get-build! :app)) "manifest.edn")
                        slurp
                        edn/read-string)
        output-name (->> manifest
                         (filter #(= (:module-id %) :app))
                         first
                         :output-name)]
    (spit home
          (str/replace (slurp home)
                       #"\{% script \"/js/app.*\.js\" %\}"
                       (format "{%% script \"/js/%s\" %%}" output-name)))))

(defn cache-buster-hook
  {:shadow.build/stage :flush}
  [build-state & _args]
  (prn [:replacing-fingerprint-hash])
  (replace-output-name)
  build-state)

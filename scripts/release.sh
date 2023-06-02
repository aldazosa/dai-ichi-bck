#!/bin/sh

java -cp target/uberjar/dai-ichi.jar clojure.main -m dai-ichi.core migrate
java -cp target/uberjar/dai-ichi.jar clojure.main -m dai-ichi.qa.db.bootstrap

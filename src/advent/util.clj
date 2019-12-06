(ns advent.util
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn read-file
  "Read the [file-name] and split the lines"
  [file-name]
  (->> (io/resource file-name)
       (slurp)
       (string/split-lines)))

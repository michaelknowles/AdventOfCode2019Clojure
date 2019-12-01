(ns advent.core
  (:gen-class)
  (:require [advent-2019.day1]))

(defn get-year
  []
  (println "Input a year (2019):")
  (read-line))

(defn get-day
  []
  (println "Input a day:")
  (read-line))

(defn get-result
  [year day]
  (let [program (symbol (str "advent-" year ".day" day "/day" day))]
    (println "Running " program)
    (resolve program)))

(defn -main
  "Run a specific advent of code task."
  []
  (println "Advent of Code")
  (let
   [year (get-year)
    day (get-day)
    result (get-result year day)]
    (println result)))
(ns advent.core
  (:gen-class)
  (:require [advent-2019.day1]
            [advent-2019.day2]
            [advent-2019.day3]
            [advent-2019.day4]
            [advent-2019.day5]))

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
  (let [space (str "advent-" year ".day" day)
        func (str "day" day)]
    (println "Running " space "/" func)
    (into {} ((resolve (symbol space func))))))

(defn -main
  "Run a specific advent of code task."
  []
  (println "Advent of Code")
  (let
   [year (get-year)
    day (get-day)]
    (println (get-result year day))))
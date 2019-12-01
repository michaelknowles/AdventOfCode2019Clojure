(ns advent-2019.day1
  (:require [advent.util :as util]))

(def input-part1 (util/read-file "2019-day1.txt"))

(defn find-fuel
  [mass]
  (- (quot (Integer/parseInt mass) 3) 2))

(defn day1
  "Answer: 3297896"
  [input]
  (reduce + (map find-fuel input)))
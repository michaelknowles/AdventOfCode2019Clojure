(ns advent-2019.day1
  (:require [advent.util :as util]))

(def input-day1
  (map clojure.edn/read-string (util/read-file "2019-day1.txt")))

(defn mass->fuel
  "The fuel is the mass divided by 3, rounded down, and subtracted by 2"
  [mass]
  (- (quot mass 3) 2))

(defn part1
  "Answer: 3297896"
  [input]
  (reduce + (map mass->fuel input)))

(defn iter-mass->fuel
  "The fuel is the mass divided by 3, rounded down, and subtracted by 2.
   The fuel itself requires fuel so find the fuel required for that as well.
   Iterate until the result is 0 or negative."
  [mass]
  (->> (iterate mass->fuel mass)
       (take-while pos?)
       (rest)
       (reduce +)))

(defn part2
  "Answer: 4943969"
  [input]
  (reduce + (map iter-mass->fuel input)))

(defn day1
  []
  (assoc {}
         :part1 (part1 input-day1)
         :part2 (part2 input-day1)))
(ns advent-2019.day5
  (:require [advent.util :as util]
            [advent-2019.intcode :as intcode]))

(def input-day5
  (vec
   (map #(Integer/parseInt %1)
        (clojure.string/split
         (first (util/read-file "2019-day5.txt"))
         #","))))

(def test-small
  [3,9,8,9,10,9,4,9,99,-1,8])

(def test-small2
  [3,9,7,9,10,9,4,9,99,-1,8])

(def test-jump
  [3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9])

(def test-jump2
  [3,3,1105,-1,9,1101,0,0,12,4,12,99,1])

(def test-big
  [3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31
   1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104
   999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99])

(defn day5
  "Input 1 for part 1. Answer: 13294380
   Input 5 for part 2. Answer: 11460760"
  []
  (assoc {}
         :part1 (intcode/calc input-day5)
         :part2 (intcode/calc input-day5)))
(ns advent-2019.day2
  (:require [advent.util :as util]))

(def input-day2
  (map clojure.edn/read-string
       (clojure.string/split
        (first (util/read-file "2019-day2.txt"))
        #",")))

(defn op-1
  "Add the numbers at x1 and x2, store it at pos."
  [params input]
  (assoc input (last params)
         (+ (nth input (first params))
            (nth input (second params)))))

(defn op-2
  "Multiply the numbers at x1 and x2, store it at pos."
  [params input]
  (assoc input (last params)
         (* (nth input (first params))
            (nth input (second params)))))

(defn restore-state
  "Replace the values as follows: (position -> value)
   1 -> val1, 2 -> val2"
  [input val1 val2]
  (assoc (vec input) 1 val1 2 val2))

(defn calc
  [input]
  (loop [input input
         xs (take 4 input)
         op (first xs)
         remaining (drop 4 input)]
    (cond
      (= 1 op) (let [params (rest xs)]
                 (recur (op-1 params input)
                        (take 4 remaining)
                        (first remaining)
                        (drop 4 remaining)))
      (= 2 op) (let [params (rest xs)]
                 (recur (op-2 params input)
                        (take 4 remaining)
                        (first remaining)
                        (drop 4 remaining)))
      :else (first input))))

(defn intcode-computer
  [input x1 x2]
  (calc (restore-state input x1 x2)))

(defn part1
  "Take 4 x from input and perform the necessary operations.
   Move to the next 4 and repeat.
   Answer: 5434663"
  [input]
  (intcode-computer input 12 2))
 
(defn part2
  "Determine what pair of inputs will result in the given output.
   Answer: 4559"
  [output input]
  (for [noun (range 100)
        verb (range 100)
        :let [result (intcode-computer input noun verb)]
        :when (= output result)]
    (+ (* 100 noun) verb)))

(defn day2
  []
  (assoc {}
         :part1 (part1 input-day2)
         :part2 (part2 19690720 input-day2)))
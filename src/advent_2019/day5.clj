(ns advent-2019.day5
  (:require [advent.util :as util]))

(def input-day5
  (vec
   (map clojure.edn/read-string
        (clojure.string/split
         (first (util/read-file "2019-day5.txt"))
         #","))))

(defn arity
  "Return how many arguments an opcode should have."
  [opcode]
  (get {1 3, 2 3, 3 1, 4 1, 99 0} opcode))

(defn param
  "Return the parameter from the input.
   If the mode is 0, return the value at the given position.
   If the mode is 1, return the given position as the value."
  ;; 0 - position mode
  ;; 1 - immediate mode
  [input mode position]
  (case mode
    0 (nth input position)
    1 position))

(defn parse-instruction
  "Parse the instruction at the given index.
   Return a map of :opcode, :params, :position"
  [input idx]
  (let [parts (vec (format "%05d" (nth input idx)))
        opcode (clojure.edn/read-string (apply str (take-last 2 parts)))
        cnt (arity opcode)
        args (drop (inc idx) (take (inc (+ idx cnt)) input))
        pos (last args)
        params (butlast args)
        modes (map #(Character/digit %1 10) [(nth parts 2) (second parts) (first parts)])]
    (assoc {}
           :opcode opcode
           :params (map #(param input %1 %2) modes params)
           :position pos)))

(defn calc
  "Run the calculator starting at the given index on the input."
  [input idx]
  (let [{opcode :opcode
         params :params
         position :position} (parse-instruction input idx)]
    (if (= 99 opcode)
      (println "The answer is the last output number") (first input)
      (recur
       (case opcode
         1 (assoc input position (apply + params))
         2 (assoc input position (apply * params))
         3 (assoc input position 1)
         4 (do (println (nth input position)) input)
         (input))
       (+ (inc idx) (arity opcode))))))

(defn day5
  []
  (assoc {}
         :part1 (calc input-day5 0) ; Answer: 13294380
         :part2 ()))
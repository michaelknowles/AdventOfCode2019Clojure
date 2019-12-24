(ns advent-2019.day5
  (:require [advent.util :as util]))

(def input-day5
  (vec
   (map #(Integer/parseInt %1)
        (clojure.string/split
         (first (util/read-file "2019-day5.txt"))
         #","))))

(defn arity
  "Return how many arguments an opcode should have."
  [opcode]
  (get {1 3, 2 3, 3 1, 4 1, 5 2, 6 2, 7 3, 8 3, 99 0} opcode))

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
        ;; Use parseInt instead of read-string because Java considers
        ;; 08 and 09 to be octal and therefore invalid
        opcode (Integer/parseInt (apply str (take-last 2 parts)))
        cnt (arity opcode)
        args (drop (inc idx) (take (inc (+ idx cnt)) input))
        modes (map #(Character/digit %1 10) (reverse (take 3 parts)))
        params (butlast args)]
    (assoc {}
           :opcode opcode
           :params (map #(param input %1 %2) modes params)
           :position (last args)
           :modes modes)))

(defn calc
  "Run the calculator starting at the given index on the input."
  ([input] (calc input 0))
  ([input idx]
   (let [{opcode :opcode
          params :params
          position :position
          modes :modes} (parse-instruction input idx)]
     (if (= 99 opcode)
       (first input)
       (case opcode
         1 (recur (assoc input position (apply + params)) (+ (inc idx) (arity opcode)))
         2 (recur (assoc input position (apply * params)) (+ (inc idx) (arity opcode)))
         3 (recur
            (do (println "Input number:") (assoc input position (Integer/parseInt (read-line))))
            (+ (inc idx) (arity opcode)))
         4 (recur (do (println (nth input position)) input)
                  (+ (inc idx) (arity opcode)))
         5 (if (zero? (first params))
        ;      (recur (assoc input idx position) (+ (inc idx) (arity opcode)))
        ;      (recur input (+ (inc idx) (arity opcode))))
             (recur input (+ (inc idx) (arity opcode)))
             (recur input (param input (second modes) position)))
         6 (if (zero? (first params))
            ;  (recur (assoc input idx position) (+ (inc idx) (arity opcode)))
            ;  (recur input (+ (inc idx) (arity opcode))))
             (recur input (param input (second modes) position))
             (recur input (+ (inc idx) (arity opcode))))
         7 (recur (if (< (first params) (second params))
                    (assoc input position 1)
                    (assoc input position 0))
                  (+ (inc idx) (arity opcode)))
         8 (recur (if (= (first params) (second params))
                    (assoc input position 1)
                    (assoc input position 0))
                  (+ (inc idx) (arity opcode)))
         (recur input (inc idx)))))))

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
         :part1 (calc input-day5)
         :part2 (calc input-day5)))
(ns advent-2019.day4)

;; range for the answer
(def input-day4 "265275-781584")

(defn not-decreasing?
  "Check if the numbers are not decreasing."
  [x]
  (let [xs (map clojure.edn/read-string (clojure.string/split (str x) #""))]
    (apply <= xs)))

(defn has-dupe?
  "Check if the number has consecutive duplicates (2 or more in a row)."
  [x]
  (let [xs (map clojure.edn/read-string (clojure.string/split (str x) #""))]
    (< (count (partition-by identity xs)) 6)))

(defn strict-has-dupe?
  "Check if the number has a consecutive duplicate (no more than 2 in a row)."
  [x]
  (let [xs (map clojure.edn/read-string (clojure.string/split (str x) #""))
        parts (partition-by identity xs)]
    (and (< (count parts) 6)
         (->> (map count parts)
              (some #(= 2 %1))))))
    
(defn part1
  "Answer: 960"
  [input]
  (let [[start end]
        (map clojure.edn/read-string (clojure.string/split input #"-"))
        xs (range start end)]
    (count (filter #(and
                     (not-decreasing? %1)
                     (has-dupe? %1)) xs))))
(defn part2
  "Answer: 626"
  [input]
  (let [[start end]
        (map clojure.edn/read-string (clojure.string/split input #"-"))
        xs (range start end)]
    (count (filter #(and
                     (not-decreasing? %1)
                     (strict-has-dupe? %1)) xs))))
  
(defn day4
  []
  (assoc {}
         :part1 (part1 input-day4)
         :part2 (part2 input-day4)))
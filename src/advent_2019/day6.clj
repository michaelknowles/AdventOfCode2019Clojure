(ns advent-2019.day6
  (:require [advent.util :as util]
            [clojure.data :as data]))

(def input-day6
  (map #(clojure.string/split %1 #"\)")
       (util/read-file "2019-day6.txt")))

(def input-test
  (map #(clojure.string/split %1 #"\)")
       ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L"]))
;;         G - H       J - K - L
;;        /           /
;; COM - B - C - D - E - F
;;                \
;;                 I
;; 42 total orbits

(def input-test2
  (map #(clojure.string/split %1 #"\)")
       ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L" "K)YOU" "I)SAN"]))

(defn get-satellites
  [path]
  (let [[object orbits] path]
    {object (map second orbits)}))

(defn get-path
  [orbits object]
  (->> (let [children (get orbits object)]
         [object (map #(get-path orbits %1) children)])
       (flatten)))

(defn build-graph
  [input]
  (->> (group-by first input)
       (map get-satellites)
       (apply merge)))

(defn count-orbits
  [input]
  (let [orbits (build-graph input)]
    (->> (keys orbits)
         (map #(get-path orbits %1))
         (map (comp dec count)) ; don't need root of each path
         (reduce +))))

(defn day6
  []
  (assoc {}
         :part1 (count-orbits input-day6) ; 333679
         :part2 (count-transfers input-day6)))
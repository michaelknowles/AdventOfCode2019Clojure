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

(defn sat-map
  "Return a map of satellite -> object"
  [input]
  (->> (map (fn [[object satellite]] {satellite object}) input)
       (apply merge)))

(defn path-sat-com
  "Return the path from the satellite to COM"
  [orbits satellite]
  (loop [object (get orbits satellite)
         path []]
    (if (nil? object)
      path
      (recur (get orbits object) (conj path object)))))

(defn count-orbits
  [input]
  (let [orbits (sat-map input)]
    (->> (keys orbits)
         (map #(path-sat-com orbits %1))
         (map (comp count))
         (reduce +))))

(defn count-transfers
  [input]
  (let [orbits (sat-map input)
        targets ["YOU" "SAN"]]
    (->> (map #(path-sat-com orbits %1) targets)
         (map set)
         (apply data/diff)
         (take 2)
         (map count)
         (reduce +))))

(defn day6
  []
  (assoc {}
         :part1 (count-orbits input-day6) ; 333679
         :part2 (count-transfers input-day6))) ; 370
(ns advent-2019.day3
  (:require [advent.util :as util]
            [clojure.set]))

(def input-day3
  (map #(clojure.string/split %1 #",")
       (util/read-file "2019-day3.txt")))

(defn direction
  "Get a map of :direction, :distance from input."
  [input]
  (->> (re-find #"(^\w)(\d+)" input)
       (rest) ; the first value is the entire input
       (zipmap [:direction :distance])))

(defn up
  "Get all points from the start going up."
  [start distance]
  (->> (iterate inc (:y start)) ; up = increasing y value
       (take (inc distance))
       (map #(assoc {} :x (:x start) :y %1))))

(defn down
  "Get all points from the start going down."
  [start distance]
  (->> (iterate dec (:y start)) ; down = decreasing y value
       (take (inc distance))
       (map #(assoc {} :x (:x start) :y %1))))

(defn right
  "Get all points from the start going right."
  [start distance]
  (->> (iterate inc (:x start)) ; right = increasing x value
       (take (inc distance))
       (map #(assoc {} :x %1 :y (:y start)))))

(defn left
  "Get all points from the start going left."
  [start distance]
  (->> (iterate dec (:x start)) ; left = decreasing x value
       (take (inc distance))
       (map #(assoc {} :x %1 :y (:y start)))))

(defn path->points
  "Return all points traversed on a given path from the starting point."
  [start path]
  (let [{dir :direction
         dis :distance} path]
    (cond
      (= "U" dir) (up start (clojure.edn/read-string dis))
      (= "D" dir) (down start (clojure.edn/read-string dis))
      (= "L" dir) (left start (clojure.edn/read-string dis))
      (= "R" dir) (right start (clojure.edn/read-string dis)))))

(defn input->points
  "Get all points traversed using the given input."
  [input]
  (set
   (reduce (fn [all path]
             (concat all
                     (path->points
                      (last all) ; use the ending point of the last path
                      (direction path))))
           [{:x 0 :y 0}] input)))

(defn distance
  "Calculate the distance from the origin to the given point."
  [point]
  (+ (Math/abs (- (:x point) 0))
     (Math/abs (- (:y point) 0))))

(defn find-closest-intersection
  "Find the intersection closest to the origin."
  [points]
  (->> (apply clojure.set/intersection points)
       (map distance)
       (sort) ; lowest to highest distance
       (second))) ; the first point is the origin

(defn part1
  [input]
  (find-closest-intersection (pmap input->points input)))

(defn day3
  []
  (assoc {}
         :part1 (part1 input-day3)
         :part2 ()))
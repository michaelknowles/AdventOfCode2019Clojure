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
       (zipmap [:direction :distance])
       (#(assoc %1 :distance (clojure.edn/read-string (:distance %1))))))

(defn up
  "Get all points from the start going up."
  [start distance]
  (let [{point :point
         step :step} start]
    (loop [point {:x (:x point) :y (inc (:y point))}
           step (inc step)
           distance (dec distance)
           all []]
      (if (neg? distance)
        all
        (recur {:x (:x point) :y (inc (:y point))}
               (inc step)
               (dec distance)
               (conj all
                     (assoc {} :point point :step step)))))))

(defn down
  "Get all points from the start going down."
  [start distance]
  (let [{point :point
         step :step} start]
    (loop [point {:x (:x point) :y (dec (:y point))}
           step (inc step)
           distance (dec distance)
           all []]
      (if (neg? distance)
        all
        (recur {:x (:x point) :y (dec (:y point))}
               (inc step)
               (dec distance)
               (conj all
                     (assoc {} :point point :step step)))))))

(defn left
  "Get all points from the start going left."
  [start distance]
  (let [{point :point
         step :step} start]
    (loop [point {:x (dec (:x point)) :y (:y point)}
           step (inc step)
           distance (dec distance)
           all []]
      (if (neg? distance)
        all
        (recur {:x (dec (:x point)) :y (:y point)}
               (inc step)
               (dec distance)
               (conj all
                     (assoc {} :point point :step step)))))))

(defn right
  "Get all points from the start going left."
  [start distance]
  (let [{point :point
         step :step} start]
    (loop [point {:x (inc (:x point)) :y (:y point)}
           step (inc step)
           distance (dec distance)
           all []]
      (if (neg? distance)
        all
        (recur {:x (inc (:x point)) :y (:y point)}
               (inc step)
               (dec distance)
               (conj all
                     (assoc {} :point point :step step)))))))

(defn path->points
  "Return all points traversed on a given path from the starting point."
  [start path]
  (let [{dir :direction
         dis :distance} path]
    (cond
      (= "U" dir) (up start dis)
      (= "D" dir) (down start dis)
      (= "L" dir) (left start dis)
      (= "R" dir) (right start dis))))

(defn input->points
  "Get all points traversed from the origin using the given input."
  [input]
  (set
   (reduce (fn [all path]
             (concat all
                     (path->points
                      (last all) ; use the ending point of the last path
                      (direction path))))
           [{:point {:x 0 :y 0} :step 0}] input)))

(defn distance
  "Calculate the distance from the origin to the given point."
  [point]
  (+ (Math/abs (- (:x point) 0))
     (Math/abs (- (:y point) 0))))

(defn find-intersections
  "Get a set of all intersecting points."
  [points]
  (->> (map #(map :point %1) points)
       (map set)
       (apply clojure.set/intersection)))

(defn find-closest-intersection
  "Find the intersection closest to the origin."
  [points]
  (->> (find-intersections points)
       (map #(assoc %1 :distance (distance %1)))
       (sort-by :distance)
       (second))) ; the first point is the origin

(defn part1
  "Find the closest intersection from input.
   Answer: 1195"
  [input]
  (:distance (find-closest-intersection (pmap input->points input))))

(defn part2
  "Find the intersection that takes the least amount of combined steps to reach.
   Answer: 91518"
  [input]
  (let [all (pmap input->points input)
        xs (map #(select-keys %1 [:x :y]) (find-intersections all))
        flat (apply concat all)]
    (->> (map (fn [point]
                (->> (filter #(= point (:point %1)) flat)
                     (map :step)
                     (reduce +))) xs)
         (sort) ; least to greatest
         (second)))) ; first value is origin

(defn day3
  []
  (assoc {}
         :part1 (part1 input-day3)
         :part2 (part2 input-day3)))
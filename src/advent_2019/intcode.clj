(ns advent-2019.intcode)

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

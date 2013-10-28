(ns jsim.sensor-test
  (:require [clojure.test :refer :all]
            [jsim.sensor :refer :all]))

(deftest test-wrap
  (testing "Wrap function keeps angles normalized"
    (loop [a (* -4 Math/PI)]
      (when (< a (* 4 Math/PI))
            (println )
            (is (or
                 (<= (wrap a) (* Math/PI 2))
                 (>= (wrap a) (0))))
            (recur (+ a 0.1))))))

(deftest test-get-visible-landmarks
  (testing "All visible landmarks are returned as a matrix"
    (let [pose [0 0 0]
          feature-map [1 0 1
                       1 1 2
                       0 1 3
                       -1 0 4
                       -1 -1 5
                       0 -1 6
                       2 0 7]
          pi_2 (/ Math/PI 2)
          sensor (RangeBearingSensor. pi_2)]
      (is (= [[1 0 1]
              [1 1 2]
              [-1 -1 5]]
             (get-visible-landmarks feature-map pose))))))

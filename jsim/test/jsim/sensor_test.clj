(ns jsim.sensor-test
  (:require [clojure.test :refer :all]
            [jsim.sensor :refer :all]
            [clojure.core.matrix.operators :as ops]))

(defn nearly=
  "a and b are nearly equal elementwise"
  [a b]
  (let [c (ops/- a b)]
    (map #(> 1E-16 (Math/abs %)) c)))

(deftest test-wrap
  (testing "Wrap function keeps angles normalized"
    (loop [a (* -4 Math/PI)]
      (when (< a (* 4 Math/PI))
            (println )
            (is (or
                 (<= (wrap a) (* Math/PI 2))
                 (>= (wrap a) (0))))
            (recur (+ a 0.1))))))

(deftest test-rotate
  (testing "Matrix rotation"
    (let [point [1 0]
          a (/ Math/PI 2)]
      (is (nearly= [0 1] (rotate point a))))))

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
          sensor (jsim.sensor/->RangeBearingSensor 1 pi_2 0)
          expected [[1 0 1] [1 1 2] [-1 -1 5]]
          actual (get-visible-landmarks sensor feature-map pose)]
      (println actual)
      (is (ops/== expected actual)))))

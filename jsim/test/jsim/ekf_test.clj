;; (ns jsim.ekf-test
;;   (:require [clojure.test :refer :all]
;;             [jsim.ekf :refer :all]))

;; (deftest test-new-landmark?
;;   (testing "New Landmarks are recognized"
;;     (let [landmark [0 0 1]
;;           feature-map [1 1 2]]
;;       (is (new-landmark? landmark feature-map)))
;;     (let [landmark [0 0 1]
;;           feature-map [0 0 1]]
;;       (is (not (new-landmark? landmark feature-map))))))

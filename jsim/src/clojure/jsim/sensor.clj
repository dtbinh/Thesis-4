(ns jsim.sensor
  (:require [clojure.core.matrix :as mat]))

(defprotocol Sensor
  (get-visible-landmarks [sensor feature-map pose])
  (observe-landmarks [sensor feature-map pose landmarks])
  (observe [sensor pose landmark])
  (observe' [sensor pose landmark])
  (is-visible? [sensor landmark]))

(defn wrap [a]
  (Math/atan2 (Math/sin a) (Math/cos a)))

(defn rotate [p a]
  (let [cosa (Math/cos a)
        sina (Math/sin a)
        -sina (- sina)
        R [[cosa -sina]
           [sina  cosa]]]
   (mat/scale R p)))

(defn translate [p f]
  (mat/add p f))

(defn transform [point frame]
  (let [f [(first frame) (second frame)]
        a (nth frame 2)]
    (translate (rotate point a) f)))

(defrecord RangeBearingSensor [range arc noise]
  Sensor
  (is-visible?
    [this landmark]
    (let [x (first landmark)
          y (second landmark)
          d (Math/sqrt (+ (* x x) (* y y)))
          bearing #(Math/atan2 y x)
          range (wrap (+ (:range this) (/ Math/PI 2)))
          arc (:arc this)]
      (and (<= d range)
           (<= bearing arc))))

  (get-visible-landmarks [this feature-map pose]
    (let [ct (/ (count feature-map) 3)
          feature-map' (mat/reshape feature-map [ct 3])
          landmarks (map #(transform % pose) feature-map')
          visible? (partial is-visible? this)]
      (filter visible? landmarks)))

  (observe
    [this pose landmark]
    (let [dx (- (first landmark) (first pose))
          dy (- (second landmark) (second pose))
          a (nth pose 3)
          d [dx dy]
          q (mat/dot d d)
          id (nth landmark 3)]
      [(Math/sqrt q)
       (wrap (- (Math/atan2 dy dx) a))
       id]))
  (observe-landmarks [this feature-map pose landmarks]
    (map #(observe this pose %)
         (get-visible-landmarks this feature-map pose)))

  (observe'
    [this pose landmark]
    nil))

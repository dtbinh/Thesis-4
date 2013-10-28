(ns jsim.sensor
  (:require [clojure.core.matrix :refer :all]))

(defprotocol Sensor
  (get-visible-landmarks [feature-map pose])
  (observe-landmarks [feature-map pose landmarks])
  (observe [pose landmark])
  (observe' [pose landmark])
  (is-visible? [feature-map pose landmark]))

(defn wrap [a]
  (Math/atan2 (sin a) (cos a)))

(defn rotate [p a]
  (let [cosa (Math/cos a)
        sina (Math/sin a)
        -sina (- sina)
        R [[cosa -sina]
           [sina  cosa]]]))

(defn transform [point frame]
  )

(deftype RangeBearingSensor [range arc noise]
  Sensor
  (get-visible-landmarks [feature-map pose])

  Sensor
  (observe-landmarks [feature-map pose landmarks]
    (map #(observe pose %)
         (get-visible-landmarks feature-map pose)))

  (observe
    [pose landmark]
    (let [dx (- (first landmark) (first pose))
          dy (- (second landmark) (second pose))
          a (nth pose 3)
          d [dx dy]
          q (dot d d)
          id (nth landmark 3)]
      [(Math/sqrt q)
       (wrap (- (Math/atan2 dy dx) a))
       id]))

  (observe'
    [pose landmark]
    nil)

  (is-visible?
    [feature-map pose landmark]))

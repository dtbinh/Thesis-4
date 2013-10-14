(ns jsim.core)

(defrecord Coordinate "Cartesian Coordinate" [x y])

(defrecord Map "A collection of landmarks" [landmarks])

(defrecord Control [linear-velocity angular-velocity])

(defrecord Pose [position heading])

(defrecord Sensor [range arc])

(defrecord Drive [noise])

(defrecord Robot [drive sensor pose])

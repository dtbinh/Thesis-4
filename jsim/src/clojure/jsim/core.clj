(ns jsim.core
  (:require [[jsim.robot]

                        ]))

(defn run-step [])

(defn run-cli []
  (let [sensor (make-sensor params)
        bot (make-robot params)
        waypoints (make-waypoints params)
        landmarks (make-landmarks params)
        bayes-filter (make-ekf params)]
   (run-robot) ; get groundtruth
   (add-noise groundtruth)
   (add-noise observations)
   (run-simulator))
           

(defn -main [& args]
; Run CLI or Web?
; Read config
; Run simulator
; Save results
)


(defrecord Coordinate "Cartesian Coordinate" [x y])

(defrecord Map "A collection of landmarks" [landmarks])

(defrecord Control [linear-velocity angular-velocity])

(defrecord Pose [position heading])

(defrecord Sensor [range arc])

(defrecord Drive [noise])

(defrecord Robot [drive sensor pose])

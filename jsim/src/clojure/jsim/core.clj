(ns jsim.core
  (:import [jsim.Robot]
           [jsim.Sensor))


(defn make-sensor
  [sensor-params]
  (let [range (:range sensor-params)
        arc (:arc sensor-params)]
    (new jsim.Sensor range arc)))

(defn make-robot
  [robot-params])

(defn make-waypoints
  [waypoint-params]
  )

(defn make-landmarks
  [landmark-params]
  )

(defn make-efk
  [ekf-params]
  )

(defn run-robot [])

(defn add-noise [points])

(defn run-simulator [])

(defn run-cli [] ;TODO: READ PARAMS!
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
  (run-cli))
; Run CLI or Web?
; Read config
; Run simulator
; Save results

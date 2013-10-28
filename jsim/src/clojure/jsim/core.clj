(ns jsim.core)

;; (defn make-sensor
;;   [range arc]
;;   (new jsim.Sensor range arc))

;; (defn make-robot
;;   [sensor max-velocity]
;;   (new jsim.Robot sensor max-velocity))

;; (defn make-map
;;   [landmarks waypoints radius]
;;   (new jsim.Map2D landmarks waypoints radius))

;; (defn add-noise [point]
;;   (let [rand (new java.util.Random)
;;         x-noise (. rand nextGaussian)
;;         y-noise (. rand nextGaussian)
;;         x (first point)
;;         y (second point)]
;;     [(+ x x-noise) (+ y y-noise)]))

;; (defn run-step
;;   [bot map2d estimate]
;;   (let [control (. bot plan)]
;;     (. bot move (add-noise control))
;;     (let [z (. bot observe map2d)]
;;       (ekf estimate control observe))))

;; (defn make-efk
;;   [ekf-params])

;; (defn run-robot [])

;; (defn run-simulator [])

;; (defn run-cli [] ;TODO: READ PARAMS!
;;   (let [sensor (make-sensor (:range params) (:arc params))
;;         bot (make-robot (:max-velocity params))
;;         map2d (make-map (:landmark-count params) (:waypoint-count params) (:radius params))

;;         bayes-filter (make-ekf params)]
;;    (run-robot) ; get groundtruth
;;    (add-noise groundtruth)
;;    (add-noise observations)
;;    (run-simulator)))


;; (defn -main [& args]
;;   (run-cli))
;; ; Run CLI or Web?
;; ; Read config
;; ; Run simulator
;; ; Save results

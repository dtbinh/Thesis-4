(ns jsim.map)

(defrecord Coordinate "Cartesian Coordinate" [x y])

(defn rotate [coord angle]
  "Rotate coord by angle counter clockwise"
)

(defn translate [coord frame]
  "Tranlate coord to frame"
)

(defn transform [coord frame angle]
  "Rotate then translate coord by angle and frame"
)

(defn visible? [coord origin length angle]
  "True if coord is within segment described by origin length and angle"
)

(defn euclidian-distance [a b]
  "Euclidian distance between points a and b"
)

(defn angular-distance [a b]
  "Angular distance between a and b"
)

(defn equals 
  ([a b] "True if a and b are the same point"
    (and (= (a :x) (b :x)) (= (a :y) (b :y))))
  ([a b delta] "True if a and b are within delta"
    (< (euclidian-distance a b) delta)))



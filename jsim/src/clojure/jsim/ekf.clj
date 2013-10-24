(ns jsim.ekf
  (:require [core.matrix :refer :all]))

(defn predict
  [robot control]
  (let [motion-noise (:motion-noise robot)
        pose_0 (:pose robot)
        P (:covariance robot)
        move (:motion robot)
        move' (:motion' robot)
        I3 (identity-matrix 3)
        I3' (transpose I3)
        R (* motion-noise I3)
        Ig [[1 0 0 0 0 0 0]
            [0 1 0 0 0 0 0]
            [0 0 1 0 0 0 0]]
        noise-matrix (* I3' R I3 )
        G (+ Ig (move' pose_0 control))
        G' (transpose G)]
    {:x (move pose_0 control)
     :P (+ (* G P G') R)}))

(defn- new-landmark?
  [landmark feature-map]
  (let [id (nth landmark 2)
        ids (take-nth 3 (drop 2 feature-map))]
    (contains? (set ids) 2)))

(defn- update-landmark ;; What are my assumptions here?
  [landmark observe observe' x p q]
  (let [Ih (identity-matrix 6)
        z (observe x landmark) ; Observation in global grame
        H (* (observe' x landmark) Ih) ; Linearized observation
        H' (transpose H)
        Q (* I3 q)
        P_est (* p H')
        info-matrix (+ (* H Pt_est H') Q)
        K (/ P_est info-matrix); Kalman Gain - Does this just affect the observed landmark??
        z- (- landmark z)
        ]
    (if (new-landmark? landmark)
      ("do a")
      ("do b"))
    {:K K
     :z-diff z-}))

(defn update
  [robot
   observations]
  (let [sensor-noise (:sensor-noise robot)
        pose (:pose robot)
        observe (:observation robot)
        feature-map (:feature-map robot)]
    (if new-landmark? )) )

(defn ekf
  [robot
   control
   observations
   update' #(update %1 observations)]
  (->
   (predict robot control)
   update'))


;    for i = 1:3:size(z,2)
;       j = c(i,1);
;       if is_new_landmark(x, j+2)
;           xt_est(j:j+2) = calc_new_lmk(xt_est,z(:,i));
;       end
;       z_est = h(xt_est, j);
;       Fxj =   [Fx;zeros(3,3+3*N)];
;       Fxj(4:end,j:j+2) = eye(3);
;       Hti = h1(xt_est,j) * Fxj;
;       K = nanzero((Pt_est * Hti')/(Hti*Pt_est*Hti' + Qt));
;       restore_lmks = xt_est(6:3:end); % in case K modifies these
;       xt_est = xt_est + K * (z(:,i) - z_est);
;       I = eye(size(P));
;       xt_est(3) = wrap(xt_est(3)); % make sure a is btwn -pi and pi
;       xt_est(6:3:end) = restore_lmks;  % landmark ids are ints
;       Pt_est = (I - K*Hti) * Pt_est;
;    end
;end

from math import atan2, sin, cos, pi, sqrt
from numpy.linalg import norm
from numpy import array, matrix, eye, zeros, concatenate, append, mat, dot
from numpy.random import randn
from scipy.linalg import pinv
from helpers import wrap, find_landmark
import sys


class Robot(object):
    def __init__(self, range, arc, dt, max_velocity,
                 noise=[0, 0],
                 assoc = lambda obs: obs[2]):
        # Robot Properties
        self.sensor_range = range
        self.sensor_arc = arc
        self.max_velocity = max_velocity
        self.control_noise = noise[0]
        self.measurement_noise = noise[1]
        self.data_association = assoc
        self.dt = dt

        # Robot State
        self.control_history = []
        self.step = 0
        self.state_estimate = mat([0.0, 0.0, 0.0]).T
        self.covariance_estimate = eye(3)
        self.known_landmarks = []

    def pose(self):
        state = self.state_estimate.T.tolist()[0]
        pose = state[0:3]
        return pose

    def _extract_pose(self, x):
        state = x.T.tolist()[0]
        pose = state[0:3]
        return pose

    def add_control_noise(self, control):
        v = control[0] + self.control_noise * randn()
        w = control[1] + self.control_noise * randn()
        return array([v, w])

    def add_measurement_noise(self, observations):
        noisy = []
        for obs in observations:
            x = obs[0] + self.measurement_noise * randn()
            y = obs[1] + self.measurement_noise * randn()
            c = obs[2]
            noisy.append(array([x,y,c]))
        return matrix(noisy)

    def is_close(self, waypoint):
        xdiff = abs(self.state_estimate[0] - waypoint[0])
        ydiff = abs(self.state_estimate[1] - waypoint[1])
        return xdiff < 0.1 and ydiff < 0.1

    def plan(self, pose, waypoint):
        eps = sys.float_info.epsilon
        wc = self._rotation(pose, waypoint)
        w = min(wc, self.max_velocity)
        if abs(w) < eps:
            w = 0
        if w == 0:
            vc = norm(self.state_estimate[0:2] - waypoint[0:2])
            v = min(norm(self.state_estimate[0:2] - waypoint[0:2]), self.max_velocity)
        else:
            v = 0
        return array([v, w])

    def landmark_to_frame(self, pose, landmark):
        range = landmark[0]
        bearing = landmark[1]
        landmark_id = landmark[2]
        a = pose[2]
        xr = array([pose[0], pose[1], landmark_id])
        xj = xr + array([range * cos (a + bearing),
                         range * sin (a + bearing), 0])
        return xj

    def move(self, state, control, noise=True):
        """ Calculate mean robot pose
            state = state estimate as a 1D list
            control = linear and angular velocity as 1D list of doubles

            OUTPUT:
                New pose as a single column matrix
        """
        #Update robot true state
        self.control_history.append(control)
        self.step += 1

        if noise:
            control = self.add_control_noise(control)

        pose = state[0:3]
        v = control[0]
        w = control[1]
        a = pose[2]

        if w == 0:
            w += sys.float_info.epsilon

        x = -v/w * sin(a) + v/w * sin(a + w * self.dt)
        y =  v/w * cos(a) - v/w * cos(a + w * self.dt)
        a = a + w * self.dt

        pose = [x, y, atan2(sin(a), cos(a))]
        return mat(pose).T

    def move_jacobian(self, pose, control):
        v = control[0]
        w = control[1]
        a = pose[2]
        if w == 0:
            w += sys.float_info.epsilon

        return array([[0, 0, -v/w * cos(a) + v/w * cos(a + w * self.dt)],
                          [0, 0, -v/w * sin(a) + v/w * sin(a + w * self.dt)],
                          [0, 0,  0]])

    def _rotation(self, pose, goal):
        """Calculate rotational velocity"""
        heading = pose[2]
        xdist = goal[0] - pose[0]
        ydist = goal[1] - pose[1]
        angle = atan2(ydist, xdist) - heading
        return angle / self.dt

    def _update_state_with_pose(self, x, P, u, g, g1):
        N = len(self.known_landmarks)
        I = eye(3)
        Z = zeros([3, 3*N])

        F = concatenate([I, Z], 1) # Map state to correct dimensions

        xt_est = mat(x) + F * mat(g(x, u))  # Update state estimate with new pose
        G = F.T * g1(x, u) * F    # Linear estimate of pose
        Gt = eye(*G.shape) + G       # Add one why?
        Rt = eye(3) * self.control_noise      # Control noise matrix
        Pt_est = Gt * P * Gt.T + F.T * Rt * F  # New estimated covariance
        return xt_est, Pt_est, F

    def _add_new_landmark(self, landmark_id, xt_est, Pt_est, obs, F):
        size = len(xt_est)
        P = zeros([size + 3, size + 3])
        self.known_landmarks.append(landmark_id)
        xt_est = concatenate([xt_est, mat(obs).T], 0)
        P[:size, :size] = Pt_est
        F = concatenate([F, zeros([3,3])], 1)
        return xt_est, P, F


    def _update_state_with_observation(self, obs, h, h1, assoc, x_est, P_est):
        if not obs.any():
            return xt_est, Pt_est
        NoiseMatrix = eye(3) * self.measurement_noise
        landmark_count = len(self.known_landmarks)

        # Matrix to map state to correct dimensions.
        F = concatenate([eye(3), zeros([3, 3*landmark_count])], 1) # 6 by 3n+3

        # Data Association
        landmark_id = assoc(obs)
        if landmark_id not in self.known_landmarks:
            x_est, P_est, F = self._add_new_landmark(landmark_id, x_est, P_est, obs, F)

        z_est = h(x_est, obs)
        Fj = concatenate([F, F * 0])
        j = find_landmark(x_est, obs)
        Fj[3:, j:j+3] = eye(3)
        jacob = h1(x_est, obs)
        Hti = mat(jacob) * mat(Fj)
        K = (P_est * Hti.T) * pinv(Hti * P_est * Hti.T + NoiseMatrix) #watch out for Ids being changed!
        Gain = K * Hti
        I = eye(*Gain.shape)
        x_est = x_est + K * mat(obs - z_est).T
        P_est = (I - K * Hti) * P_est
        return x_est, P_est

    def ekf(self, x, P, control, measurements, motion_funcs, observation_funcs):
        #TODO: Linear simulation test
        xt_est, Pt_est, Fx = self._update_state_with_pose(x, P, control, *motion_funcs)

        for meas in measurements.tolist():
            xt_est, Pt_est = \
                self._update_state_with_observation(
                    self.landmark_to_frame(self._extract_pose(xt_est), meas),
                    observation_funcs[0], observation_funcs[1],
                    self.data_association, xt_est, Pt_est)

        self.state_estimate = xt_est
        self.covariance_estimate = Pt_est
        return xt_est, Pt_est

    def observe(self, xt_est, observation):
        j = find_landmark(xt_est, observation)
        lmk = xt_est[j:j+3]
        dx = lmk[0, 0] - xt_est[0].item()
        dy = lmk[1, 0] - xt_est[1].item()
        a = xt_est[2].item()
        d = [dx, dy]
        q = dot(d,d)
        z = array([sqrt(q),
                   wrap(atan2(dy, dx) - a),
                   lmk[2].item()])
        return z

    def observe_jacobian(self, xt_est, observation):
        j = find_landmark(xt_est, observation)
        lmk = xt_est[j:j+2]
        dx = lmk[0].item() - xt_est[0].item()
        dy = lmk[1].item() - xt_est[1].item()
        a = xt_est[2].item()
        d = [dx, dy]
        q = dot(d,d)
        if q == 0:
            k = 0
        else:
            k = 1 / q
        sq = sqrt(q)
        x = [-sq*dx, -sq*dy, 0, sq*dx, sq*dy, 0]
        y = [dy, -dx, -q, -dy, dx, 0]
        t = [0, 0, 0, 0, 0, q]
        z = k * array([x,y,t])
        return z

from math import atan2, sin, cos
from numpy.linalg import norm
from numpy import array, matrix
from numpy.random import randn
import sys

class Robot(object):
    def __init__(self, range, arc, dt, max_velocity, noise=[0, 0]):
        self.sensor_range = range
        self.sensor_arc = arc
        self.pose = array([0.0, 0.0, 0.0])
        self.dt = dt
        self.max_velocity = max_velocity
        self.control_noise = noise[0]
        self.measurement_noise = noise[1]

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
        xdiff = abs(self.pose[0] - waypoint[0])
        ydiff = abs(self.pose[1] - waypoint[1])
        return xdiff < 0.1 and ydiff < 0.1

    def plan(self, waypoint):
        eps = sys.float_info.epsilon
        wc = self._rotation(waypoint)
        w = min(wc, self.max_velocity)
        if w < eps:
            w = 0
        if w == 0:
            vc = norm(self.pose[0:2] - waypoint[0:2])
            v = min(norm(self.pose[0:2] - waypoint[0:2]), self.max_velocity)
        else:
            v = 0
        return array([v, w])

    def move(self, control):
        v = control[0]
        w = control[1]
        a = self.pose[2]
        if w == 0:
            self.pose = self.pose + array([v * cos(a) * self.dt,
                                           v * sin(a) * self.dt, 0])
        else:
            a = self.pose[2] + w * self.dt
            self.pose = array([self.pose[0],
                               self.pose[1],
                               atan2(sin(a), cos(a))])
        return self.pose

    def _rotation(self, goal):
        """Calculate rotational velocity"""
        heading = self.pose[2]
        xdist = goal[0] - self.pose[0]
        ydist = goal[1] - self.pose[1]
        angle = atan2(ydist, xdist) - heading
        return angle / self.dt

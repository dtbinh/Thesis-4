from numpy.random import rand
from numpy import matrix
from math import sin, cos, atan2, sqrt, pi

class Map(object):
    def __init__(self, landmark_count, waypoint_count, radius):
        def random_points(count):
            r = radius / 2
            return [(radius * rand() - r,
                     radius * rand() - r, i + 1) for i in xrange(count)]

        self.landmarks = random_points(landmark_count)
        self.waypoints = random_points(waypoint_count)
        self._waypoint_generator = self._yield_waypoint()

    def _yield_waypoint(self):
        for w in self.waypoints:
            yield w

    def pop_waypoint(self):
        return self._waypoint_generator.next()

    def get_visible_landmarks(self, robot):
        visible = []
        pose = robot.pose()
        for f in self.landmarks:
            t = matrix(pose[0:2]).T
            a = pose[2]
            R = matrix([[cos(a), -sin(a)],
                        [sin(a), cos(a)]])

            lmk = R * matrix(f[0:2]).T + t
            r = sqrt(lmk[0] ** 2 + lmk[1] ** 2)
            b = abs(atan2(lmk[1], lmk[0])) + pi/2
            if r <= robot.sensor_range and b <= robot.sensor_arc + a:
                visible.append(f)
        return visible


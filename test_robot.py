import unittest
from robot import Robot
from math import pi
import numpy as np

class TestRobot(unittest.TestCase):
    def test_add_control_noise(self):
        r = Robot(1,1,1,1)
        u = [0,0]
        assert(np.alltrue(u == r.add_control_noise(u)))
        r = Robot(1,1,1,1,[1,1])
        assert(not np.alltrue(u == r.add_control_noise(u)))

    def test_add_measurement_noise(self):
        r = Robot(1,1,1,1)
        z = [[0.0,0.0,1.0]]
        print z, r.add_measurement_noise(z).tolist()
        result = r.add_measurement_noise(z)
        assert(result.tolist() == z)
        r = Robot(1,1,1,1, [1,1])
        assert(not np.alltrue(z == r.add_measurement_noise(z)))
        assert(z[0][2] == r.add_measurement_noise(z).tolist()[0][2])
        assert (not np.alltrue(r.add_measurement_noise(z) ==
                                r.add_measurement_noise(z)))
    def test_is_close(self):
        r = Robot(1,1,1,1)
        assert(r.is_close([0,0]))
        assert(not r.is_close([1,0]))
        assert(r.is_close([0.09, 0]))

    def test_plan(self):
        r = Robot(1,1,1,1)
        p = r.plan(r.pose(), [0,0])
        assert(np.alltrue(p == np.array([0, 0])))
        p = r.plan(r.pose(), [1,0])
        assert(np.alltrue(p == np.array([1, 0])))
        p = r.plan(r.pose(), [0, pi])
        assert(np.alltrue(p == np.array([0, 1])))
        p = r.plan([0, 0, 1], [0.0, 0.9])
        assert(np.alltrue(p == np.array([0.0, pi/2 - 1])))

    def test_pose(self):
        r = Robot(1,1,1,1)
        pose = r.pose()
        assert type(pose) == type([])

    def test_move(self):
        r = Robot(1,1,1,1)
        p = r.plan(r.pose(), [0,0])
        result = r.move(r.pose(), p).T.tolist()
        np.testing.assert_almost_equal([[0,0,0]], result)
        p = r.plan(r.pose(), [1,0])
        result = r.move([0.0, 0.0, 0.0], p).T.tolist()
        print result
        np.testing.assert_almost_equal([[1.0, 0.0, 0.0]], result)
        x = 0.8414709848078965
        y = 0.45969769413186023
        a = 1
        result = r.move([0,0,0], [1,1]).T.tolist()
        np.testing.assert_almost_equal([[x,y,a]], result)

    def test_observe(self):
        r = Robot(1,1,1,1)
        state = np.matrix([0,0,0,1,0,1]).T
        observation = np.array([1,0,1])
        result = r.observe(state, observation)

    def test_ekf(self):
        r = Robot(10, pi/2, 1, 1, [0.01, 0.01])
        landmarks = np.array([(9, 0, 1)])
        observation = np.array([[9.1,0.01,1]])
        P = np.eye(3)
        x = np.mat([0,0,0]).T
        u = [1, 0]
        import ipdb; ipdb.set_trace()
        result = r.ekf(x, P, u, observation, (r.move, r.move_jacobian),
                       (r.observe, r.observe_jacobian))

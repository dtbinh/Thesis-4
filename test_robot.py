import unittest
from robot import Robot
from math import pi
import numpy as np

class TestRobot(unittest.TestCase):
    def setUp(self):
        pass
    def tearDown(self):
        pass
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
        p = r.plan([0,0])
        assert(np.alltrue(p == np.array([0, 0])))
        p = r.plan([1,0])
        assert(np.alltrue(p == np.array([1, 0])))
        p = r.plan([0, pi])
        assert(np.alltrue(p == np.array([0, 1])))
        r.pose = [0,0,1]
        p = r.plan([0.0, 0.9])
        assert(np.alltrue(p == np.array([0.0, pi/2 - 1])))
    def test_move(self):
        r = Robot(1,1,1,1)
        p = r.plan([0,0])
        assert([0,0,0] == r.move(p).tolist())
        p = r.plan([1,0])
        assert([1, 0, 0] == r.move(p).tolist())

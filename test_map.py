import unittest
from math import pi
from map import Map
from robot import Robot

class TestMap(unittest.TestCase):
    def setUp(self):
        self.map = Map(10, 10, 10)
    def test_make_map(self):
        assert(10 == len(self.map.landmarks))
        assert(10 == len(self.map.waypoints))
    def test_pop_waypoint(self):
        w = self.map.pop_waypoint()
        assert(w == self.map.waypoints[0])
    def test_get_visible_landmarks(self):
        self.map.landmarks = [(1, 0, 1), (-1, 0, 2), (-4, -4, 3)]
        r = Robot(2, pi, 1, 1)
        assert(1 == len(self.map.get_visible_landmarks(r)))

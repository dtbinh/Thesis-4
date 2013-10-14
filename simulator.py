from map import Map
from robot import Robot
from math import pi

class Simulator(object):
    def __init__(self, map, robots):
        self.map = map
        self.robots = robots
        self.waypoint = map.pop_waypoint()

    def run_step(self, bot_id):  #Throws StopIteration when we're done!
        """Run a single step with a single robot"""
        #TODO: Verify other robot visibility
        r = self.robots[bot_id]
        if r.is_close(self.waypoint):
            self.waypoint = self.map.pop_waypoint()
        p = r.plan(self.waypoint)
        next_pose = r.move(p)
        obs = self.map.get_visible_landmarks(r)
        return {'step': r.step,
                'truth': ('pose', next_pose.tolist(), 'obs', obs),
                'measured': ('u', r.add_control_noise(p).tolist(),
                             'z', r.add_measurement_noise(obs).tolist())}

    def run_sim(self):
        try:
            while True:
                for id_, _ in enumerate(self.robots):
                    print self.run_step(id_)
        except StopIteration:
            pass

if __name__ == '__main__':
    m = Map(50,10,10)
    r = Robot(10,pi/2,1,1)
    s = Simulator(m, [r])
    print "Landmarks:"
    print m.landmarks
    print "Waypoints:"
    print m.waypoints
    s.run_sim()

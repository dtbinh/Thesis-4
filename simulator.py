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
        return {'truth': (next_pose, obs),
                'measured': (r.add_control_noise(p),
                             r.add_measurement_noise(obs))}


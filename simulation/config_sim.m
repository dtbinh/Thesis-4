% Config
landmarks = 1000;
waypoints = 1000;
map_radius = 3000;
dt = 1;
close = 1;
motion_noise = 0.001;
observation_noise = 0.01;
sensor_arc = pi/4;
sensor_range = 50;
% Initialize
start_pose = [0;0;0];
start_time = 0;
max_v = 50;
#include <math.h>

#define LANDMARKS 10
#define WAYPOINTS 10
#define MAP_RADIUS 30
#define DT 1
#define CLOSE 1

#define MOTION_NOISE 1.001
#define OBSERVATION_NOSIE 0.01

#define SENSOR_ARC M_PI_4
#define SENSOR_RANGE 10

#define start_time 0

double start_pose[3] = {0,0,0};

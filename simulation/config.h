#include <math.h>

#define LANDMARKS 10
#define WAYPOINTS 100
#define MAP_RADIUS 30
#define DT 1
#define CLOSE 1

const double MOTION_NOISE 0.001;
const double OBSERVATION_NOSIE 0.01;

const double SENSOR_ARC = PI_M/4;
const double SENSOR_RANGE = 10;

double start_pose[3] = {0;0;0};
double start_time = 0;

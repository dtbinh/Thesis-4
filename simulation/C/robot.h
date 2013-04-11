#ifndef _rotate
#define _rotate
#define X 0
#define Y 1
#include "landmarks.h"

typedef struct odometry {
    double measurement[3];
    double truth[3];
} odometry;

double velocity(double* pose, double* goal, double max_vel);
odometry move_robot(double* x, double* u, double dt, double noise);

#endif

#include <math.h>
#include <stdio.h>
#include <assert.h>
#include "geometry.h"
#include "robot.h"


double velocity(double* pose, double* goal, double max_vel) 
{
    double d = euclidean_distance(pose, goal, 2); 
    if (d < max_vel)
        return d;
    return max_vel;
}


odometry move_robot(double* x, double* u, 
                    double dt, double noise)
{
    double v = u[0],
           w = u[1],
           a = x[2];
    double pose[3];
    double truth[3];
    odometry result;

    if (w) {
        double vw = v/w;
        truth[0] = x[0] + -vw * sin(a) + vw * sin(a + w * dt);
        truth[1] = x[1] +  vw * cos(a) - vw * cos(a + w * dt);
        truth[2] = x[2] + w * dt;    
    } else {
        truth[0] = x[0] + v * cos(a) * dt;
        truth[1] = x[1] + v * sin(a) * dt;
        truth[2] = x[2];
    }
    pose[0] = truth[0] + rand_normal(0, noise);
    pose[1] = truth[1] + rand_normal(0, noise);
    pose[2] = truth[2] + rand_normal(0, noise);
    int i;
    for (i = 0; i < 3; i++) {
        result.truth[i] = truth[i];
        result.measurement[i] = pose[i];
    }
    return result;
}


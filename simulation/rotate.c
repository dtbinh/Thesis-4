#include <math.h>
#include <stdio.h>
#include <assert.h>
#include "rotate.h"

#define TEST
#ifdef TEST
#define TEST
void test_rotate() {
    double dt = 1;
    double pose[] = {1, 1, 0};
    double goal[] = {1, 2};
    double a = rotate(pose, goal, dt);
    assert((int)(a*100) == 157);
    puts("Rotate passing");
}

void test_edist() {
    double p[] = {0,0};
    double q[] = {1,1};
    assert ((int)(euclidean_distance(p,q,2) * 100) == (int)(sqrt(2) * 100));
    puts("Euclidean Distance passing");
}

void test_drive() {
    double pose[] = {0, 0, 0};
    double goal[] = {1, 0};
    assert (drive(pose,goal,1) == 1.0);
    puts("Drive passing");
}

int main() {
    test_edist();
    test_rotate();
    test_drive();
    return 0;
}
#endif


double rotate(double* pose, double* goal, double dt) {
    double heading = pose[2];
    double goal_local[2];
    goal_local[X] = goal[X] - pose[Y];
    goal_local[Y] = goal[Y] - pose[Y];

    double a = atan2(goal_local[Y], goal_local[X]) - heading;
    return a / dt;
}

double euclidean_distance(double* p, double* q, int dimensions) {
    double sum = 0;
    for (int i = 0; i < dimensions; i++) {
        sum += q[i] - p[i];
    }     
    return sqrt(sum);
}

double drive(double* pose, double* goal, double max_vel) {
    double d = euclidean_distance(pose, goal, 2); 
    if (d < max_vel)
        return d;
    return max_vel;
}


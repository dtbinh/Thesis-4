#include <assert.h>
#include <stdlib.h>
#include <math.h>
#include <stdio.h>
#include <time.h>
#include "robot.h"
#include "geometry.h"

int test_move_robot() {
    double x[] = {0,0,0};
    double u[] = {1, M_PI_4};
    double dt = 1;
    double noise = 0.01;
    odometry result = move_robot(x, u, dt, noise);
    printf("Pose: %f %f %f\nTruth: %f %f %f\n",
            result.measurement[0], result.measurement[1], result.measurement[2],
            result.truth[0], result.truth[1], result.truth[2]);
    return 0;
}

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

void test_velocity() {
    double pose[] = {0, 0, 0};
    double goal[] = {1, 0};
    assert (velocity(pose,goal,1) == 1.0);
    puts("velocity passing");
}

int test_geom() {
    int buckets[20] = {0};
    int i, j;

    srand(time(NULL));
    for(i=0; i<500; i++) {
        double val = rand_normal(10.0, 3.0);
        int i = (int)floor(val + 0.5);
        if (i >= 0 && i < sizeof(buckets)/sizeof(*buckets))
            buckets[i]++;
    }
    for(i=0; i<sizeof(buckets)/sizeof(*buckets); i++) {
        for(j=0; j<buckets[i]; j++) {
            printf("#");
        }
        printf("\n");
    }
    return 0;
}

int main() {
    test_edist();
    test_rotate();
    test_velocity();
    test_move_robot();
    test_geom();
    return 0;
}

//Test Geometry



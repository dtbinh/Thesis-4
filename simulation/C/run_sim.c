#include <float.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include "geometry.h"
#include "config.h"
#include "landmarks.h"
#include "robot.h"

#define eps DBL_EPSILON

void print_list(point_list* p) {
    if (p == NULL)
        return;
    if (p->head != NULL)
        printf("a %i %f %f\n", 
                p->head->id,
                p->head->x,
                p->head->y);
    print_list(p->next);
}


int main(int argc, char** argv)
{
    srand48(time(NULL));
    point_list* lmks = make_list(create_landmarks(MAP_RADIUS, LANDMARKS), 
                                 LANDMARKS);
    point_list* waypts = make_list(create_waypoints(MAP_RADIUS, WAYPOINTS), 
                                   WAYPOINTS);
    puts("LANDMARKS");
    print_list(lmks);
    puts("WAYPOINTS");
    print_list(waypts);
    sleep(1);

    int time = 0;
    double pose[3] = {0, 0, 0};
    double odom[3] = {0, 0, 0}; /* t v w */
    double u[2];

    point_list* itr = waypts;
    point* current_waypoint = itr->head;
    while (current_waypoint != NULL) {  //For each waypoint
        double wpt_d[] = {current_waypoint->x, current_waypoint->y};
        double dist = fabs(euclidean_distance(wpt_d, pose, 2));
        printf("Dist: %lf\n", dist);
        while (dist > CLOSE) { //until waypoint
            printf("odo in main: %f %f %f\n", pose[0], pose[1], pose[2]);
            double arc = atan2(wpt_d[1] - pose[1], wpt_d[0] - pose[0]);
            double delta_a = arc - pose[2];
            if (abs(delta_a) > 10 * eps) {
                u[0] = 0;
                u[1] = rotate(pose, wpt_d, DT);
            } else {
                u[0] = velocity(pose, wpt_d, 1);
                u[1] = 0;
            }
            time += DT;
            odometry odo = move_robot(pose, u, DT, MOTION_NOISE);
            memcpy(&pose, &(odo.truth), 3);
            sleep(1);
            point_list* obs = get_visible_landmarks(lmks, pose, 
                                                    SENSOR_RANGE, 
                                                    SENSOR_ARC);
            point_list* list = obs;
            while (list != NULL) {
                if (list->head != NULL) 
                    printf("Obs: %i %f %f\n", list->head->id, 
                                         list->head->x, 
                                         list->head->y);
                else
                    puts("No obs");
                list = list->next;
            }
            dist = fabs(euclidean_distance(wpt_d, pose, 2));
            //TODO: Make observations wtih nosie
            //TODO: Clean up memory
            //save odometry and observation to lists!!
            //odometry = [odometry; time odo];
            //observations = [observations; obs];
            //TODO: Add another robot!!
        }
        itr = itr->next;
        current_waypoint = itr->head;
    }
    // Save here!
    return 0;
}

#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "landmarks.h"
#include "geometry.h"

point_list* observe(const point_list* visible, double noise)
{
    point_list* obs = malloc(sizeof(point_list));
    obs->head = NULL;
    obs->next = NULL;
    if (visible->head == NULL)
        return obs;
    point* p = malloc(sizeof(observation));
    double x = visible->head->x;
    double y = visible->head->y;
    /* in rb form */
    p->x = sqrt(x*x + y*y) + rand_normal(0, noise);
    p->y = atan2(y,x) + rand_normal(0, noise);
    obs->head = p;
    obs->next = observe(visible->next, noise);
    return obs;
}

void delete_list(point_list* p)
{
    if (p == NULL)
        return;
    if (p->next == NULL) {
        free(p->head);
        free(p);
    }
    else {
        point_list* q = p->next;
        free(p->head);
        free(p);
        delete_list(q);
    }
}

point_list* make_list(point* p_list, int count)
{
    point_list* p = malloc(sizeof(point_list));
    p->head = NULL;
    p->next = NULL;
    if (count) {
        p->head = p_list;
        p->next = make_list(++p_list, count-1);
    }
    return p;
}


point to_local_frame(const point* lm, const double* pose) {
    double a = pose[2];
    point result;
    result.id = lm->id;
    result.x = cos(a) * lm->x + -sin(a) * lm->y + pose[0];
    result.y = sin(a) * lm->x +  cos(a) * lm->y + pose[1];
    return result;
}


int is_visible(const point* lmk, const double* pose, 
               double range, double arc) {
    point p = to_local_frame(lmk, pose);
    double x = p.x;
    double y = p.y;

    double r = sqrt(x*x + y*y);
    double b = abs(atan2(y, x));
    if (r <= range && b <= arc) 
        return 1;
    return 0;
}


//Rename to get_observations
point_list* get_visible_landmarks(const point_list* lm, const double* pose, 
                                  double range, double arc)
{
    point_list* visible = malloc(sizeof(point_list));
    visible->head = NULL;
    visible->next = NULL;
    if (lm->head == NULL)
        return visible;
    
    if (is_visible(lm->head, pose, range, arc)) {
        visible->head = lm->head;    
        visible->next = get_visible_landmarks(lm->next, pose, range, arc);
    }
    return visible;
}

point* create_landmarks(double max_distance, int n_landmarks) {
    point* landmarks = malloc(sizeof(point) * n_landmarks);
    //srand48(time(NULL)); // TODO: Make this a random number!
    point* current_lmk = landmarks; 
    int i;
    for (i = 0; i < n_landmarks; i++)
    {
        double r = max_distance * drand48();
        double b = 2 * M_PI * drand48();
        current_lmk->id = i;
        current_lmk->x = r * cos(b);
        current_lmk->y = r * sin(b);
        ++current_lmk;
    }
    return landmarks;
}

point* create_waypoints(double max_distance, int n_waypoints) {
    return create_landmarks(max_distance, n_waypoints);
}

#ifdef test
#include <stdio.h>
int main(int argc, char** argv) {
    point* lmks = create_waypoints(100, 10);
    int i;
    for (i = 0; i < 10; i++) {
        printf("%i %f %f\n", lmks->id, lmks->x, lmks->y);
        ++lmks;
    }
    return 0;
}
#endif

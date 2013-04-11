#ifndef _landmarks_h
#define _landmarks_h
typedef struct point {
    int id;
    double x;
    double y;
} point;

typedef struct observation {
    int id;
    double r;
    double b;
} observation;

typedef struct point_list {
    point* head;
    struct point_list* next;
} point_list;

point* create_landmarks(double max_distance, int n_landmarks);
point* create_waypoints(double max_distance, int n_waypoints);
point_list* make_list(point* p_list, int count);
point_list* observe(const point_list* visible, double noise);
point_list* get_visible_landmarks(const point_list* lm, const double* pose,
                                  double range, double arc);
void delete_list(point_list* p);
#endif

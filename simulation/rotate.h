#ifndef _rotate
#define _rotate
#define X 0
#define Y 1

double rotate(double* pose, double* goal, double dt);
double euclidean_distance(double* p, double* q, int dimensions);
double drive(double* pose, double* goal, double max_vel);

#endif

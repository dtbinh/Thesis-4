#ifndef geometry_h
#define geometry_h

double rotate(double* pose, double* goal, double dt);
double euclidean_distance(double* p, double* q, int dimensions);
double rand_normal(double mean, double stddev);

#endif

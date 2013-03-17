#include <stdlib.h>
#include <math.h>

double[][] create_landmarks(double max_distance, int n_landmarks) {
    double landmarks[n_landmarks][3];
    double srand48(0); // TODO: Make this a random number!

    int i;
    for (i = 0; i < n_landmarks; i++)
    {
        double r = max_distance * drand48();
        double b = 2 * PI_M * drand48();
        landmarks[i][0] = i; //This can be done nicer with pointers!
        landmarks[i][1] = r * cos(b);
        landmarks[i][2] = r * sin(b);
    }
    return landmarks;
}

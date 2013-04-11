#include <math.h>
#include <stdlib.h>
#include <stdio.h>

double rotate(double* pose, double* goal, double dt) {
    double heading = pose[2];
    double goal_local[2];
    goal_local[0] = goal[0] - pose[0];
    goal_local[1] = goal[1] - pose[1];

    double a = atan2(goal_local[1], goal_local[0]) - heading;
    return a / dt;
}

double euclidean_distance(double* p, double* q, int dimensions) {
    double sum = 0;
    for (int i = 0; i < dimensions; i++) {
        sum += (q[i] - p[i]) * (q[i] - p[i]);
    }     
    return sqrt(sum);
}

double rand_normal(double mean, double stddev) 
{
    static double n2 = 0.0;
    static int n2_cached = 0;
    if (!n2_cached) {
        double x, y, r;
        do {
            x = 2.0 * rand()/RAND_MAX -1;
            y = 2.0 * rand()/RAND_MAX -1;
            r = x*x + y*y;
        } while (r == 0.0 || r > 1.0);
        double d = sqrt(-2.0 * log(r) / r);
        double n1 = x * d;
        n2 = y * d;
        double result = n1 * stddev + mean;
        n2_cached = 1;
        return result;
    } else {
        n2_cached = 0;
        return n2 * stddev + mean;
    }
}

/* rand_normal copyright
 * Copyright (c) 2013 the authors listed at the following URL, and/or
 * the authors of referenced articles or incorporated external code:
 * http://en.literateprograms.org/Box-Muller_transform_(C)?action=history&offset=20060711193108
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Retrieved from:
 * http://en.literateprograms.org/Box-Muller_transform_(C)?oldid=7011
 * */

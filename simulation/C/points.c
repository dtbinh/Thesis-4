#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

char* usage = "points count radius";

int main(int argc, char* argv[]) {
    srand48(time(NULL));
     if (argc < 3) {
        puts(usage);
        return 0;
    }     
     int count = atoi(argv[1]);
     double radius = atof(argv[2]);
     //srand48(time(NULL)); // TODO: Make this a random number!                  
     int i;                                                                      
     for (i = 0; i < count; i++)                                           
     {                                                                           
         double r = radius * drand48();                                    
         double b = 2 * M_PI * drand48();                                        
         double x = r * cos(b);                                            
         double y = r * sin(b);                                            
         printf("%i %f %f\n", i, x, y);
     }                                                                           
     printf("%i", EOF);
 }

#ifndef SPHERE_H
#define SPHERE_H

class Sphere {

    public:
        Sphere(double r = 1, char c = 'N');
        double getRadius();
        char getColor();
        double getDiameter();
        double getSurfaceArea();
        double getVolume();
        void randomizeColor();
        void grow(double);
        void shrink(double);
        void printSummary(int);
    private:
        const char colors[8] = {'B','R','P','Y','G','L','M'};
        const static double PI = 3.14159265;
        double radius;
        char color;
        void fix();

};

#endif
#include "sphere.h"
#include <iostream>

using namespace std;

Sphere::Sphere(double r, char c){
    radius = r;
    color = c;
    fix();
}

void Sphere::fix(){
    if (radius <= 0)
        radius = 1;
    if ((int) color > 96)
        color -= 32;
    bool v = false;
    int size = sizeof(colors)/sizeof(colors[0]);
    for (int i = 0; i < size; i++)
        if (colors[i] == color)
            v = true;
    if(!v)
        randomizeColor();
}

double Sphere::getRadius(){
    return radius;
}

char Sphere::getColor(){
    return color;
}

double Sphere::getDiameter(){
    return radius*2;
}

double Sphere::getSurfaceArea(){
    return 4*PI*radius*radius;
}

double Sphere::getVolume(){
    return (4.0/3)*PI*radius*radius*radius;
}

void Sphere::randomizeColor(){
    char colors[8] = {'B','R','P','Y','G','L','M'};
    color = colors[rand()%7];
}

void Sphere::grow(double g){
    radius += g;
}

void Sphere::shrink(double s){
    radius -= s;
}

void Sphere::printSummary(int p){
    cout << "Radius: \t" << getRadius() << endl;
    cout << "Color: \t";
        switch (getColor()){
        case 'B':
            cout << "Blue" << endl;
            break;
        case 'R':
            cout << "Red" << endl;
            break;
        case 'P':
            cout << "Purple" << endl;
            break;
        case 'Y':
            cout << "Yellow" << endl;
            break;
        case 'G':
            cout << "Green" << endl;
            break;
        case 'L':
            cout << "Black" << endl;
            break;
        case 'M':
            cout << "Maroon" << endl;
            break;
        default:
            cout << "Null" << endl;
    }
    cout << "Diameter: \t" << getDiameter() << endl;
    cout << "Volume: \t" << getVolume() << endl;
    cout << "Surface Area: \t" << getSurfaceArea() << endl;
}
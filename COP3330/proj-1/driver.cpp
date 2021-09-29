#include <iostream>
#include <cstdlib>
#include <ctime>

#include "sphere.h"

using namespace std;

void test1()
{

    cout << "*** Unit Testing One Starts ***" << endl;

    int precision = 1; // Precision variable for summary testing

    cout << "------------------------------" << endl;
    Sphere s1;
    s1.printSummary(precision);
    cout << endl;
   
    cout << "------------------------------" << endl;
    Sphere s2(5);
    s2.printSummary(precision);
    cout << endl;

    cout << "------------------------------" << endl;
    Sphere s3(2.25, 'X');
    s3.printSummary(precision);
    cout << endl;

    cout << "------------------------------" << endl;
    Sphere s4(3.175, 'B');
    s4.printSummary(5);
    cout << endl;

    cout << "****  Growing ...... " << endl;
    s4.grow(0.01);
    s4.printSummary(5);
    cout << endl;

    cout << "^^^^  Shrinking ...... " << endl;
    s4.shrink(0.01);
    s4.printSummary(5);
    cout << endl;

    cout << "^^^^  Shrinking ...... " << endl;
    s4.shrink(4.5);
    s4.printSummary(5);
    cout << endl;

    cout << "*** Unit Testing One Ends ***" << endl;
}

int main() {

    // seed the random number generator
    srand((time(0)));
    test1();

    return 0;
}
#include <iostream>
#include "matrix.h"

using namespace std;

void test1()
{
    cout << "******** Start the first test ********" << endl;
    Matrix m;
    cout << m << endl;
    cout << endl;

    Matrix m1 = m;
    cout << m1 << endl;
    cout << endl;

    Matrix m2(4, 5, 1);
    m = m2;
    cout << m << endl;
    cout << endl;

    cout << "******** End the first test ********" << endl;
}

void test2()
{
    cout << "******** Start the second test ********" << endl;
    Matrix m0;
    Matrix m1(2, 5, 7);
    Matrix m2(2, 5, 5);
    cout << m0+m1 << endl;
    cout << m1+m2 << endl;

    Matrix m3(5, 1, 2);
    cout << m0 * m3 << endl;

    Matrix m4 = m2 * m3;
    cout << m4  << endl;

    cout << "******** End the second test ********" << endl;
}

void test3()
{
    cout << "******** Start the third test ********" << endl;
    Matrix m1(2, 4, 0);
    m1.set(1, 1, 1);
    m1.set(1, 2, 1);
    m1.set(1, 3, 1);
    m1.set(1, 4, 1);
    
    m1.set(2, 1, 2);
    m1.set(2, 2, 2);
    m1.set(2, 3, 2);
    m1.set(2, 4, 2);
    
    cout << m1 << endl;
    m1.printinternal();

    cout << "Transform to column major ... " << endl;
    m1.tocolumnmajor();
    m1.printinternal();
    cout << m1 << endl;


    cout << "Transform to row major ... " << endl;
    m1.torowmajor();
    m1.printinternal();
    cout << m1 << endl;

    cout << m1.transpose() << endl;

    cout << "******** End the third test ********" << endl;
}

void test4()
{
    cout << "******** Start the fourth test ********" << endl;
    Matrix m(3, 5, 7);
    cout << m << endl;
    Matrix m1 = m.submatrix(1, 3, 2, 4);
    cout << m1 << endl;

    Matrix m2 = m.submatrix(1, 1, 1, 5);
    cout << m2 << endl;

    Matrix m3 = m.submatrix(1, 3, 5, 5);
    cout << m3 << endl;
    cout << "******** End the fourth test ********" << endl;
}

int main()
{
    test1();
    test2();
    test3();
    test4();
    return 0;
}
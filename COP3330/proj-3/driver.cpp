#include "polynomial.h"

int main()
{
    cout << "NOTE: the max-degree is preset to 10 in the class declaration." << endl;
    cout << "Start testing ..." << endl;
    cout << endl;

    Polynomial p1(3);
    cout << "p1 is: " << p1 << endl;
    cout << "p1.getDegree(): " << p1.getDegree() << endl;

    Polynomial p2(42);
    cout << "p2 is: " << p2 << endl;
    cout << "p2.getDegree(): " << p2.getDegree() << endl;

    p1.setLetter('a');
    cout <<  "p1 is: " << p1 << endl;

    p1.setCoefficient(2, 10);
    cout << p1 << " at 0 evaluates to: "  << p1.evaluate() << endl;
    cout << p1 << " at 2 evaluates to: "  << p1.evaluate(2) << endl;
    cout << endl;
    
    cout << "p2 + p1: " << p2 + p1 << endl;
    cout << "p1 + p2: " << p1 + p2 << endl;
    cout << "p2 - p1: " << p2 - p1 << endl;
    cout << "p1 - p2: " << p1 - p2 << endl;
    cout << "p1 * p2: " << p1 * p2 << endl;
    cout << "p2 * p1: " << p2 * p1 << endl;

    cout << endl;

    if(p1 == p2)
        cout << "p1 is equal to p2" << endl;
    else
        cout << "p1 is not equal to p2" << endl;

    p1 = Polynomial();
    p2.clear();

    cout << "p1.getDegree(): " << p1.getDegree() << endl;
    cout << "p2.getDegree(): " << p2.getDegree() << endl;
    
    if(p1 == p2)
        cout << "p1 is equal to p2" << endl;
    else
        cout << "p1 is not equal to p2" << endl;

    return 0;
}
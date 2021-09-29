/////////////////////////////////////////////////////////
// Test program for the Date class
/////////////////////////////////////////////////////////

#include <iostream>
#include "date.h"

using namespace std;

void test1()
{
    Date d1;             // 1/1/2020
    Date d2(2,28,1980);  // 2/28/1980

    // display dates to the screen
    cout << "\nDate d1 is: ";
    d1.ShowByDay();            

    cout << "\nDate d2 is: ";
    d2.ShowByDay();

    d1.Decrement();
    cout << "\nAfter decrement, Date d1 is: ";
    d1.ShowByDay();

    d2.Increment();
    cout << "\nAfter increment, Date d2 is: ";
    d2.ShowByDay();

    cout << "\nShown by month, Date d2 is: \n\n";
    d2.ShowByMonth();
    cout << endl;

    int ret = d1.Compare(d2);
    if(ret == -1)
        cout << "d1 is before d2" << endl;
    else if (ret == 1)
        cout << "d1 is after d2" << endl;
    else 
        cout << "d1 is equal to d2" << endl;
}

void test2()
{
    Date d1("01/01/2000");

    char strdate[] = "03/01/2020";
    Date d2(strdate);
    d2.ShowByMonth();
}

int main()
{
    cout << "*** First test suites ***" << endl;
    test1();
    cout << endl << endl;

    cout << "*** Second test suites ***" << endl << endl;
    test2(); 

    return 0;
}

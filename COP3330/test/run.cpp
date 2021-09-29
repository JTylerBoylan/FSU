#include <iostream>
#include "test.h"

using namespace std;

int main(){
    Test t = Test(1,'c');
    cout << "Test Int: " << t.getTestInt() << " - Test Char: " << t.getTestChar() << endl;
    return 0;
}
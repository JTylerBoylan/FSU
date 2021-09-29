#include <iostream>
using namespace std;

bool isPrime(int n);

int main() {
    int max = 100;
    cout << "Printing all prime numbers from 1 to " << max << endl;
    for (int i = 1; i <= max; i++){
        if (isPrime(i))
            cout << i << endl;
    }
    return 0;
}

bool isPrime(int n){
    for (int i = 2; i <= n/i; i++)
        if (n%i==0)
            return false;
    return true;
}
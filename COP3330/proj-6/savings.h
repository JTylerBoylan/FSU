#ifndef _SAVINGS_H_
#define _SAVINGS_H_

#include "account.h"

class Savings:public Account {

    private:
        float currentBalance, interestRate;
    public:
        Savings(float b, float i);
        float ProjectedBalance();
        float CurrentBalance();
        float InterestRate();
};

#endif
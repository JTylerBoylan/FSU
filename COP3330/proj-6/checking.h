#ifndef _CHECKING_H_
#define _CHECKING_H_

#include "account.h"

class Checking:public Account {

    private:
        float currentBalance;
    public:
        Checking(float b);
        float ProjectedBalance();
        float CurrentBalance();

};

#endif
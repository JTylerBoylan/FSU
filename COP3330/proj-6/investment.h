#ifndef _INVESTMENT_H_
#define _INVESTMENT_H_

#include "account.h"

class Investment:public Account {

    public:
        struct ETF {
            float A, IVS, CVS, I; };

        Investment(ETF* etfList);
        float ProjectedBalance();
        float CurrentValue();
        ETF* getETFs();
    private:
        ETF etfs[5];
};

#endif
#ifndef _PORTFOLIO_H_
#define _PORTFOLIO_H_

#include "account.h"
#include <fstream>
#include <string>

using namespace std;

class Portfolio {

    private:
        Account ** accounts;
        Account::TYPE stot(string type);
        int accounts_size;
    public:
        Portfolio();
        ~Portfolio();
        bool importFile(const char* filename);
        bool createFileReport(const char* filename);
        void showAccounts() const;
        void sort();

};

#endif
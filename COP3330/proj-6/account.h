#ifndef _ACCOUNT_H_
#define _ACCOUNT_H_

#include <string>
using namespace std;

class Account {

    public:
        enum TYPE {SAVINGS, CHECKING, INVESTMENT, NONE};
    protected:
        string lastname,firstname;
        TYPE type;
    public:
        void setName(string last, string first);
        TYPE getType();
        string getFirstName();
        string getLastName();

};

#endif
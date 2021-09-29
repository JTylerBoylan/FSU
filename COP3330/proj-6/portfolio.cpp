#include "portfolio.h"
#include "savings.h"
#include "checking.h"
#include "investment.h"
#include <iomanip>
#include <iostream>
#include <cctype>

Portfolio::Portfolio(){
    //dynamic array
    accounts = new Account*[0];
    accounts_size = 0;
}
Portfolio::~Portfolio(){
    delete[] accounts;
}

bool Portfolio::importFile(const char* filename){

    ifstream in;
    in.open(filename);
    if (!in)
        return false;

    string s;
    getline(in, s);

    int size = stoi(s);      

    // Array of Account pointers to store classes
    Account ** temp = accounts;

    accounts = new Account*[accounts_size+size];

    //copy old value to new array
    for (int i = 0; i < accounts_size;  i++){
        accounts[i] = temp[i];
    }

    accounts_size += size;

    delete[] temp;

    for (int i = accounts_size-size; i < accounts_size; i++){

        string l1,l2;
        getline(in, l1);
        getline(in, l2);

        //The Last character of each line is a carraige return which is causing problems (except for last line)
        l1.back() = '\a';
        if (i != accounts_size-1)
            l2.back() = '\a';

        //split data using substrings starting at a character
        int comma = l1.find(',');
        string lastname(l1.begin(),l1.begin()+comma);
        string firstname(l1.begin()+comma+2,l1.end());

        int space = l2.find(' ');
        string accountType(l2.begin(),l2.begin()+space);

        switch (stot(accountType)){
            
        case Account::SAVINGS: {

            int next_space = l2.find(' ', space+1);

            string currentBalance(l2.begin()+space,l2.begin()+next_space);
            string interestRate(l2.begin()+next_space,l2.end());

            accounts[i] = new Savings(stof(currentBalance), stof(interestRate));
            accounts[i]->setName(lastname,firstname);

            break;
        }
            
        case Account::CHECKING: {

            string currentBalance(l2.begin()+space+1, l2.end());

            accounts[i] = new Checking(stof(currentBalance));
            accounts[i]->setName(lastname,firstname);

            break;
        }

        case Account::INVESTMENT: {

            //etf values
            float values[20];

            int last = space;
            int next = l2.find(' ',last+1);
            for (int i = 0; i < 20; i++){

                if (i == 19){

                    string val(l2.begin()+last,l2.end());
                    values[i] = stof(val);
                    break;

                } else {

                    string val(l2.begin()+last,l2.begin()+next);
                    values[i] = stof(val);
                    last = next;
                    next = l2.find(' ',last+1);

                }
            }

            //new etfs array
            Investment::ETF * etfs = new Investment::ETF[5];

            //setting etf values
            for (int i = 0 ; i < 5; i++){

                int j = (i*4);

                etfs[i].A = values[j];
                etfs[i].IVS = values[j+1];
                etfs[i].CVS = values[j+2];
                etfs[i].I = values[j+3];

            }

            accounts[i] = new Investment(etfs);
            accounts[i]->setName(lastname,firstname);

            delete[] etfs;

            break;
        }

        case Account::NONE: {
            //skip when accountType errors
            break;
        }

        }

    }

    in.close();
    return true;
}

bool Portfolio::createFileReport(const char* filename){
    
    ofstream out;
    out.open(filename);
    if (!out)
        return false;

    out << "Banking Summary" << endl;
    out << "-------------------" << endl << endl;

    out << fixed << showpoint;

    // Print Savings Accounts

    out << "Saving Accounts" << endl << endl;
    out << setw(50) << left << "Holder's Name" << setw(25) << right << "Initial Balance" << setw(25) << right << "Projected Balance" << endl;
    out << setw(100) << setfill('-') << right << " " << endl << setfill(' ');
    
    int savings = 0;
    float proj_savings = 0;
    for (int i = 0; i < accounts_size; i++){
        if (accounts[i]->getType() == Account::SAVINGS){

            Savings * acc = (Savings*) accounts[i];
            string name = acc->getFirstName() + " " + acc->getLastName();
            out << setw(50) << left << name << setw(25) << right << setprecision(2) << acc->CurrentBalance() << setw(25) << right << setprecision(2) << acc->ProjectedBalance() << endl;
            savings++;
            proj_savings += acc->ProjectedBalance();

        }
    }

    //Print Checking Accounts
    
    out << endl << endl;
    out << "Checking Accounts" << endl << endl;
    out << setw(50) << left << "Holder's Name" << setw(25) << right << "Initial Balance" << setw(25) << right << "Projected Balance" << endl;
    out << setw(100) << setfill('-') << right << " " << endl << setfill(' ');

    int checking = 0;
    float proj_checking = 0;
    for (int i = 0; i < accounts_size; i++){
        if (accounts[i]->getType() == Account::CHECKING){

            Checking * acc = (Checking*) accounts[i];
            string name = acc->getFirstName() + " " + acc->getLastName();
            out << setw(50) << left << name << setw(25) << right << setprecision(2) << acc->CurrentBalance() << setw(25) << right << setprecision(2) << acc->ProjectedBalance() << endl;
            checking++;
            proj_checking += acc->ProjectedBalance();

        }
    }

    //Print Investment Accounts

    out << endl << endl;
    out << "Investment Accounts" << endl << endl;
    out << setw(50) << left << "Holder's Name" << setw(25) << right << "Initial Balance" << setw(25) << right << "Projected Balance" << endl;
    out << setw(100) << setfill('-') << right << " " << endl << setfill(' ');

    int investment = 0;
    float proj_investment = 0;
    for (int i = 0; i < accounts_size; i++){
        if (accounts[i]->getType() == Account::INVESTMENT){

            Investment * acc = (Investment*) accounts[i];
            string name = acc->getFirstName() + " " + acc->getLastName();
            out << setw(50) << left << name << setw(25) << right << setprecision(2) << acc->CurrentValue() << setw(25) << right << setprecision(2) << acc->ProjectedBalance() << endl;
            investment++;
            proj_investment += acc->ProjectedBalance();

        }
    }

    //Print Overview

    out << endl << endl;
    out << "Overall Account distribution" << endl << endl;
    out << setw(15) << left << "Savings: " << setw(5) << left << savings << "-" << setw(15) << right << setprecision(2) << proj_savings/savings << endl;
    out << setw(15) << left << "Checking: " << setw(5) << left << checking << "-" << setw(15) << right << setprecision(2) << proj_checking/checking << endl;
    out << setw(15) << left << "Investment: " << setw(5) << left << investment << "-" << setw(15) << right << setprecision(2) << proj_investment/investment << endl;

    out.close();
    return true;
}

void Portfolio::showAccounts() const {

    cout << "Accounts: " << endl;
    cout << fixed << showpoint;
    for (int i = 0; i < accounts_size; i++){

        string name = accounts[i]->getLastName() + ", " + accounts[i]->getFirstName();
        cout << setw(50) << left;
        cout << name;

        if (accounts[i]->getType() == Account::SAVINGS){
            Savings* acc = (Savings*) accounts[i];
            cout << setprecision(2);
            cout << setw(20) << left;
            cout << "Savings";
            cout << setw(20) << right;
            cout << "$" <<  acc->CurrentBalance() << endl;
        }
        if (accounts[i]->getType() == Account::CHECKING){
            Checking* acc = (Checking*) accounts[i];
            cout << setprecision(2);
            cout << setw(20) << left;
            cout << "Checking";
            cout << setw(20) << right;
            cout << "$" <<  acc->CurrentBalance() << endl;
        }
        if (accounts[i]->getType() == Account::INVESTMENT){
            Investment* acc = (Investment*) accounts[i];
            cout << setprecision(2);
            cout << setw(20) << left;
            cout << "Investment";
            cout << setw(20) << right;
            cout << "$" <<  acc->CurrentValue() << endl;
        }
    }

}

void Portfolio::sort(){

    //Bubble sort
    for (int i = 0; i < accounts_size-1; i++){
        for (int j = 0; j < accounts_size-i-1; j++) {

            if (toupper(accounts[j]->getLastName()[0]) > toupper(accounts[j+1]->getLastName()[0])){
                Account* temp = accounts[j];
                accounts[j] = accounts[j+1];
                accounts[j+1] = temp;
            }
        }
    }
}

//String to Account::TYPE function
Account::TYPE Portfolio::stot(string input){
    if (input == "Savings"){
        return Account::SAVINGS;
    } else if (input == "Checking") {
        return Account::CHECKING;
    } else if (input == "Investment") {
        return Account::INVESTMENT;
    } else {
        return Account::NONE;
    }
}
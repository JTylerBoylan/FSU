#include "account.h"
#include "savings.h"
#include "checking.h"
#include "investment.h"

#include <iostream>

// ACCOUNT H //

void Account::setName(string last, string first){
    firstname = first;
    lastname = last;
}

Account::TYPE Account::getType() {
    return type;
}

string Account::getFirstName(){
    return firstname;
}

string Account::getLastName(){
    return lastname;
}

// SAVINGS H //

Savings::Savings(float b, float i){
    currentBalance = b;
    interestRate = i;
    type = Account::SAVINGS;
}

float Savings::ProjectedBalance() {
    return currentBalance*(1+interestRate);
}

float Savings::CurrentBalance() {
    return currentBalance;
}

float Savings::InterestRate() {
    return interestRate;
}

// CHECKING H //

Checking::Checking(float b) {
    currentBalance = b;
    type = Account::CHECKING;
}

float Checking::ProjectedBalance(){
    return currentBalance+0.1;
}

float Checking::CurrentBalance() {
    return currentBalance;
}

// INVESTMENT H //

Investment::Investment(ETF* etfList){
    type = Account::INVESTMENT;
    for (int i = 0; i < 5; i++)
        etfs[i] = etfList[i];
}

float Investment::ProjectedBalance(){
    float sum = 0;
    for (int i = 0; i < 5; i++){
        ETF e = etfs[i];
        float currentValue = (e.A/e.IVS)*e.CVS;
        float dividend = e.I * e.A;
        sum += currentValue + dividend;
    }
    return sum;
}

float Investment::CurrentValue(){
    float sum = 0;
    for (int i = 0; i < 5; i++) {
        ETF e = etfs[i];
        float currentValue = e.A;
        sum += currentValue;
    }
    return sum;
}

Investment::ETF * Investment::getETFs(){
    return etfs;
}
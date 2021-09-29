#ifndef _DATE_H
#define _DATE_H

#include <string>
#include <iostream>

using namespace std;

class Date {

    public:
        Date();
        Date(int month, int day, int year);
        Date(const char* date);
        int getDay();
        int getMonth();
        int getYear();
        bool isLeapYear();
        int getDaysInMonth();
        bool validDate();
        void Input();
        bool Set(int m, int d, int y);
        void Increment();
        void Decrement();
        int DayofWeek();
        int Compare(const Date& d);
        void ShowByDay();
        void ShowByMonth();
    private:
        int year, month, day;
        const static string months[12];
        const static int days_in_month[12];
        const static string days[7];



};

#endif
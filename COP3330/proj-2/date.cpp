#include "date.h"

//Month names array, month_name = months[month-1]
const string Date::months[12] = {"January","February","March","April","May","June","July",
                                    "August","September","October","November","December"};
//Days in month array, days_in_month = days[month-1]
const string Date::days[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
const int Date::days_in_month[12] = {31,28,31,30,31,30,31,31,30,31,30,31};

Date::Date(){ // default constructor (sets to 1/1/2020)
        year = 2020;
        month = 1;
        day = 1;
}

Date::Date(int m, int d, int y){ // parameter constructor
        year = y;
        month = m;
        day = d;
        if (!validDate()){ // valid date checker
            year = 2020;
            month = 1;
            day = 1;
        }
}

Date::Date(const char* date){ // string parse constructor
    int size = sizeof(date)/sizeof(date[0]); // get size of array
    int mdy = 3; //time identifier, year = 3, day = 2, month = 1
    int index = 1; //number multiplier
    month = 0; day = 0; year = 0; //set date to 0
    for (int i = size+1; i >= 0 ; i--){ //loop c-string from back to front (why size+1? not sure, but cuts short otherwise)
        int a = int(date[i]); //get number in ascii format
        if (a < 47 || a > 56) // check if ascii is not between 47(/) and 56(9)
            continue; // if so, skip the character
        if (a==47){ // check if character is a '/'
            mdy--; // if so, change time identifier (year -> day, day -> month)
            index = 1; // change index back to 1
            continue; // skip the rest of the loop
        }
        if (mdy == 3) // year time identifier
            year += (a-48)*index; // if so, add number*index to year, (ascii-48 = number)
        if (mdy == 2) // day time identifier
            day += (a-48)*index;
        if (mdy == 1) // month time identifier
            month += (a-48)*index;
        index *= 10; // change index by *10
        // index acts as the slot of the number (1985 = 5*1 + 8*10 + 9*100 + 1*1000)
    }

    if (!validDate()){ // valid date checker
        year = 2020;
        month = 1;
        day = 1;
    }
}

int Date::getDay(){ // returns day
    return day;
}

int Date::getMonth(){ // returns month
    return month;
}

int Date::getYear(){ // returns year
    return year;
}

int Date::getDaysInMonth(){ // gets days in month on year
    if (month == 2 && isLeapYear()) // if february and leap year
        return 29; // return 29
    else
        return days_in_month[month-1]; // else return days in month from 'days' array
}

bool Date::isLeapYear(){ // leap year checker
    return year%4==0 && year%400!=0;
}

bool Date::validDate(){ // valid date checker
    if (day < 1 || day > getDaysInMonth())  // valid day checker
        return false;
    if (month < 1 || month > 12) // valid month checker
        return false;
    // every year is valid
    return true;
}

void Date::Input(){ // input function
    bool valid = false; // valid entry checker, starts as false (no entry)
    while (!valid){ // while entry isn't valid
        string date;
        cout << "Input date in (month/date/year) format: "; // ask for date in format
        cin >> date; 
        /* Following code same as Date(const char* date) constructor) */
        int size = sizeof(date)/sizeof(date[0]);
        int mdy = 3; 
        int index = 1;
        month = 0; day = 0; year = 0;
        for (int i = size+1; i >= 0 ; i--){
            int a = int(date[i]);
            if (a < 47 || a > 57)
                continue;
            if (a==47){
                mdy--;
                index = 1;
                continue;
            }
            if (mdy == 3)
                year += (a-48)*index;
            if (mdy == 2)
                day += (a-48)*index;
            if (mdy == 1)
                month += (a-48)*index;
            index *= 10;
        }
        if (validDate()) // if valid date, valid = true, loop ends
            valid = true;
        else // else print 'Try again', loop again
            cout << "Invalid Date. Try again." << endl;
        
    }
}

bool Date::Set(int m, int d, int y){ // date setter
    Date date(m,d,y); // temp date
    if (!date.validDate()) // check if valid
        return false; // if not return false, do not set
    // else, set values, return true
    month = m;
    day = d;
    year = y;
    return true;
}

void Date::Increment(){ // increment function
    if (day++ == getDaysInMonth()){ // if day = days in month, set day back to 1, add 1 to month
        day = 1;
        if (month++ == 12){ // if month = 12, set month back to 1, add 1 to year
            month = 1;
            year++;
        }
    }
}

void Date::Decrement(){ // decrement function
    if (day-- == 1){ // if day = 1, set day to days in last month
        if (month-- == 1){ // if month = 1, subtract 1 from year
            month = 12;
            year--;
        }
        day = getDaysInMonth();
    }
}

int Date::DayofWeek(){ // day of week function
    int y = year; // copy year value
    /* Following algorithm provided in assignment notes */
    static int t[] = {0,3,2,5,0,3,5,1,4,6,2,4};
    y -= month < 3;
    return (y+y/4-y/100+y/400+t[month-1]+day)%7;
}

int Date::Compare(const Date& d){ // compare function
    if (year > d.year) // compare years first
        return 1;
    else if (year < d.year)
        return -1;
    else if (month > d.month) // then compare months
        return 1;
    else if (month < d.month)
        return -1;
    else if (day > d.day) // then compare days
        return 1;
    else if (day < d.day)
        return -1;
    else
        return 0; // if all equal then return 0
}

void Date::ShowByDay(){ // show by day function
    string d = days[DayofWeek()]; // get day in string
    cout << d << " " << month << "/" << day << "/" << year << endl; // print m/d/y in format
}

void Date::ShowByMonth(){ // show by month function
    cout << months[month-1] << "    " << year << endl; // first print header (Month    Year)
    cout << "Su    Mo    Tu    We    Th    Fr    Sa" << endl; // print day of week header
    int first_day = Date(month,1,year).DayofWeek(); // get first day of the month
    for (int i = 0; i < first_day; i++){ // print space fillers for all days until first day of month
        cout << "      ";
    }
    for (int i = 1; i <= getDaysInMonth(); i++){ // print day integer for rest of month
        if (Date(month,i,year).DayofWeek() == 0 && i!=1) // if monday, go back to start of line
            cout << endl;
        if (i < 10) // if day is less than 10, add a 0 to the front
            cout << "0";
        cout << i << "    "; // print number
    }
    cout << endl; // finish month
}
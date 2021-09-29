#ifndef _DATE_H
#define _DATE_H

#include <iostream>
#include <string>
#include <cctype>

using namespace std;

class Polynomial {

    friend ostream& operator<< (ostream& s, const Polynomial& p);
    friend istream& operator>> (istream& s, Polynomial& p);

    public:
        const static int MAX_DEGREE = 10;
        char VAR;
        Polynomial();
        Polynomial(int a0);
        void clear();
        int evaluate(int x = 0);
        int getCoefficient(int deg) const;
        int getDegree() const;
        bool setCoefficient(int deg, int co);
        bool setLetter(char var);
        bool operator>(const Polynomial p) const;
        bool operator<(const Polynomial p) const;
        bool operator==(const Polynomial p) const;
        bool operator>=(const Polynomial p) const;
        bool operator<=(const Polynomial p) const;
        bool operator!=(const Polynomial p) const;
        Polynomial operator+(const Polynomial p);
        Polynomial operator-(const Polynomial p);
        Polynomial operator*(const Polynomial p);
        //Overloading Operators >> << > < = >= <= != + - *
	private:
		int polynomial[MAX_DEGREE+1];

};

#endif
	#include "polynomial.h"
	#include <cmath>
	#include <cctype>

	//NOTE: polynomial array is in reverse order '{5,4,3,2,1,2} -> x^5 + 2x^4 + 3x^3 + 4x^2 + 5x + 2'

	Polynomial::Polynomial() { // default constructor
		for (int i = 0; i <= MAX_DEGREE; i++) {
			polynomial[i] = 0;
		}
		VAR = 'x';
	}

	Polynomial::Polynomial(int a0) { //paramater constructor
		for (int i = 0; i <= MAX_DEGREE; i++) { // set coefficients
			polynomial[i] = a0 - i; // '- i' to decrease the number per interval
		}
		VAR = 'x';
	}

	void Polynomial::clear() {
		for (int i = 0; i <= MAX_DEGREE; i++) {
			polynomial[i] = 0; // set all coefficients to 0
		}
	}

	int Polynomial::evaluate(int x) {
		int e = 0; // evaluate placeholder
		for (int i = 0; i <= MAX_DEGREE; i++) {
			e += pow(x, i) * polynomial[i]; //add coefficient * x^i to e
		}
		return e;
	}

	int Polynomial::getCoefficient(int deg) const{
		if (deg > MAX_DEGREE || deg < 0) // valid degree checker
			return 0;
		return polynomial[deg];
	}

	int Polynomial::getDegree() const{
		for (int i = MAX_DEGREE; i >= 0; i--){
			if (polynomial[i] != 0) // find first place where coefficient != 0
				return i;
		}
		return -1; // if never returned, all coefficients = 0 -> return -1
	}

	bool Polynomial::setCoefficient(int deg, int co){
		if (deg > MAX_DEGREE || deg < 0) // valid degree checker
			return false;
		polynomial[deg] = co;
		return true;
	}

	bool Polynomial::setLetter(char var){
		if (!isalpha(var)) // valid char checker
			return false;
		VAR = tolower(var); // to lowercase
		return true;
	}

	bool Polynomial::operator>(const Polynomial p) const {
		if(getDegree() > p.getDegree())
			return true;
		else if (getDegree() == p.getDegree()){ // if equal, check sum of coefficients
			int sum_t = 0; // sum of this classes coefficients
			int sum_p = 0; // sum of p class coefficients
			for (int i = 0; i < MAX_DEGREE; i++){
				sum_t += polynomial[i];
				sum_p += p.getCoefficient(i);
			}
			if (sum_t > sum_p) // compare sums
				return true;
		}
		return false;
	}
	bool Polynomial::operator<(const Polynomial p) const { // same as above except '<' sign used
		if(getDegree() < p.getDegree())
			return true;
		else if (getDegree() == p.getDegree()){
			int sum_t = 0;
			int sum_p = 0;
			for (int i = 0; i < MAX_DEGREE; i++){
				sum_t += polynomial[i];
				sum_p += p.getCoefficient(i+1);
			}
			if (sum_t < sum_p)
				return true;
		}
		return false;
	}
	bool Polynomial::operator==(const Polynomial p) const {
		for (int i = 0; i <= MAX_DEGREE; i++){ // compares all coefficients
			if (polynomial[i]!=p.getCoefficient(i+1))
				return false;
		}
		return true;
	}
	bool Polynomial::operator>=(const Polynomial p) const { // makes use of > and == operator functions above
		if (operator>(p) || operator==(p))
			return true;
		return false;
	}
	bool Polynomial::operator<=(const Polynomial p) const { // same as above except <
		if (operator<(p) || operator==(p))
			return true;
		return false;
	}
	bool Polynomial::operator!=(const Polynomial p) const { // returns opposite of == operator
		return !operator==(p);
	}
	istream& operator>>(istream &s, Polynomial& p) {
		char co_l = ','; //spacer

		string input;
		getline(s,input,';'); // get first letter
		if (!isalpha(input[0]))
			return s; // return if char isn't letter
		p.setLetter(input[0]); // set letter

		for (int i = 0; i <= p.MAX_DEGREE; i++){
			string next;
			if (i == p.MAX_DEGREE) // if it is the last char, there will be no ',' to delimit
				getline(s,next);
			else
				getline(s, next, ','); // if it isn't, there is
			p.setCoefficient(i,stoi(next)); // set coefficient to next value
		}
		return s;
	}
	ostream& operator<<(ostream &s, const Polynomial& p) {
		string poly = ""; // final string
		for (int i = p.MAX_DEGREE; i >= 0; i--){ 
			if (p.getCoefficient(i) == 0) // skip if coefficient = 0
				continue;
			if (p.getCoefficient(i)>0 && i != p.getDegree()) // put '+' if it is positive, but not first number
				poly += " + ";
			else if (p.getCoefficient(i)<0 && i != p.getDegree()) // put ' - ' (spaced) if it is negative, but not first number
				poly += " - ";
			else if (p.getCoefficient(i)<0 && i == p.getDegree()) // put '-' (not spaced) if negative, but is first
				poly += "-";
			// used ' - ' (spaced) to match format in the sample output

			if (abs(p.getCoefficient(i)) != 1 && i != 0) // skip putting coefficient if co = 1 and deg != 0
				poly += to_string(abs(p.getCoefficient(i)));
			
			if (i != 0) //put the variable if deg != 0
				poly += p.VAR;
			if (i != 1 && i != 0) // put the exponent if deg != 0 or 1
				poly += "^" + to_string(i);
		}
		s << poly; // print final string
		return s;
	}
	Polynomial Polynomial::operator+(const Polynomial p) {
		Polynomial t; // new temp poly
		t.setLetter(VAR); // set it's letter to match this
		for (int i = 0; i <= MAX_DEGREE; i++) {
			t.setCoefficient(i, polynomial[i] + p.getCoefficient(i)); // add coefficients together
		}
		return t;
	}
	Polynomial Polynomial::operator-(const Polynomial p) { // same as + operator, but subtract
		Polynomial t;
		t.setLetter(VAR);
		for (int i = 0; i <= MAX_DEGREE; i++) {
			t.setCoefficient(i, polynomial[i] - p.getCoefficient(i));
		}
		return t;
	}
	Polynomial Polynomial::operator*(const Polynomial p){
		Polynomial t;
		t.setLetter(VAR);
		for (int i = 0; i <= MAX_DEGREE; i++){
			for (int j = 0; j <= i; j++){ // run through all numbers between 0 and i
			/* 
			*	Since when multiplying degrees they add together, the new coefficient of each degree (i)
			* 	will be the sum of all products of both polynomials where the degrees (j,k) add up to i
			*/ 
				int k = i-j; // j + k = i
				t.setCoefficient(i, t.getCoefficient(i) + (polynomial[j]*p.getCoefficient(k))); // add value to coefficient
			}
		}
		return t;
	}




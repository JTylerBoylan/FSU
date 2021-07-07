public class Fraction {
	
  private int numerator = 0;		// numerator (and keeps sign)
  private int denominator = 1;		// always stores positive value

  public Fraction() {
	  
  }

  public Fraction(int n, int d) {
    if (set(n,d) == false)
    	set(0,1);
  }

  public boolean set(int n, int d) {
    if (d > 0) {
		numerator = n;
		denominator = d;
		return true;
    } else
    	return false;
  }
  
  public String toString() {
    return (numerator + "/" + denominator);
  }

  public int getNumerator() {
    return numerator;
  }

  public int getDenominator() {
    return denominator;
  }

  public double decimal() {
    return (double)numerator / denominator;
  }
  
  public Fraction simplify() {
	  Fraction sFrac = new Fraction(numerator,denominator);
	  int x = numerator;
	  int y = denominator;
	  int z;
	  while (y != 0) {
		  z = x%y;
		  x = y;
		  y = z;
	  }
	  x = x < 0 ? -x : x;
	  sFrac.set(numerator/x, denominator/x);
	  return sFrac;
  }
  
  public Fraction add(Fraction f) {
	  Fraction aFrac = new Fraction(numerator * f.denominator + f.numerator * denominator, denominator* f.denominator);
	  return aFrac.simplify();
  }
  
  public Fraction subtract(Fraction f) {
	  Fraction sFrac = new Fraction(numerator * f.denominator - f.numerator * denominator, denominator* f.denominator);
	  return sFrac.simplify();
  }

  public Fraction multiply(Fraction f) {
	  Fraction mFrac = new Fraction(numerator * f.numerator, denominator * f.denominator);
	  return mFrac.simplify();
  }
  
  public Fraction divide(Fraction f) {
	  int sign = f.numerator < 0 ? -1 : 1;
	  Fraction dFrac = new Fraction(numerator * f.denominator * sign, denominator * f.numerator * sign);
	  return dFrac.simplify();
  }
  
}

import java.util.Scanner;

public class Reverse {

	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Exercise 6.26: 'Reverse Digits'");
		
		System.out.print("\nPlease enter a long integer (0 to quit): ");
		long num = scanner.nextLong();
		
		while (num != 0) {
			
			num = Math.abs(num);
			
			System.out.print("\nThe number reversed is: " + reverseDigits(num));
			
			System.out.print("\n\nPlease enter a long integer (0 to quit): ");
			num = scanner.nextLong();
		}
		
		System.out.println("\nGoodbye!");
		
	}
	
	
	public static long reverseDigits(long num) {
		long reverse = 0;
		long digits = (long) Math.log10(num) + 1;
		while (digits > 0) {
			reverse += num%10*((long) Math.pow(10, --digits));
			num /= 10;
		}
		return reverse;
	}
	
}

import java.util.Scanner;

public class Pi {

	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Exercise 5.20: 'Approximating PI'");
		System.out.print(" Compute to how many terms of the series? ");
		int N = scanner.nextInt();
		
		System.out.println("PI Approximation:");
		double a = 0;
		for (int i = 1; i <= N; i++) {
			
			a += Math.pow(-1, i+1)*4/(i*2-1);
			System.out.println(i + "\t" + a);
			
		}
		
	}
	
}

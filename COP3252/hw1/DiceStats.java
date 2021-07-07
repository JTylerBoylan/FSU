import java.util.Random;
import java.util.Scanner;

public class DiceStats {

	static Scanner scanner = new Scanner(System.in);
	static Random rand = new Random();
	
	public static void main(String[] args) {
		
		System.out.println("Exercise 7.17: 'Dice Stats'");
		
		System.out.print("\nHow many dice will constitute one roll? ");
		int dice = scanner.nextInt();
		
		System.out.print("\nHow many rolls? ");
		int rolls = scanner.nextInt();
		
		int lowest = dice;
		int highest = dice*6;
		
		int[] freq = new int[highest-lowest+1];
		
		for (int i = 0; i < rolls; i++) {
			int sum = 0;
			for (int j = 0; j < dice; j++)
				sum += rand.nextInt(6)+1;
			freq[sum-lowest]++;
		}
		
		System.out.println("\nSum \t# of times \tPercentage\n");
		for (int i = 0; i <= highest-lowest; i++) {
			System.out.printf("%d\t%d\t\t%.2f%%\n", i+lowest,freq[i],freq[i]*100f/rolls);
		}
		
	}
	
}

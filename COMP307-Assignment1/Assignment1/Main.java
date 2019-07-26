import java.util.Scanner;

import Part1.KNearestNeighbour;
import Part2.DecisionTree;
import Part2.Run10;
import Part3.Perceptron;

public class Main {

	public static int kVal;

	public static void main(String[] args) {
		if (args.length > 3 || args.length < 2) {
			System.out.println("Parameters Must Be 'PART NUMBER', FILEPATH1, FILEPATH2");
			System.out.println("For Part 3, Parameters Must Be 'PART NUMBER', FILEPATH");
		} else {
			System.out.println(args[0]);
			System.out.println(args[1]);

			// ===============================PART1=================================

			if (args[0].equals("1")) {
				System.out.println("PART 1");
				Scanner sc = new Scanner(System.in);
				System.out.printf("ENTER A 'K' VALUE: 	");
				int k = Integer.parseInt(sc.next());
				System.out.println("KVAL =" + k);
				kVal = k;
				sc.close();
				new KNearestNeighbour(args[1], args[2], kVal);

				// These are the paths if running from IDE
				// "./Assignment1/ass1-data/part1/iris-training.txt"
				// "./Assignment1/ass1-data/part1/iris-test.txt"

			}

			// =====================================================================

			// ===============================PART2=================================
			if (args[0].equals("2")) {
				System.out.println("PART 2");
				new DecisionTree(args[1], args[2]);
				System.out.println("");
				System.out.println("Would you like to run 10 trials? (y/n)");
				Scanner sc2 = new Scanner(System.in);
				String userInput = sc2.next();

				boolean valid = false;
				if (userInput.equals("Y") || userInput.equals("y") || userInput.equals("N") || userInput.equals("n")) {
					valid = true;
				}
				while (!valid) {
					if (userInput.equals("Y") || userInput.equals("y") || userInput.equals("N")
							|| userInput.equals("n")) {
						valid = true;
					} else {
						System.out.println("Invalid input, enter 'y' or 'n' ");
						userInput = sc2.next();
					}
				}
				// These are the paths if running from IDE
				// new DecisionTree("./Assignment1/ass1-data/part2/hepatitis-training.dat",
				// "./Assignment1/ass1-data/part2/hepatitis-test.dat");

				if (userInput.equals("Y") || userInput.equals("y")) {
					System.out.println("");
					System.out.println("Enter the path to all 10 run files, do not include any files");
					System.out.println("Example: ./ass1-data/part2/");
					Scanner sc3 = new Scanner(System.in);
					String userInput2 = sc3.next();

					if (userInput2 != null) {
						new Run10(userInput2);
					}
					sc3.close();
				} else {
					System.out.println("END");
				}
				sc2.close();

				// This is the path if running from IDE
				// ./Assignment1/ass1-data/part2/
			}

			// =====================================================================

			// ===============================PART3=================================

			if (args[0].equals("3")) {
				System.out.println("PART 3");
				new Perceptron(args[1]);
			}
			// This is the path if running from IDE
			// "./Assignment1/ass1-data/part3/image.data"

			// =====================================================================

		}
	}
}

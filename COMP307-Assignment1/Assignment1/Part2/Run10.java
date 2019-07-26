package Part2;

public class Run10 {
	private double avg = 0;
	public Run10(String directory){
		double average = 0;
		for(int i = 1; i < 11; i++) {
		DecisionTree k = new DecisionTree(directory + "hepatitis-training-run" + i +".dat", directory + "hepatitis-test-run" + i + ".dat", true);
		avg += k.getAccuracyPercent();
		//System.out.println(avg);
		}
		average = (avg/10);
		System.out.printf("Accuracy Average Of 10 Trials = %.2f%% ", average);
	}
}

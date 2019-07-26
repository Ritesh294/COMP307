package Part3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {
	private Random randomGen;
	private int numberOfFeatures = 50;
	private List<PerceptronImage> imageList;
	private List<PairP3<Feature, List<Integer>>> featuresList;
	private double[] weight;
	private int attempts = 130;
	private int attemptsMade;
	private int correctClassifications;

	public Perceptron(String fname) {
		ParseP3 parser = new ParseP3();
		parser.parse(new File(fname));
		this.imageList = parser.getImagesList();
		this.featuresList = new ArrayList<>();
		this.weight = new double[numberOfFeatures + 1];
		this.setRandomGen(new Random());
		setupFeatures();
		initialize();
		doPerceptron();
	}

	public void displayResults() { // printing results
		System.out.println("=================RESULTS==================");
		System.out.printf("|\n");
		System.out.println("|  CORRECT CLASSIFICATION:	" + correctClassifications + " / " + this.imageList.size());
		System.out.printf("|-----------------------------------------\n");
		double accuracyPercentage = (((double) correctClassifications) / (this.imageList.size()) * 100);
		System.out.printf("|  ACCURACY:  		       	 %.2f%%", accuracyPercentage);
		System.out.printf("\n");
		System.out.printf("|-----------------------------------------\n");
		System.out.printf("|  ATTEMPTS:   	       	         %d", this.attemptsMade);
		System.out.printf("\n");
		System.out.printf("|-----------------------------------------\n");
		System.out.printf("|  WRONGLY CLASSIFIED:           %d", (imageList.size() - this.correctClassifications));
		System.out.printf("\n");
		System.out.printf("|-----------------------------------------\n");
		System.out.println("|  FINAL SET OF WEIGHTS:          ");
		System.out.printf("|\n");
		int k = -1;
		for (int i = 0; i < weight.length; i++) {
			if (i != 0) {
				System.out.printf("\n|");
			}
			if (i == 0) {
				System.out.printf("|");
			}
			for (int j = 0; j < 5; j++) {
				k++;
				if (k < weight.length) {
					System.out.printf(" %.2f,", weight[k]);
				} else {
					i = weight.length;
					break;
				}
			}
		}
		System.out.println("");
		System.out.printf("|-----------------------------------------\n");
		System.out.println("| CREATED FEATURES:          ");
		int printFeature = 0;
		int printC = 0;
		for (int i = 0; i < featuresList.size() - 1; i++) {
			printFeature = 0;
			printC = i + 1;
			System.out.println("");
			System.out.printf(printC + "");
			System.out.println(
					"____________________________________________________________________________________________________________");
			int[] r = featuresList.get(i).getBoolKey().getCol();
			int[] c = featuresList.get(i).getBoolKey().getCol();
			boolean[] v = featuresList.get(i).getBoolKey().getSgn();
			for (int j = 0; j < 4; j++) {
				System.out.print(" | Row:" + r[printFeature] + "," + " Col:" + c[printFeature] + "," + " Boolean:"
						+ v[printFeature]);
				printFeature++;
			}
		}
		System.out.println("");
		System.out.println(
				"==============================================================================================================");

	}

	public void doPerceptron() {
		int correct = 0;
		int attemptCount = 0;
		while (correct < imageList.size() && attemptCount < attempts) {
			correct = 0; // Reset the accuracy %
			for (int i = 0; i < imageList.size(); i++) {
				int category = imageList.get(i).getCategory();
				double ans = 0;

				for (int j = 0; j < featuresList.size(); j++) {
					ans += ((double) (featuresList.get(j).getValues().get(i)) * weight[j]);
				}
				if (ans > 0) {
					ans = 1;
				} else {
					ans = 0;
				}

				if (ans == category) { // iff correct
					correct++;
				}
				// if incorrect change weights
				else if (category == 0 && ans == 1) { // if Weights high --change
					changeWeight(i, category, ans);
				}

				else if (category == 1 && ans == 0) { // if Weights low --change
					changeWeight(i, category, ans);
				}
			}
			attemptCount++;
		}
		attemptsMade = attemptCount;
		correctClassifications = correct;
		displayResults();
	}

	public void initialize() {
		for (PairP3<Feature, List<Integer>> p : featuresList) {
			Feature f = p.getBoolKey();
			List<Integer> value = p.getValues();
			for (PerceptronImage img : imageList) {
				if (!f.isDummy())
					value.add(f.compare(img));
				else
					value.add(1); // adds dummy value
			}
		}
	}

	public void changeWeight(int index, int category, double ans) {
		for (int i = 0; i < featuresList.size(); i++) {
			double currentWeight = weight[i];
			double newWeight = currentWeight
					+ ((double) (((double) category - ans))) * featuresList.get(i).getValues().get(index);
			weight[i] = newWeight; // replace the current weight with new
		}
	}

	public void setupFeatures() {
		for (int i = 0; i < numberOfFeatures; i++) {
			Feature f = new Feature(10, 10, this.randomGen);
			featuresList.add(new PairP3<Feature, List<Integer>>(f, new ArrayList<>()));
		}
		featuresList.add(new PairP3<Feature, List<Integer>>(new Feature(), new ArrayList<>())); // adding the dummy
																								// feature to the list
	}

	public void setRandomGen(Random rand) {
		this.randomGen = rand;
	}

}

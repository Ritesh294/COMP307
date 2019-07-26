package part1;


import java.util.ArrayList;

public class NaiveBayes {
	private ArrayList<String> probabilityFraction = new ArrayList<>();
	private ArrayList<Data> train = new ArrayList<>();
	private ArrayList<Data> test = new ArrayList<>();
	private ArrayList<Double> trueSpam = new ArrayList<>();
	private ArrayList<Double> falseSpam = new ArrayList<>();
	private ArrayList<Double> trueNonSpam = new ArrayList<>();
	private ArrayList<Double> falseNonSpam = new ArrayList<>();
	private int spamTrueC;
	private int spamFalseC;
	private double probabilityTrue;

	public NaiveBayes(String trainFilePath, String testFilePath) {
		Parse parser = new Parse(trainFilePath, testFilePath);
		test = parser.getInstances();
		train = parser.getTrainInstances();
		NaiveBayesT();
		displayResults();
		classify();
	}

	private void displayResults() {
		for (int i = 0; i < trueNonSpam.size(); i++) {
			System.out.println("**************************** FEATURE:" + (i + 1) + " *****************************");
			System.out.println("				|");
			System.out.println("P(TRUE|SPAM) " + probabilityFraction.get(i * 4) + "		|	 P(FALSE|SPAM) "
					+ probabilityFraction.get(i == 0 ? 1 : (i * 4) + 1));
			System.out.println("P(TRUE|!SPAM) " + probabilityFraction.get(i == 0 ? 2 : (i * 4) + 2)
					+ "	        |  	 P(FALSE|!SPAM) " + probabilityFraction.get(i == 0 ? 3 : (i * 4) + 3));
			System.out.println("				|");
			System.out.println("--------------------------------------------------------------------");
		}
		System.out.println("\n");
		System.out.println("##################################");
		System.out.println("|     Total NON-SPAM = " + (spamFalseC) + "       |");
		System.out.println("|     Total SPAM =  " + (spamTrueC) + "           |");
		System.out.println("|--------------------------------|");
		System.out.printf("|     P(SPAM) = %.2f             | \n", probabilityTrue);
		System.out.printf("|     P(NON-SPAM) = %.2f         | \n", (1 - probabilityTrue));
		System.out.println("##################################");
		System.out.println("\n \n");
	}

	private void NaiveBayesT() {
		int count = 1;
		for (Data in : train) {
			if (in.getType() == 1)
				count++;
		}
		int total = train.size() + 2;
		spamTrueC = count;
		spamFalseC = total - count;
		probabilityTrue = (double) count / total;

		for (int i = 0; i < train.get(0).getAttributes().size(); i++) {
			double trueSpamC = 1;
			double falseSpamC = 1;
			double trueNonSpamC = 1;
			double falseNonSpamC = 1; // Zero Occurrence
			for (Data inst : train) {
				if (inst.getAttributes().get(i) == 1 && inst.getType() == 1) {
					trueSpamC++;
				} else if (inst.getAttributes().get(i) == 0 && inst.getType() == 1) {
					falseSpamC++;
				} else if (inst.getAttributes().get(i) == 1 && inst.getType() == 0) {
					trueNonSpamC++;
				} else if (inst.getAttributes().get(i) == 0 && inst.getType() == 0) {
					falseNonSpamC++;
				}
			}
			trueSpam.add((double) (trueSpamC / (trueSpamC + falseSpamC)));
			falseSpam.add((double) (falseSpamC / (trueSpamC + falseSpamC)));
			trueNonSpam.add((double) (trueNonSpamC / (trueNonSpamC + falseNonSpamC)));
			falseNonSpam.add((double) (falseNonSpamC / (trueNonSpamC + falseNonSpamC)));

			probabilityFraction.add((int) trueSpamC + "/" + ((int) trueSpamC + (int) falseSpamC));
			probabilityFraction.add((int) falseSpamC + "/" + ((int) trueSpamC + (int) falseSpamC));
			probabilityFraction.add((int) trueNonSpamC + "/" + ((int) trueNonSpamC + (int) falseNonSpamC));
			probabilityFraction.add((int) falseNonSpamC + "/" + ((int) trueNonSpamC + (int) falseNonSpamC));

		}
	}

	private void classify() {
		System.out.println("****************************** Test Data ******************************");
		System.out.println("");
		int j = 1;
		for (Data input : test) {
			ArrayList<Integer> attribute = input.getAttributes();
			double spamProbability = 1;
			double nonSpamProbability = 1;
			String lineString = "";
			for (int i = 0; i < attribute.size(); i++) {
				if (attribute.get(i) == 0) {
					spamProbability = spamProbability * falseSpam.get(i);
					nonSpamProbability = nonSpamProbability * falseNonSpam.get(i);
				} else if (attribute.get(i) == 1) {
					spamProbability = spamProbability * trueSpam.get(i);
					nonSpamProbability = nonSpamProbability * trueNonSpam.get(i);
				}
				lineString = lineString + attribute.get(i) + " ";
			}

			spamProbability = spamProbability * probabilityTrue;
			nonSpamProbability = nonSpamProbability * (1 - probabilityTrue);

			if (spamProbability > nonSpamProbability) {
				input.setType(1);
			} else {
				input.setType(0);
			}
			String prediction;
			if (input.getType() == 1) {
				prediction = "True";
			} else {
				prediction = "False";
			}
			System.out.println("********************Vector F" + j+ ":********************");
			System.out.println("Format: " + lineString);
			System.out.println("Spam Probability = " + spamProbability);
			System.out.println("Non-Spam Probability = " + nonSpamProbability);
			System.out.println("\nPrediction = " + prediction);
			System.out.println("--------------------------------------------------\n");
			j++;
		}
	}

}

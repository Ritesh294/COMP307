package Part1;
//https://medium.com/@equipintelligence/java-algorithms-the-k-nearest-neighbor-classifier-4faca7ad26b2

import java.util.ArrayList;
import java.util.List;

public class KNearestNeighbour {
	private static final String setosa = "Iris-setosa";
	private static final String versi = "Iris-versicolor";
	private static final String virg = "Iris-virginica";

	private List<Iris> irisTrainingList;
	private List<Iris> irisTestList;
	private int correctClassifications;
	private int k;

	public KNearestNeighbour(String trainFileName, String testFileName, int kValue) {
		this.k = kValue;
		Parse parser = new Parse(trainFileName, testFileName);
		this.irisTrainingList = parser.getTrainList();
		this.irisTestList = parser.getTestList();
		this.classifyIris();
	}

	public void classifyIris() {
		for (Iris irisTest : irisTestList) {
			List<Iris> neighbour = new ArrayList<Iris>();
			for (Iris irisTrain : irisTrainingList) {
				for (int i = 0; i <= neighbour.size(); i++) {
					if (i == neighbour.size()) {
						neighbour.add(irisTrain);
						break; // added the closest neighbour
					} else if (eucledianDist(neighbour.get(i), irisTest) > eucledianDist(irisTrain, irisTest)) {
						neighbour.add(i, irisTrain);
						break;
					}
				}
			}
			irisTest.setIrisNeighbours(neighbour);

			int setosaC = 0;
			int versiC = 0;
			int virgC = 0;

			for (int i = 0; i < k; i++) {
				switch (irisTest.getIrisNeighbours().get(i).getTypeOfIris()) {
				case "Iris-setosa":
					setosaC++;
					break;
				case "Iris-versicolor":
					versiC++;
					break;
				case "Iris-virginica":
					virgC++;
					break;
				default:
					break;
				}
			}
			if (setosaC >= versiC && setosaC >= virgC) {
				if (irisTest.getTypeOfIris().equals(setosa)) {
					correctClassifications++;
				}
			} else if (versiC >= setosaC && versiC >= virgC) {
				if (irisTest.getTypeOfIris().equals(versi)) {
					correctClassifications++;
				}
			} else if (virgC >= setosaC && virgC >= versiC) {
				if (irisTest.getTypeOfIris().equals(virg)) {
					correctClassifications++;
				}
			}
		}
		this.displayResults();
	}

	public double eucledianDist(Iris start, Iris end) {
		double eucledianDistance = 0;
		double sepalW = (end.getSepalWidth() - start.getSepalWidth());
		double sepalL = (end.getSepalLength() - start.getSepalLength());
		double petalW = (end.getPetalWidth() - start.getPetalWidth());
		double petalL = (end.getPetalLength() - start.getPetalLength());
		eucledianDistance = Math.sqrt(
				(Math.pow(sepalW, 2.0)) + (Math.pow(sepalL, 2.0)) + (Math.pow(petalW, 2.0)) + (Math.pow(petalL, 2.0)));

		return eucledianDistance;
	}

	public void displayResults() {
		System.out.println("=================RESULTS=================");
		System.out.printf("| 					|\n");
		System.out.println(
				"|  CORRECT CLASSIFICATION:	" + correctClassifications + " / " + this.irisTestList.size() + " |");
		System.out.printf("|---------------------------------------|\n");
		double accuracy = (((double) correctClassifications) / (this.irisTestList.size()) * 100);
		System.out.printf("|  ACCURACY:  		       	 %.2f%%", accuracy);
		System.out.printf(" | \n");
		System.out.printf("| 					|\n");
		System.out.println("=========================================");
	}
}
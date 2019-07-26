package Part2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DecisionTree {
	private List<Instance> trainList;
	private List<Instance> testList;
	private List<Instance> predictionList;
	private List<Integer> attributesList;
	private String[] classes;
	private String attributeName[];
	private Node rootNode;
	private Pair<String, Double> baseline;
	// Hashmap of attributes with instances values
	private Map<Integer, Pair<List<Instance>, List<Instance>>> instanceAttributeMap = new HashMap<>();
	private double falseImpurity = 0, trueImpurity = 0;
	private int correctClassifications;
	private double accuracyPercent;

	public DecisionTree(String trainFilePath, String testFilePath) {
		ParseP2 parser = new ParseP2();
		parser.parse(trainFilePath, testFilePath);
		this.trainList = parser.getTrainList();
		this.testList = parser.getTestList();
		this.attributesList = parser.getAttributesList();
		this.classes = parser.getClasses();
		this.attributeName = parser.getAttributeName();
		this.predictionList = new ArrayList<>();
		this.baseline = mostFrequent();
		this.rootNode = buildTree(trainList, attributesList);

		rootNode.report("   ");
		predict();
		displayResults();
	}
	
	public DecisionTree(String trainFilePath, String testFilePath, Boolean run10) {
		ParseP2 parser = new ParseP2();
		parser.parse(trainFilePath, testFilePath);
		this.trainList = parser.getTrainList();
		this.testList = parser.getTestList();
		this.attributesList = parser.getAttributesList();
		this.classes = parser.getClasses();
		this.attributeName = parser.getAttributeName();
		this.predictionList = new ArrayList<>();
		this.baseline = mostFrequent();
		this.rootNode = buildTree(trainList, attributesList);
		predict();
		for (int i = 0; i < testList.size(); i++) {
			if (predictionList.get(i).getClassification().equals(testList.get(i).getClassification())) {
				correctClassifications++;
			}
		}
		accuracyPercent = (((double) correctClassifications) / (this.testList.size()) * 100);
	}

	public void displayResults() {
		for (int i = 0; i < testList.size(); i++) {
			if (predictionList.get(i).getClassification().equals(testList.get(i).getClassification())) {
				correctClassifications++;
			}
		}
		System.out.println("");
		System.out.println("=================RESULTS=================");
		System.out.printf("| 					|\n");
		System.out.println(
				"|  CORRECT CLASSIFICATION:	" + correctClassifications + " / " + this.testList.size() + " |");
		System.out.printf("|---------------------------------------|\n");
		double accuracyPercentage = (((double) correctClassifications) / (this.testList.size()) * 100);
		setAccuracyPercent(accuracyPercentage);
		System.out.printf("|  ACCURACY:  		       	 %.2f%%", accuracyPercentage);
		System.out.printf(" | \n");
		System.out.printf("| 					|\n");
		System.out.println("=========================================");
		String f = baseline.getBoolKey();
		System.out.println("Baseline Classifier = " + f.toUpperCase());
	}

	public Node buildTree(List<Instance> instances, List<Integer> attributes) {

		boolean isInstancePure = true;

		if (instances.isEmpty()) {
			Node leafNode = new Node(baseline.getBoolKey(), baseline.getValues());
			return leafNode; // returns leaf node with probability of the most probable class.
		}

		String pureI = instances.get(0).getClassification();

		for (Instance i : instances) {
			if (!i.getClassification().equals(pureI)) {
				isInstancePure = false;
			}
		}
		if (isInstancePure) {
			return new Node(pureI, 1); // returns leaf node containing the name of class.
		}

		if (attributes.isEmpty()) {
			Node leafNode = mostLikelyClass(instances);
			return leafNode; // returns majority class of the instances in the node
		} else {
			
			PriorityQueue<Pair<Integer, Double>> prioritzedAttribute = new PriorityQueue<>(
					new Comparator<Pair<Integer, Double>>() {
						public int compare(Pair<Integer, Double> pair1, Pair<Integer, Double> pair2) {
							if (pair1.getValues() > pair2.getValues())
								return -1;
							else if (pair1.getValues() == pair2.getValues())
								return 0;
							else
								return 1;}});
			
			for (Integer i : attributes) {
				List<Instance> trueList = new ArrayList<>();
				List<Instance> falseList = new ArrayList<>();
				for (Instance inst : instances) {
					if (inst.getValue()[i]) {
						trueList.add(inst);
					} else {
						falseList.add(inst);
					}
				}

				if (!trueList.isEmpty())
					trueImpurity = calculateImpurity(trueList);

				if (!falseList.isEmpty())
					falseImpurity = calculateImpurity(falseList);

				
				double weightedTImpurty = RImpurity(trueImpurity, trueList, instances);
				double weightedFImpurty = RImpurity(falseImpurity, falseList, instances);
				double averagedPurity = 1 - ((weightedTImpurty + weightedFImpurty) / 2);
				prioritzedAttribute.add(new Pair<Integer, Double>(i, averagedPurity));
				instanceAttributeMap.put(i, new Pair<List<Instance>, List<Instance>>(trueList, falseList));
			}
			Pair<Integer, Double> best = prioritzedAttribute.poll();
			List<Integer> attribute = new ArrayList<>();
			List<Integer> attribute2 = new ArrayList<>();
			for (Integer in : attributes) {
				if (best.getBoolKey() != in) {
					attribute.add(in);
					attribute2.add(in);
				}
			}
			List<Instance> trueI = instanceAttributeMap.get(best.getBoolKey()).getBoolKey();
			List<Instance> falseI = instanceAttributeMap.get(best.getBoolKey()).getValues();
			Node left = buildTree(trueI, attribute);
			Node right = buildTree(falseI, attribute2);
			return new Node(left, right, attributeName[best.getBoolKey()], best.getBoolKey());
		}
	}

	public void predict() {
		for (Instance instance : testList) {
			Instance predictedInstance = rootNode.predict(instance);
			predictionList.add(predictedInstance); // add all predicted instances to list
		}
	}

	public Node mostLikelyClass(List<Instance> instanceList) {
		double count = 0;
		double probability = 0;

		for (Instance instance : instanceList) {
			if (instance.getClassification().equals(classes[0])) {
				count++; // "live" coutbn
			}
		}

		if (count < ((double) instanceList.size() / 2)) {
			count = instanceList.size() - count;
			probability = (count / (double) instanceList.size());
			return new Node(classes[1], probability); // Node(String classification, double probability)
		}

		// Tie Situation = 50/50/ chance
		else if (count == ((double) instanceList.size() / 2)) {
			double generateRandom = Math.random();
			if (generateRandom < .5) {
				return new Node(classes[0], 0.5);
			} else {
				return new Node(classes[1], 0.5);
			}
		}
		probability = (count / (double) instanceList.size());
		System.out.println(probability);
		return new Node(classes[0], probability);
	}

	public double calculateImpurity(List<Instance> attribute) { // True or False attribute list
		double count = 0;
		double tr = 0;
		double size = attribute.size();

		for (Instance instance : attribute) {
			if (instance.getClassification().equals(classes[0])) {// true
				count++;
			}
		}
		tr = 2 * ((count / size) * ((size - count) / size)); // impurity calculation
		return tr;
	}

	public double RImpurity(double tImpurity, List<Instance> tList, List<Instance> instances) {
		return (tImpurity * ((double) tList.size() / (double) instances.size()));
	}

	public Pair<String, Double> mostFrequent() {
		int i = 0;
		int j = 0;
		for (Instance instance : trainList) {
			if (instance.getClassification().equals(classes[0])) // gets the most frequent instance of the two
				i++;
			else
				j++;
		}
		int mostFrequent = Math.max(i, j); // whichever has higher count is most frequent
		if (mostFrequent == i) {
			return new Pair<String, Double>(classes[0], ((double) i / (double) trainList.size()));
		} else {
			return new Pair<String, Double>(classes[1], ((double) j / (double) trainList.size()));
		}
	}

	public double getAccuracyPercent() {
		return accuracyPercent;
	}

	public void setAccuracyPercent(double accuracyPercent) {
		this.accuracyPercent = accuracyPercent;
	}
}
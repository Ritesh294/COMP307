package Part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParseP2 {
	private List<Instance> trainList = new ArrayList<>();
	private List<Instance> testList = new ArrayList<>();
	private List<Integer> attributesList = new ArrayList<>();
	private String[] classes;
	private String attributeName[];

	public void parse(String pathToTrain, String pathToTest) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(pathToTrain)));
			classes = br.readLine().split("\\t"); // Name of the two classes. TAB separated.
			attributeName = br.readLine().split("\\s+"); // Names of the attributes
			String line;

			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split("\\s+");
				String classification = lineArray[0];
				boolean[] values = new boolean[lineArray.length - 1];
				for (int i = 1; i < lineArray.length; i++) {
					values[i - 1] = Boolean.valueOf(lineArray[i]);
				}
				trainList.add(new Instance(values, classification));
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(pathToTest)));
			String line;
			br.readLine(); // Ignore the first two lines, info already obtained from training.
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split("\\s+");
				String classification = lineArray[0];
				boolean[] values = new boolean[lineArray.length - 1];
				for (int i = 1; i < lineArray.length; i++) {
					values[i - 1] = Boolean.valueOf(lineArray[i]);
				}
				testList.add(new Instance(values, classification));
			}
			for (int i = 0; i < trainList.get(i).getValue().length; i++) {
				attributesList.add(i);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String[] getClasses() {
		return classes;
	}

	public List<Instance> getTrainList() {
		return trainList;
	}

	public List<Instance> getTestList() {
		return testList;
	}

	public List<Integer> getAttributesList() {
		return attributesList;
	}

	public String[] getAttributeName() {
		return attributeName;
	}

}
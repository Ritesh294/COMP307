package Part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Parse {

	private List<Iris> trainList = new ArrayList<>();
	private List<Iris> testList = new ArrayList<>();

	public Parse(String trainFilePath, String testFilePath) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(testFilePath)))) {
			String line;
			String[] lineArray; // columns
			while ((line = br.readLine()) != null) {
				lineArray = line.split("\\s+");
				if (lineArray.length == 5) {
					trainList.add(new Iris(Double.parseDouble(lineArray[0]), Double.parseDouble(lineArray[1]),
							Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]), lineArray[4]));
				}
			}
		} catch (IOException e) {
			System.out.println("Error: 1" + e.getMessage());
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(trainFilePath)))) {
			String line;
			String[] lineArray;
			while ((line = br.readLine()) != null) {
				lineArray = line.split("\\s+");
				if (lineArray.length == 5) {
					testList.add(new Iris(Double.parseDouble(lineArray[0]), Double.parseDouble(lineArray[1]),
							Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]), lineArray[4]));
				}

			}
		} catch (IOException e) {
			System.out.println("Error: 2" + e.getMessage());
		}
	}

	public List<Iris> getTrainList() {
		return trainList;
	}

	public List<Iris> getTestList() {
		return testList;
	}
}

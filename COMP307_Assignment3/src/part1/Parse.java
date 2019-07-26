package part1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Parse {
	private ArrayList<Data> train = new ArrayList<>();
	private ArrayList<Data> test = new ArrayList<>();

	public Parse(String trainingFilePath, String testFilePath) {
		try (Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(trainingFilePath)))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				String[] tokens = line.split("\\s+");
				ArrayList<Integer> attributes = new ArrayList<>();
				for (int i = 0; i < tokens.length - 1; i++) {
					attributes.add(Integer.parseInt(tokens[i]));
				}

				train.add(new Data(attributes, Integer.parseInt(tokens[tokens.length - 1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(testFilePath)))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				String[] tokens = line.split("\\s+");
				ArrayList<Integer> attributes = new ArrayList<>();
				for (int i = 0; i < tokens.length; i++) {
					attributes.add(Integer.parseInt(tokens[i]));
				}

				test.add(new Data(attributes, -1));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Data> getTrainInstances() {
		return train;
	}

	public ArrayList<Data> getInstances() {
		return test;
	}
}

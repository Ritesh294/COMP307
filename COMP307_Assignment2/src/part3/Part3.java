

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class Part3 extends GPProblem {
	public static Boolean isTrain;
	public static ArrayList<Instance> trainInstances;
	public static ArrayList<Instance> testInstances;
	public static Variable clumpThickness;
	public static Variable uniformityOfCellSize;
	public static Variable uniformityOfCellShape;
	public static Variable marginalAdhesion;
	public static Variable singleEpithelialCellSize;
	public static Variable bareNuclei;
	public static Variable blandChromatin;
	public static Variable normalNucleoli;
	public static Variable mitoses;
	
	
	public static void main(String[] args) throws InvalidConfigurationException {
		if(args.length == 2) {
		String trainingFilePath = args[0];
		String testFilePath = args[1];
		trainInstances = new ArrayList<>();
		testInstances = new ArrayList<>();
		isTrain = true;
		readFile(trainingFilePath, isTrain);
		isTrain = false; // no longer the training file
		GPProblem problem = new Part3();
		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(200);
		System.out.println("Best discovered function is: ");
		gp.outputSolution(gp.getAllTimeBest());
		readFile(testFilePath, isTrain);
		testing(gp.getAllTimeBest());
		
		}else { //default file paths used
		trainInstances = new ArrayList<>();
		testInstances = new ArrayList<>();
		isTrain = true;
		readFile("./src/training.txt", isTrain);
		isTrain = false; // no longer the training file
		GPProblem problem = new Part3();
		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(200);
		System.out.println("Best discovered function is: ");
		gp.outputSolution(gp.getAllTimeBest());
		readFile("./src/testing.txt", isTrain);
		testing(gp.getAllTimeBest());
		}
	}


	public Part3() throws InvalidConfigurationException {
		super(new GPConfiguration());

		GPConfiguration config = getGPConfiguration();

		clumpThickness = Variable.create(config, "ct", CommandGene.DoubleClass);
		uniformityOfCellSize = Variable.create(config, "ucsi", CommandGene.DoubleClass);
		uniformityOfCellShape = Variable.create(config, "ucsh", CommandGene.DoubleClass);
		marginalAdhesion = Variable.create(config, "ma", CommandGene.DoubleClass);
		singleEpithelialCellSize = Variable.create(config, "secs", CommandGene.DoubleClass);
		bareNuclei = Variable.create(config, "bn", CommandGene.DoubleClass);
		blandChromatin = Variable.create(config, "bc", CommandGene.DoubleClass);
		normalNucleoli = Variable.create(config, "nn", CommandGene.DoubleClass);
		mitoses = Variable.create(config, "m", CommandGene.DoubleClass);

		config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator());
		config.setPopulationSize(500);
		config.setFitnessFunction(new Part3FitnessFunction(trainInstances, clumpThickness, uniformityOfCellSize,
				uniformityOfCellShape, marginalAdhesion, singleEpithelialCellSize, bareNuclei, blandChromatin,
				normalNucleoli, mitoses));
		config.setStrictProgramCreation(true);
	}

	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration config = getGPConfiguration();
		@SuppressWarnings("rawtypes")
		Class[] types = { CommandGene.DoubleClass };
		@SuppressWarnings("rawtypes")
		Class[][] argTypes = { new Class[0] };

		CommandGene[][] nodeSets = { { clumpThickness, uniformityOfCellSize, uniformityOfCellShape, marginalAdhesion,
				singleEpithelialCellSize, bareNuclei, blandChromatin, normalNucleoli, mitoses,
				new Add(config, CommandGene.DoubleClass), new Subtract(config, CommandGene.DoubleClass),
				new Multiply(config, CommandGene.DoubleClass),
				new Terminal(config, CommandGene.DoubleClass, 0.0D, 10.0D, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

		return result;
	}





	public static void testing(IGPProgram program) {
		Object[] NO_ARGS = new Object[0];
		double truePositive = 0.0D;
		double trueNegative = 0.0D;
		double falsePositive = 0.0D;
		double falseNegative = 0.0D;
		for (int i = 0; i < testInstances.size(); i++) {
			clumpThickness.set(testInstances.get(i).getAttributes().get(0));
			uniformityOfCellSize.set(testInstances.get(i).getAttributes().get(1));
			uniformityOfCellShape.set(testInstances.get(i).getAttributes().get(2));
			marginalAdhesion.set(testInstances.get(i).getAttributes().get(3));
			singleEpithelialCellSize.set(testInstances.get(i).getAttributes().get(4));
			bareNuclei.set(testInstances.get(i).getAttributes().get(5));
			blandChromatin.set(testInstances.get(i).getAttributes().get(6));
			normalNucleoli.set(testInstances.get(i).getAttributes().get(7));
			mitoses.set(testInstances.get(i).getAttributes().get(8));

			try {
				int classLabel = 0;
				double result = program.execute_double(0, NO_ARGS);

				if (result > 0.0D) {
					classLabel = 4;
				} else {
					classLabel = 2;
				}

				if (classLabel == testInstances.get(i).getClassLabel()) {
					if (result > 0.0D) {
						truePositive += 1.0D;
					} else {
						trueNegative += 1.0D;
					}
				} else if (result > 0.0D) {
					falsePositive += 1.0D;
				} else {
					falseNegative += 1.0D;
				}
			} catch (ArithmeticException e) {
				e.printStackTrace();
			}
		}

		double measure = (truePositive / (truePositive + falseNegative) + trueNegative / (trueNegative + falsePositive))
				/ 2.0D;

		System.out.printf("Test data @ : %.2f", measure * 100.0D);
	}

	public static void readFile(String path, Boolean isTrain) {
		ArrayList<Instance> instances = new ArrayList<>();
		File file = new File(path);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] splitLine = line.split(",");

				Instance data = new Instance();

				data.setID(Double.parseDouble(splitLine[0]));
				for (int i = 1; i < splitLine.length - 1; i++) {
					data.getAttributes()
							.add(Double.valueOf(splitLine[i].equals("?") ? -1.0D : Double.parseDouble(splitLine[i])));
				}
				data.setLabel(Integer.parseInt(splitLine[(splitLine.length - 1)]));
				instances.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
		if (isTrain == true) {
			trainInstances = instances;
		} else {
			testInstances = instances;
		}
	}
}

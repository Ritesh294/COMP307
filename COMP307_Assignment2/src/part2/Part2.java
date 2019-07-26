

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class Part2 extends GPProblem {
	private Variable x_Var;
	private List<Double> xx_Vals;
	private List<Double> yy_Vals;
	
	
	  public static void main(String[] args) throws InvalidConfigurationException
	  {
	    GPProblem problem = new Part2(args[0]);
	    GPGenotype gp = problem.create();
	    gp.setVerboseOutput(true);
	    gp.evolve(200);
	    System.out.println("----------------------------------BEST FUNCTION----------------------------------");
	    gp.outputSolution(gp.getAllTimeBest());
	  }

	public Part2(String fName) throws InvalidConfigurationException {
		super(new GPConfiguration());

		xx_Vals = new ArrayList<>();
		yy_Vals = new ArrayList<>();

		parse(fName);
		GPConfiguration config = getGPConfiguration();
		x_Var = Variable.create(config, "X", CommandGene.DoubleClass);
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setPopulationSize(500);
		config.setFitnessFunction(new FitnessFunction(xx_Vals, yy_Vals, x_Var));
		config.setStrictProgramCreation(true);
	}

	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		// TODO Auto-generated method stub
		GPConfiguration config = getGPConfiguration();
		@SuppressWarnings("rawtypes")
		Class[] types = { CommandGene.DoubleClass };
		@SuppressWarnings("rawtypes")
		Class[][] argTypes = { new Class[0] };

		CommandGene[][] nodeSets = {
				{ x_Var, new Add(config, CommandGene.DoubleClass), new Subtract(config, CommandGene.DoubleClass),
						new Multiply(config, CommandGene.DoubleClass), new Divide(config, CommandGene.DoubleClass),
						new Terminal(config, CommandGene.DoubleClass, 0.0, 10.0, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);
		return result;
	}

	private void parse(String filePath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			br.readLine();
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\s+");
				xx_Vals.add(Double.parseDouble(split[1]));
				yy_Vals.add(Double.parseDouble(split[2]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

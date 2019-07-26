

import java.util.ArrayList;
import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunction extends GPFitnessFunction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Variable xVar;
	private static Object[] NO_ARGS = new Object[0];
	private List<Double> input;
	private List<Double> output;

	public FitnessFunction(List<Double> inputf, List<Double> outputf, Variable x) {
		this.input = new ArrayList<>(inputf);
		this.output = new ArrayList<>(outputf);
		xVar = x;
	}

	@Override
	protected double evaluate(IGPProgram program) {
		// TODO Auto-generated method stub
		double error = 0.0D;
		
		for (int i = 0; i < input.size(); i++) {
			xVar.set(input.get(i));

			try {
				double value = program.execute_double(0, NO_ARGS);
				error += Math.abs(value - output.get(i));
				if (Double.isInfinite(error)) {
					return Double.MAX_VALUE;
				}
			} catch (ArithmeticException e) {
				throw e;
			}
		}

		if (error < 0.001D) {
			error = 0.0D;
		}
		return error;
	}
}

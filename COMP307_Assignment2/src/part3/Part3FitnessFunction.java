

import java.util.ArrayList;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class Part3FitnessFunction
  extends GPFitnessFunction
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private ArrayList<Instance> instances = new ArrayList<>();
  
  private Variable clumpThickness;
  
  private Variable uniformityOfCellSize;
  private Variable uniformityOfCellShape;
  private Variable marginalAdhesion;
  private Variable singleEpithelialCellSize;
  private Variable bareNuclei;
  private Variable blandChromatin;
  private Variable normalNucleoli;
  private Variable mitoses;
  private static Object[] NO_ARGS = new Object[0];
  
  Part3FitnessFunction(ArrayList<Instance> in, Variable ct, Variable ucsi, Variable ucsh, Variable ma, Variable secs, Variable bn, Variable bc, Variable nn, Variable m)
  {
    instances = in;
    clumpThickness = ct;
    uniformityOfCellSize = ucsi;
    uniformityOfCellShape = ucsh;
    marginalAdhesion = ma;
    singleEpithelialCellSize = secs;
    bareNuclei = bn;
    blandChromatin = bc;
    normalNucleoli = nn;
    mitoses = m;
  }
  
  @Override
protected double evaluate(IGPProgram program)
  {
    double truePositive = 0.0D;double trueNegative = 0.0D;double falsePositive = 0.0D;double falseNegative = 0.0D;
    for (int i = 0; i < instances.size(); i++)
    {
      clumpThickness.set(instances.get(i).getAttributes().get(0));
      uniformityOfCellSize.set(instances.get(i).getAttributes().get(1));
      uniformityOfCellShape.set(instances.get(i).getAttributes().get(2));
      marginalAdhesion.set(instances.get(i).getAttributes().get(3));
      singleEpithelialCellSize.set(instances.get(i).getAttributes().get(4));
      bareNuclei.set(instances.get(i).getAttributes().get(5));
      blandChromatin.set(instances.get(i).getAttributes().get(6));
      normalNucleoli.set(instances.get(i).getAttributes().get(7));
      mitoses.set(instances.get(i).getAttributes().get(8));
      try
      {
        int classLabel = 0;
        double result = program.execute_double(0, NO_ARGS);
        
        if (result > 0.0D) {
          classLabel = 4;
        } else {
          classLabel = 2;
        }       
        if (classLabel == instances.get(i).getClassLabel()) {
          if (result > 0.0D) {
            truePositive += 1.0D;
          } else {
            trueNegative += 1.0D;
          }
        }
        else if (result > 0.0D) {
          falsePositive += 1.0D;
        } else {
          falseNegative += 1.0D;
        }
      }
      catch (ArithmeticException e)
      {
        e.printStackTrace();
      }
    }   
    double measure = (truePositive / (truePositive + falseNegative) + trueNegative / (trueNegative + falsePositive)) / 2.0D;
    
    return measure * 100.0D;
  }
}



import java.util.ArrayList;

public class Instance {
  public ArrayList<Double> attributes; // all the attributes x1,x2,x3....
  public double id;
  public double classLabel;
  
  public Instance() {
    attributes = new ArrayList<>();
  }
  
  public double getID() {
    return id;
  }
  
  public void setID(double d) {
    id = d;
  }
  
  public ArrayList<Double> getAttributes() {
    return attributes;
  }
  
  public double getClassLabel() {
    return classLabel;
  }
  
  public void setLabel(int classLabel) {
    this.classLabel = classLabel;
  }
}

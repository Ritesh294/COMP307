package Part2;

public class Node {
	
	private Node left;
	private Node right;
	private String attribute;
	private String classifcation;
	private double probability;
	private boolean leafNode;
	private int attributeInt;

	public Node(Node left, Node right, String attribute, int attributeInt) {
		this.left = left;
		this.right = right;
		this.attribute = attribute;
		this.attributeInt = attributeInt;
		this.leafNode = false;
	}

	public Node(String classificationN, double probability) {
		this.probability = probability;
		this.classifcation = classificationN;
		this.leafNode = true;
	}

	public void report(String indent) {
		if (!leafNode) {
			System.out.format("%s%s = True:\n", indent, attribute);
			left.report(indent + " ");
			System.out.format("%s%s = False:\n", indent, attribute);
			right.report(indent + " ");
		} else {
			System.out.format("%sClass %s, Probability = %4.2f\n", indent, classifcation, probability);
		}

	}
	
	public Instance predict(Instance instance) {
		if (this.isLeafNode()) {
			return new Instance(instance.getValue().clone(), this.classifcation);
		} else {
			if (instance.getValue()[attributeInt]) {
				return left.predict(instance); //if true
			} else {
				return right.predict(instance);
			}
		}
	}
	
	public boolean isLeafNode() {
		return leafNode;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public String getAttribute() {
		return attribute;
	}

	public double getProbability() {
		return probability;
	}
}

package Part1;

import java.util.ArrayList;
import java.util.List;

public class Iris {
	
	//Iris flower Parameters
	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	
	//Neighbours of the Irirs
	private List<Iris> irisNeighbours;
	
	//The type of iris flower
	private String typeOfIris;
	
	
	public Iris(double sepalL, double sepalW, double petalL, double petalW, String type) {
		/*
		 * if(type instanceof IrisType) { typeOfIris = array; } else { throw new
		 * IllegalArgumentException("Invalid type of Iris"); }
		 */
		this.sepalLength = sepalL;
		this.sepalWidth = sepalW;
		this.petalLength = petalL;
		this.petalWidth = petalW;
		this.typeOfIris = type;
		this.irisNeighbours = new ArrayList<>(); // initialize each iris with no neighbours. (USE GETTERS AND SETTERS)
	}


	public double getSepalLength() {
		return sepalLength;
	}


	public double getSepalWidth() {
		return sepalWidth;
	}


	public double getPetalLength() {
		return petalLength;
	}


	public double getPetalWidth() {
		return petalWidth;
	}


	public String getTypeOfIris() {
		return typeOfIris;
	}


	public List<Iris> getIrisNeighbours() {
		return irisNeighbours;
	}


	public void setIrisNeighbours(List<Iris> irisNeighbours) {
		this.irisNeighbours = irisNeighbours;
	}
	
	
	
	

}

package part1;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  if (args.length == 2) { new NaiveBayes(args[0],args[1]); } 
		  
		  else {
		  System.out.println("ERROR: Invalid Arguments, Attempting default filepaths"); 
			new NaiveBayes("./ass3DataFiles/part1/spamLabelled.dat","./ass3DataFiles/part1/spamUnLabelled.dat");
		  }
		 

	}

}

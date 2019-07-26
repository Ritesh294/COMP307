package Part3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseP3 {
	private ArrayList<PerceptronImage> imagesList = new ArrayList<>();
	
	public ArrayList<PerceptronImage> getImagesList() {
		return imagesList;
	}

	public void parse(File file) {
		try {
			Scanner f = new Scanner(file);
			while (f.hasNext()) {
				boolean[][] newimage = null;
				java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
				if (!f.next().equals("P1"))
					System.out.println("Not a P1 PBM file");
				String category = f.next().substring(1);
				int rows = f.nextInt();
				int cols = f.nextInt();
				newimage = new boolean[rows][cols];
				for (int r = 0; r < rows; r++) {
					for (int c = 0; c < cols; c++) {
						newimage[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
					}
				}
				imagesList.add(new PerceptronImage(category, newimage));
			}
			f.close();
		} catch (IOException e) {
			System.out.println("Load from file failed");
		}
	}
}

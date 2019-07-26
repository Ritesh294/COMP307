package Part3;

import java.util.Random;

public class Feature {
	private int[] row;
	private int[] col;
	private boolean[] sgn;
	private Random randomGen;
	private boolean isDummy;

	public Feature(int width, int height, Random randomVal) {
		this.row = new int[4];
		this.col = new int[4];
		this.randomGen = randomVal;
		this.sgn = new boolean[4];
		this.isDummy = false;

		for (int i = 0; i < row.length; i++) {
			int pos = randomGen.nextInt(width);
			this.row[i] = pos;
		}
		for (int i = 0; i < col.length; i++) {
			int pos = randomGen.nextInt(height);
			this.col[i] = pos;
		}
		for (int i = 0; i < sgn.length; i++) {
			boolean pixel = randomGen.nextBoolean();
			this.sgn[i] = pixel;
		}
	}

	public Feature() { // trigger here
		this.isDummy = true;
	}

	public int compare(PerceptronImage img) {
		int sum = 0;
		for (int i = 0; i < 4; i++)
			if (img.getImagePixel(this.row[i], this.col[i]) == sgn[i])
				sum++;
		return (sum >= 3) ? 1 : 0;
	}

	public boolean isDummy() {
		return this.isDummy;
	}

	public int[] getRow() {
		return row;
	}

	public int[] getCol() {
		return col;
	}

	public boolean[] getSgn() {
		return sgn;
	}

	public void setDummy(boolean isDummy) {
		this.isDummy = isDummy;
	}
}
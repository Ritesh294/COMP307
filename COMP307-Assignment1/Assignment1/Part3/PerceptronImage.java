package Part3;

public class PerceptronImage {
	private boolean category;
	private boolean[][] imgArray;

	public PerceptronImage(String classification, boolean[][] newimage) {
		if(classification.equals("X")) { //turn X or O into boolean
			this.category = true;
		}
		else {
			this.category = false;
		}
		this.imgArray = newimage;
	}
	
	public int getCategory() {
		return category ? 1 : 0;
	}
	public boolean getImagePixel(int x, int y) {
		return imgArray[x][y];
	}
	
}
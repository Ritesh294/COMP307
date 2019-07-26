package part1;

import java.util.ArrayList;

public class Data {
	private ArrayList<Integer> attributes;
    private int type;

    public Data(ArrayList<Integer> attribute,int type) {
        this.attributes  = attribute;
        this.type = type;
    }

    public ArrayList<Integer> getAttributes() {
        return attributes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

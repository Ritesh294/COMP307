package Part2;
//tuple
public class Pair<BoolKey, Values> {

	private Values val;
	private BoolKey key;

	public Pair(BoolKey key, Values value) {
		this.key = key;
		this.val= value;
	}

	public Values getValues() {
		return val;
	}

	public BoolKey getBoolKey() {
		return key;
	}

}
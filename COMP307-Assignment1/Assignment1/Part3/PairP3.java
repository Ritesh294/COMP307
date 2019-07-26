package Part3;
//tuple
public class PairP3<BoolKey, Values> {

	private Values val;
	private BoolKey key;

	public PairP3(BoolKey key, Values value) {
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
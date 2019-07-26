package Part2;

import java.util.Arrays;

public class Instance {
		private boolean[] value;
		private String Classification;

		public Instance(boolean[] boolValue, String classification) {
			this.value = boolValue;
			this.Classification = classification;
		}

		public boolean[] getValue() {
			return value;
		}

		public String getClassification() {
			return Classification;
		}

		@Override
		public String toString() {
			String str = "";
			for (boolean b : value) {
				str += b + " | ";
			}
			str += Classification;
			return str;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Instance) {
				Instance a = (Instance) obj;
				if (Arrays.equals(value, a.getValue()) && Classification.equals(a.getClassification()))
					return true;
			}
			return false;
		}
}

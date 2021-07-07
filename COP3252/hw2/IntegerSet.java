public class IntegerSet {

	public static final int length = 101;
	
	public boolean[] set;
	
	public IntegerSet() {
		set = new boolean[length];
	}
	
	public IntegerSet union(IntegerSet iSet) {
		IntegerSet uSet = new IntegerSet();
		for (int i = 0; i < length; i++)
			uSet.set[i] =  this.set[i] || iSet.set[i];
		return uSet;
	}
	
	public IntegerSet intersection(IntegerSet iSet) {
		IntegerSet uSet = new IntegerSet();
		for (int i = 0; i < length; i++)
			uSet.set[i] =  this.set[i] && iSet.set[i];
		return uSet;
	}
	
	public IntegerSet insertElement(int data) {
		this.set[data] = true;
		return this;
	}
	
	public IntegerSet deleteElement(int data) {
		this.set[data] = false;
		return this;
	}
	
	public boolean isEqualTo(IntegerSet iSet) {
		for (int i = 0; i < length; i++)
			if (this.set[i] != iSet.set[i])
				return false;
		return true;
	}
	
	public String toString() {
		String str = new String();
		for (int i = 0; i < length; i++)
			str += this.set[i] == true ? i + " " : "";
		return str.length() > 0 ? str : "---";
	}
	
}

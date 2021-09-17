package proj.jtyler.linalgebra;

public class Subspace {

	private Vector[] basis;
	
	public Subspace() {
		this.basis = new Vector[0];
	}
	
	public Subspace(Vector...vectors) {
		this.basis = vectors;
	}
	
	public boolean contains(Vector vec) {
		return new Matrix(basis).Append(vec).isConsistent();
	}
	
	public Matrix toMatrix() {
		return new Matrix(basis);
	}
	
	public int dimensions() {
		return basis.length;
	}
	
	
	
	
}

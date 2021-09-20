public class Subspace {

	private Vector[] basis;
	
	public Subspace() {
		this.basis = new Vector[0];
	}
	
	public Subspace(Vector...vectors) {
		this.basis = vectors;
	}
	
	public Vector[] basis() {
		return basis;
	}

	public Subspace clone() {
		return new Subspace(basis);
	}

	public Subspace addBasis(Vector vec) {
		Vector[] tempBasis = new Vector[dimensions()+1];
		for (int b = 0; b < dimensions(); b++)
			tempBasis[b] = basis[b];
		tempBasis[dimensions()] = vec;
		basis = tempBasis;
		return this;
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

	public boolean orthagonalBasis(){
		for (int i = 0; i < dimensions(); i++)
			for (int j = 0; j < dimensions(); j++)
				if (i != j && !basis[i].isOrthogonalTo(basis[j]))
					return false;
		return true;
	}
	
	public Subspace ToOrthagonalBasis(){
		Subspace V = new Subspace();
		Vector[] tempBasis = new Vector[dimensions()];
		for (int b = 0; b < dimensions(); b++){
			tempBasis[b] = basis[b].OrthagonalProjection(V);
			V.addBasis(tempBasis[b]);
		}
		basis = tempBasis;
		return this;
	}

	public Subspace ToOrthanormalBasis() {
		this.ToOrthagonalBasis();
		for (int b = 0; b < dimensions(); b++)
			basis[b] = basis[b].Normalize();
		return this;
	}
	
	
}

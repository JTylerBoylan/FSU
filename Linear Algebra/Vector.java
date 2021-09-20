public class Vector extends Matrix {

	// Constructors
	
	public Vector(float...values) {
		super(values.length,1);
		for (int r = 0; r < rows; r++)
			matrix[r][0] = values[r];
	}
	
	public Vector(int n) {
		super(n,1);
	}
	
	public Vector(Vector vector) {
		this(vector.rows);
		for (int r = 0; r < rows; r++)
			matrix[r][0] = vector.matrix[r][0];
	}
	
	
	// Member Functions

	public Vector clone() {
		return new Vector(this);
	}
	
	public int size() {
		return rows;
	}
	
	public boolean inSpan(Matrix mat) {
		return new Matrix(mat,this).isConsistent();
	}
	
	public boolean dependentWith(Vector vec2) {
		if (vec2.rows != rows)
			return false;
		float scale = matrix[0][0]/vec2.matrix[0][0];
		for (int r = 1; r < rows; r++)
			if (matrix[r][0]/vec2.matrix[r][0] != scale)
				return false;
		return true;
			
	}

	public float innerProduct(Vector vec2) {
		float sum = 0;
		for (int r = 0; r < rows; r++){
			try {
				sum += matrix[r][0]*vec2.matrix[r][0];
			} catch (IndexOutOfBoundsException e){}
		}
		return sum;
	}

	public float normal() {
		return (float) Math.sqrt(innerProduct(this));
	}

	public boolean isOrthogonalTo(Vector vec2){
		return (long)(innerProduct(vec2)*100000L) == 0;
	}
	
	// Actions
	
	public Vector Addition(Vector add) {
		if (add.rows != rows)
			return this;
		for (int r = 0; r < rows; r++)
			matrix[r][0] += add.matrix[r][0];
		return this;
	}
	
	public Vector Scale(float scalar) {
		for (int r = 0; r < rows; r++)
			matrix[r][0] *= scalar;
		return this;
	}

	public Vector Normalize() {
		return this.Scale(1/normal());
	}

	public Vector ProjectOn(Subspace W){
		if (W.dimensions() == 0)
			return new Vector(size());
		Vector tempVector = new Vector(W.basis()[0].size());
		for (int b = 0; b < W.dimensions(); b++){
			Vector vec = W.basis()[b].clone();
			float scale = this.clone().innerProduct(vec) / vec.clone().innerProduct(vec);
			tempVector.Add(vec.Scale(scale));
		}
		this.matrix = tempVector.matrix;
		this.rows = tempVector.rows;
		return this;
	}

	public Vector OrthagonalProjection(Subspace W) {
		Vector proj = this.clone().ProjectOn(W);
		return this.Addition(proj.Scale(-1));
	}
	
}

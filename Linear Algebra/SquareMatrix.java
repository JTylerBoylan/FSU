public class SquareMatrix extends Matrix {

	public SquareMatrix(int n) {
		super(n,n);
	}
	
	public SquareMatrix(Matrix mat) {
		super(mat.submatrix(mat.rows > mat.cols ? mat.cols : mat.rows, mat.rows > mat.cols ? mat.cols : mat.rows));
	}
	
	public SquareMatrix(float[]...mat) {
		super(mat);
	}
	
	// Types
	
	public SquareMatrix Identity() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols;c ++)
				matrix[r][c] = r == c ? 1 : 0;
		return this;
	}
	
	// Member Functions
	
	public int size() {
		return rows;
	}
	
	public float determinant() {
		if (this.size() == 1)
			return matrix[0][0];
		float d = 0f;
		for (int c = 1; c <= cols; c++) {
			float m = c%2 == 0 ? -1 : 1;
			float v1 = matrix[0][c-1];
			float v2 = this.clone().TrimRow(1).TrimColumn(c).toSquareMatrix().determinant();
			d += m*v1*v2;
		}
		return d;
	}
	
	public boolean isInvertible() {
		Matrix mat = new Matrix(this);
		mat.ReduceToReducedEchelon();
		return mat.equals(new SquareMatrix(this.rows).Identity());
	}
	
	@Override
	public SquareMatrix clone() {
		return new SquareMatrix(this);
	}
	
	// Matrix Operations
	
	public SquareMatrix Power(int k) {
		if (k < 1)
			return this;
		for (int i = 0; i < k; i++)
			MultiplyBy(this);
		return this;
	}
	
	public SquareMatrix Invert() {
		Matrix mat = new Matrix(this).Append(new SquareMatrix(this.rows).Identity());
		mat.ReduceToReducedEchelon();
		matrix = mat.submatrix(1, rows, cols+1, mat.cols).matrix;
		return this;
	}
	
	// Eigenvectors/values
	
	public float EigenvalueOf(Vector vec) {
		if (vec.size() != this.rows)
			return 0;
		Matrix tempMatrix = this.clone().MultiplyBy(vec);
		float scale = tempMatrix.matrix[0][0] / vec.matrix[0][0];
		if (scale == 0)
			return 0;
		tempMatrix.Scale(1/scale);
		return tempMatrix.equals(vec) ? scale : 0;
	}
	
	public boolean isEigenvector(Vector vec) {
		return EigenvalueOf(vec) != 0;
	}
	
	public Subspace SubspaceOfEigenvalue(float lambda) {
		return this.clone().Subtract(this.clone().Identity().Scale(lambda)).nullSpace();
	}
	
}

package proj.jtyler.linalgebra;

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
	
	public int size() {
		return rows;
	}
	
	public boolean spans(Matrix mat) {
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
	
}

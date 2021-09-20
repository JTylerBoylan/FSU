public class Matrix {
	
	// Local Variables
	
	protected int rows, cols;
	protected float[][] matrix;
	
	// Constructors
	
	public Matrix(int n, int m) {
		this.rows = n;
		this.cols = m;
		matrix = new float[n][m];
	}
	
	public Matrix(float[]...matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}
	
	public Matrix(Matrix... matrices) {
		this.rows = matrices.length == 0 ? 0 : matrices[0].rows;
		this.cols = 0;
		this.matrix = new float[rows][];
		for (Matrix mat : matrices)
			Append(mat);
	}
	
	// Member Functions
	
	public int rows() {
		return rows;
	}
	
	public int columns() {
		return cols;
	}
	
	public float[][] to2DArray(){
		return matrix;
	}
	
	public float at(int row, int col) {
		return matrix[--row][--col];
	}
	
	public boolean isEmpty() {
		return rows == 0 || cols == 0;
	}
	
	public int leadColumnOf(int row) {
		row--;
		for (int c = 0; c < cols; c++)
			if ((long)(matrix[row][c]*10000L) != 0)
				return ++c;
		return cols+1;
	}
	
	public boolean isLeadingEntry(int row, int col) {
		return --col == leadColumnOf(--row);
	}
	
	public boolean isInEchelonForm() {
		for (int r = 2; r < rows+1; r++)
			if (leadColumnOf(r) <= leadColumnOf(r-1))
				return false;
		return true;
	}
	
	public boolean isInReducedEchelonForm() {
		if (!isInEchelonForm())
			return false;
		for (int r = 2; r < rows+1; r++) {
			int leadCol = leadColumnOf(r)-1;
			if (leadCol == cols)
				continue;
			if (matrix[r-1][leadCol] != 1)
				return false;
			for (int r2 = 0; r2 < rows; r2++) 
				if (r2 != r && (long)(matrix[r2][leadCol]*100000L) != 0)
					return false;
		}
		return true;
	}
	
	public boolean isPivotColumn(int col) {
		Matrix mat = this.clone();
		mat.ReduceToReducedEchelon();
		for (int r = 0; r < rows; r++) {
			if (mat.leadColumnOf(r+1) == col)
				return true;
		}
		return false;
	}
	
	public boolean isConsistent() {
		return !isPivotColumn(cols);
	}
	
	public boolean isFree(int col) {
		return !isPivotColumn(col);
	}
	
	public boolean isIndependent() {
		if (cols > rows)
			return false;
		Matrix mat = new Matrix(this,new Vector(new float[rows]));
		mat.ReduceToEchelon();
		for (int c = 0; c < cols; c++)
			if (mat.isFree(c))
				return false;
		return true;
	}
	
	public boolean isOneToOne() {
		return isIndependent();
	}
	
	public boolean isOnto() {
		if (rows > cols)
			return false;
		Matrix mat = this.clone();
		mat.ReduceToEchelon();
		for (int r = 1; r <= rows; r++)
			if (mat.leadColumnOf(r) == cols+1)
				return false;
		return true;
	}
	
	public boolean equals(Matrix mat2) {
		if (rows != mat2.rows || cols != mat2.cols)
			return false;
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				if ((long)(matrix[r][c]*100000L) != (long)(mat2.matrix[r][c]*100000L))
					return false;
		return true;
	}
	
	public Matrix submatrix(int r1, int r2, int c1, int c2) {
		r1--;c1--;
		float[][] mat = new float[r2-r1][c2-c1];
		for (int r = r1; r < r2; r++)
			for (int c = c1; c < c2; c++)
				mat[r-r1][c-c1] = matrix[r][c];
		return new Matrix(mat);
	}
	
	public Matrix submatrix(int r2, int c2) {
		return submatrix(1,r2,1,c2);
	}
	
	public Subspace columnSpace() {
		Matrix tempMatrix = this.clone();
		boolean[] free = new boolean[cols];
		for (int c = 0; c < cols; c++)
			free[c] = tempMatrix.isFree(c+1);
		int o = 0;
		for (int c = 0; c < cols; c++)
			if (free[c])
				tempMatrix.TrimColumn(c+1-o++);
		return new Subspace(tempMatrix.toVectorArray());		
	}
	
	public Subspace nullSpace() {
		Matrix red = this.clone().ReduceToReducedEchelon();
		SquareMatrix sq = new SquareMatrix(cols).Identity();
		for (int r = 1; r <= rows; r++) {
			int lead = red.leadColumnOf(r);
			for (int c = lead+1; c <= cols; c++)
				sq.Set(lead, c, -red.at(r, c));
		}	
		int o = 0;
		for (int c = 1; c <= cols; c++)
			if (!isFree(c))
				sq.TrimColumn(c-o++);
		return new Subspace(sq.toVectorArray());
	}
	
	// Actions
	
	public Matrix Append(Matrix matrix2) {
		if (rows != matrix2.rows)
			return this;
		float[][] tempMatrix = new float[rows][cols+matrix2.cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols+matrix2.cols; c++)
				tempMatrix[r][c] = c < cols ? matrix[r][c] : matrix2.matrix[r][c-cols];
		}
		matrix = tempMatrix;
		cols += matrix2.cols;
		return this;
	}
	
	public Matrix Organize() {
		boolean organized = false;
		while(!organized) {
			organized = true;
			for (int r = 0; r < rows-1; r++) {
				if (leadColumnOf(r+1) > leadColumnOf(r+2))
					RowInterchange(r+1,r+2);
			}
		}
		return this;
	}
	
	public Matrix TrimRow(int row) {
		if (row > rows || row < 1)
			return this;
		float[][] tempMatrix = new float[rows-1][cols];
		for (int r = 0; r < rows-1; r++)
			tempMatrix[r] = r >= row-1 ? matrix[r+1] : matrix[r];
		matrix = tempMatrix;
		rows--;
		return this;
	}
	
	public Matrix TrimColumn(int column) {
		if (column > cols || column < 1)
			return this;
		float[][] tempMatrix = new float[rows][cols-1];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols-1; c++)
				tempMatrix[r][c] = c >= column-1 ? matrix[r][c+1] : matrix[r][c];
		matrix = tempMatrix;
		cols--;
		return this;
	}
	
	public Matrix Set(int r, int c, float val) {
		matrix[--r][--c] = val;
		return this;
	}
	
	public SquareMatrix toSquareMatrix() {
		return new SquareMatrix(this);
	}
	
	public Vector[] toVectorArray() {
		Vector[] vectors = new Vector[cols];
		for (int c = 0; c < cols; c++) {
			float[] vals = new float[rows];
			for (int r = 0; r < rows; r++) {
				vals[r] = matrix[r][c];
			}
			vectors[c] = new Vector(vals);
		}
		return vectors;
	}
	
	public Matrix clone() {
		return new Matrix(this);
	}
	
	// Matrix Operations
	
	public Matrix MultiplyBy(Matrix matrix2) {
		if (cols != matrix2.rows)
			return this;
		Matrix mat = new Matrix(rows,matrix2.cols);
		for (int r = 0; r < rows; r++) 
			for (int c = 0; c < matrix2.cols; c++) 
				for (int i = 0; i < cols; i++)
					mat.matrix[r][c] += matrix[r][i]*matrix2.matrix[i][c];
		this.matrix = mat.matrix;
		this.cols = matrix2.cols;
		return this;
	}
	
	public Matrix Scale(float scaleBy) {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				matrix[r][c] *= scaleBy;
		return this;
	}
	
	public Matrix Add(Matrix matrix2) {
		if (rows != matrix2.rows && cols != matrix2.cols)
			return this;
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				matrix[r][c] += matrix2.matrix[r][c];
		return this;
	}
	
	public Matrix Subtract(Matrix matrix2) {
		if (rows != matrix2.rows && cols != matrix2.cols)
			return this;
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				matrix[r][c] -= matrix2.matrix[r][c];
		return this;
	}
	
	public Matrix Transpose() {
		float[][] mat = new float[cols][rows];
		for (int c = 0; c < cols; c++)
			for (int r = 0; r < rows; r++)
				mat[c][r] = matrix[r][c];
		this.matrix = mat;
		int t = rows;
		this.rows = cols;
		this.cols = t;
		return this;
	}
	
	// Row Operations
	
	public Matrix RowReplacement(int replacedRow, int byRow, float scaledBy){
		replacedRow--;
		byRow--;
		for (int c = 0; c < cols; c++)
			matrix[replacedRow][c] += matrix[byRow][c]*scaledBy;
		return this;
	}
	
	public Matrix RowInterchange(int row1, int row2) {
		row1--;
		row2--;
		float[] tempRow = matrix[row1];
		matrix[row1] = matrix[row2];
		matrix[row2] = tempRow;
		return this;
	}
	
	public Matrix RowScale(int row, float scaleBy) {
		row--;
		for (int c = 0; c < cols; c++)
			matrix[row][c] *= scaleBy;
		return this;
	}
	
	// Reductions
	
	public Matrix ReduceToEchelon() {
		for (int r = 0; r < rows; r++) {
			Organize();
			int lead = leadColumnOf(r+1)-1;
			if (lead == cols)
				break;
			for (int r2 = r+1; r2 < rows; r2++) 
				RowReplacement(r2+1,r+1,-matrix[r2][lead]/matrix[r][lead]);
		}
		return this;
	}
	
	public Matrix ReduceToReducedEchelon() {
		ReduceToEchelon();
		for (int r = rows-1; r >= 0; r--) {
			Organize();
			int lead = leadColumnOf(r+1)-1;
			if (lead == cols)
				continue;
			RowScale(r+1,1.0f/matrix[r][lead]);
			for (int r2 = r-1; r2 >= 0; r2--)
				RowReplacement(r2+1,r+1,-matrix[r2][lead]/matrix[r][lead]);
		}
		return this;
	}
	
	// System Functions
	
	public void print() {
		if (isEmpty()) {
			System.out.println("|\t|\n");
			return;
		}
		for (int r = 0; r < rows; r++) {
			System.out.print("|\t");
			for (int c = 0; c < cols; c++) {
				String format = matrix[r][c] < 0 ? "%.4f" : " %.4f";
				format += matrix[r][c] >= 10 || matrix[r][c] <= -10 ? "\t" : "\t\t";
				matrix[r][c] = (long)(matrix[r][c]*100000L) == 0 ? 0f : matrix[r][c];
				System.out.printf(format,matrix[r][c]);
			}
			System.out.println("|");
		}
		System.out.println();
	}

}

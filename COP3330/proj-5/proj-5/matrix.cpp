#include "matrix.h"

int index(unsigned int rows, unsigned int cols, unsigned int r, unsigned int c, bool rm);

// DEFAULT CONSTRUCTOR
Matrix::Matrix(unsigned int row , unsigned int column, unsigned int value){
    rows = row;
    cols = column;
    rmajor = true;
    matrix = new int[rows*cols];
    for (int i = 0; i < rows*cols; i++)
        matrix[i] = value;
}

//COPY CONSTRUCTOR
Matrix::Matrix(const Matrix &mtx){
    rows = mtx.rows;
    cols = mtx.cols;
    rmajor = mtx.rmajor;
    matrix = new int[rows*cols];
    for (int i = 0; i < rows*cols; i++)
        matrix[i] = mtx.matrix[i];
}

//DESTRUCTOR
Matrix::~Matrix(){
    delete[] matrix;
}

//COPY OPERATOR
Matrix Matrix::operator=(const Matrix &mtx){
    rows = mtx.rows;
    cols = mtx.cols;
    rmajor = mtx.rmajor;
    matrix = new int[rows*cols];
    for (int i = 0; i < rows*cols; i++)
        matrix[i] = mtx.matrix[i];
    return *this;
}

// Get value from (r,c)
int Matrix::get(unsigned int r, unsigned int c){
    if (!V(r-1,c-1))
        return -1;
    return matrix[index(rows,cols,r-1,c-1,rmajor)];
}

// Set value to (r,c)
bool Matrix::set(unsigned int r, unsigned int c, unsigned int value){
    if (!V(r-1,c-1))
        return false;
    matrix[index(rows,cols,r-1,c-1,rmajor)] = value;
    return true;
}

// Set value straight to array (i)
bool Matrix::set(unsigned int i, unsigned int value){
    if (i >= rows*cols)
        return false;
    matrix[i] = value;
    return true;
}

//ADDITION OPERATOR
Matrix Matrix::operator+(const Matrix &mtx){
    Matrix sum(rows,cols);
    if (rows == mtx.rows && cols == mtx.cols)
        for (int i = 0; i < rows*cols; i++)
            sum.set(i,matrix[i]+mtx.matrix[i]);
    return sum;
}

//MULTIPLICATION OPERATOR
Matrix Matrix::operator*(const Matrix &mtx){
    if (cols != mtx.rows){
        Matrix m(rows,cols);
        return m;
    }
    Matrix mul(rows,mtx.cols);
    for (int r = 0; r < rows; r++){
        for (int c = 0; c < mtx.cols; c++){
            int sum = 0;
            for (int k = 0; k < cols; k++){
                sum += matrix[index(rows,cols,r,k,rmajor)] * mtx.matrix[index(mtx.rows,mtx.cols,c,k,mtx.rmajor)];
            }
            mul.matrix[index(rows,mtx.cols,r,c,true)] = sum;
        }
    }
    return mul;
}

//TO ROW MAJOR
void Matrix::torowmajor(){
    if (!rmajor){
        int *m = new int[rows*cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                m[index(rows,cols,r,c,true)] = matrix[index(rows,cols,r,c,false)];
        for (int i = 0; i < rows*cols; i++)
            matrix[i] = m[i];
        delete[] m;
    }
    rmajor = true;
}

//TO COLUMN MAJOR
void Matrix::tocolumnmajor(){
    if (rmajor){
        int *m = new int[rows*cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                m[index(rows,cols,r,c,false)] = matrix[index(rows,cols,r,c,true)];
        for (int i = 0; i < rows*cols; i++)
            matrix[i] = m[i];
        delete[] m;
    }
    rmajor = false;
}

//Print matrix array
void Matrix::printinternal(){
    for (int i = 0; i < rows*cols; i++)
        cout << matrix[i] << "\t";
    cout << endl;
}

//Transpose matrix
Matrix Matrix::transpose(){
    Matrix T(cols, rows);
    for (int r = 1; r <= rows; r++){
        for (int c = 1; c <= cols; c++){
            T.set(c,r,matrix[index(rows,cols,r-1,c-1,rmajor)]);
        }
    }
    return T;
}

//Get submatrix
Matrix Matrix::submatrix(unsigned int startrow, unsigned int endrow, unsigned int startcol, unsigned int endcol){
    int row_size = endrow-startrow+1;
    int col_size = endcol-startcol+1;
    Matrix sub(row_size,col_size);
    for (int r = 1; r <= row_size; r++){
        for (int c = 1; c <= col_size; c++){
            sub.set(r,c,matrix[index(rows,cols,r+startrow-2,c+startcol-2,rmajor)]);
        }
    }
    return sub; 
}

//OUTPUT STREAM OPERATOR
ostream& operator<<(ostream& s, const Matrix& mtx){
    for (int r = 0; r < mtx.rows; r++){
        for (int c = 0; c < mtx.cols; c++){
            s << mtx.matrix[index(mtx.rows, mtx.cols,r,c,mtx.rmajor)] << "\t";
        }
        s << endl;
    }
    return s;
}

//V() is valid rows/cols checker
bool Matrix::V(unsigned int r, unsigned int c){
    return (0 <= r && r < rows) && (0 <= c && c < cols);
}
//index() converts row,column to index in array
int index(unsigned int rows, unsigned int cols, unsigned int r, unsigned int c, bool rm){
    return rm ? r*cols+c : c*rows+r;
}

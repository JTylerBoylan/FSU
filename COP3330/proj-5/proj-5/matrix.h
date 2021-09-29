#ifndef _MATRIX_H
#define _MATRIX_H

#include <iostream>

using namespace std;

class Matrix {

    friend ostream& operator<<(ostream& s, const Matrix& mtx);

    private:
        int rows;
        int cols;
        int *matrix;
        bool rmajor;
        bool V(unsigned int r, unsigned int c);
    public:
        Matrix(unsigned int row = 5, unsigned int column = 5, unsigned int value = 0);
        Matrix(const Matrix &mtx);
        ~Matrix();
        Matrix operator=(const Matrix &matrix);
        int numofrows(){ return rows; }
        int numofcols(){ return cols; }
        int get(unsigned int r, unsigned int c);
        bool set(unsigned int r, unsigned int c, unsigned int value);
        bool set(unsigned int i, unsigned int value);
        Matrix operator+(const Matrix &mtx);
        Matrix operator*(const Matrix &mtx);
        void torowmajor();
        void tocolumnmajor();
        bool isrowmajor() { return rmajor; }
        void printinternal();
        Matrix transpose();
        Matrix submatrix(unsigned int startrow, unsigned int endrow, unsigned int startcol, unsigned int endcol);
    };

#endif
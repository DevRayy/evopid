package math;

public class Matrix {

    public Double[][] val;

    public Matrix(Double[][] val) {
        this.val = val;
    }

    public Matrix(int a, int b) {
        this.val = new Double[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                this.val[i][j] = 0.00000;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < this.val.length; i++) {
            for (int j = 0; j < this.val[0].length; j++) {
                this.val[i][j] = 0.00000;
            }
        }
    }

    public static Matrix multiply(Matrix A, Matrix B) {

        int aRows = A.val.length;
        int aColumns = A.val[0].length;
        int bRows = B.val.length;
        int bColumns = B.val[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A.val[i][k] * B.val[k][j];
                }
            }
        }

        return new Matrix(C);
    }

    public static Matrix add(Matrix A, Matrix B) {
        int aRows = A.val.length;
        int aColumns = A.val[0].length;
        int bRows = B.val.length;
        int bColumns = B.val[0].length;

        if (aColumns != bColumns) {
            throw new IllegalArgumentException("A:Columns: " + aColumns + " did not match B:Columns " + bColumns + ".");
        }
        if (aRows != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aRows + " did not match B:Rows " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A.val[i][j] + B.val[i][j];
                }
            }
        }

        return new Matrix(C);
    }

    public String toString() {
        String ret = "{";
        for (int i = 0; i < val.length; i++) {
            ret = ret+"{";
            for (int j = 0; j < val[0].length; j++) {
                ret = ret + " " + val[i][j];
            }
            ret = ret + "}";
        }
        ret=ret+"}";
        return ret.trim();
    }

    public String getDimensions() {
        return this.val.length + "x" + this.val[0].length;
    }
}

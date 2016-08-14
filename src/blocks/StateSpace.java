package blocks;

import blocks.matrix.*;
import math.Matrix;

public class StateSpace extends Block {
    public Matrix A, B, C, D;

    private MatrixMultiply mulA, mulB, mulC, mulD;
    private MatrixSum sum1, sum2;
    private MatrixIntegral mtxIntegral;

    private ValueToMatrix valToMtx;
    private MatrixToValue mtxToVal;

    public StateSpace(Matrix A, Matrix B, Matrix C, Matrix D, Block input) {
        Double[][] init = { {  0.00},
                { 0.00},
                {  1.00},
                {0.00} };
        Matrix initMtx = new Matrix(init);
        this.inputBlock = input;
        valToMtx = new ValueToMatrix(input);
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        mulB = new MatrixMultiply(B, valToMtx);
        mulD = new MatrixMultiply(D, valToMtx);
        sum1 = new MatrixSum(mulB);
        sum2 = new MatrixSum(mulD);
//        mtxIntegral = new MatrixIntegral(initMtx, sum1);
        mtxIntegral = new MatrixIntegral(sum1);
        mulC = new MatrixMultiply(C, mtxIntegral);
        mulA = new MatrixMultiply(A, mtxIntegral);
        sum2.addInputBlock(mulC);
        sum1.addInputBlock(mulA);
        mtxToVal = new MatrixToValue(sum2);
    }

    public void tick(double dt) {
        valToMtx.tick(dt);
        mulD.tick(dt);
        mulB.tick(dt);
        sum1.tick(dt);
        mtxIntegral.tick(dt);
        mulA.tick(dt);
        mulC.tick(dt);
        sum2.tick(dt);
        mtxToVal.tick(dt);
        this.output = mtxToVal.output;
    }
}

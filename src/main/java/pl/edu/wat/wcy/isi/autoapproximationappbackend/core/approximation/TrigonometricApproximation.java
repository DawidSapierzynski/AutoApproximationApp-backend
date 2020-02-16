package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import Jama.Matrix;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class TrigonometricApproximation extends TrigonometricApproximationAbstract {
    public TrigonometricApproximation(List<PointXY> points, int degree) {
        super(points, degree);
    }

    @Override
    protected Matrix getMatrixA(Matrix matrixX, Matrix matrixY) {
        return matrixX.qr().solve(matrixY);
    }

    @Override
    protected Matrix setMatrixBaseFunction(double[] pointsX, int degree) {
        int size = pointsX.length;

        return getEvenMatrix(pointsX, degree, size);
    }

    public static int getTrigonometricDegree(int degree, int size) {
        int maxDegree = (size - 1) / 2;
        if (degree > maxDegree) {
            degree = maxDegree;
        }
        return degree;
    }
}

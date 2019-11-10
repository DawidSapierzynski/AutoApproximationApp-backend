package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import Jama.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class PolynomialApproximation extends Approximation {
    private Logger logger = LoggerFactory.getLogger(PolynomialApproximation.class);

    public PolynomialApproximation(List<PointXY> points, int degree) {
        super(points, degree);
    }

    @Override
    public Polynomial doApproximations() {
        Matrix matrixX, matrixY, matrixA;
        List<PointXY> mapPoints = getPoints();

        matrixX = setMatrixBaseFunction(mapPoints.stream().mapToDouble(PointXY::getX).toArray());
        matrixY = setMatrixY(mapPoints.stream().mapToDouble(PointXY::getY).toArray());

        matrixA = ((matrixX.transpose().times(matrixX)).inverse()).times(matrixX.transpose().times(matrixY));

        setPolynomial(new AlgebraicPolynomial(mapMatrixAToList(matrixA)));

        return getPolynomial();
    }

    private Matrix setMatrixBaseFunction(double[] pointsX) {
        int size = pointsX.length;
        double[][] matrix = new double[size][getDegree() + 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= getDegree(); j++) {
                matrix[i][j] = Polynomial.getValueMonomial(pointsX[i], j);
            }
        }
        return new Matrix(matrix);
    }

    private List<Double> mapMatrixAToList(Matrix matrixA) {
        List<Double> result = new ArrayList<>();
        for (double[] d : matrixA.getArray()) {
            result.add(d[0]);
        }
        return result;
    }

    private Matrix setMatrixY(double[] pointsY) {
        double[][] y = new double[pointsY.length][1];

        for (int i = 0; i < pointsY.length; i++) {
            y[i][0] = pointsY[i];
        }

        return new Matrix(y);
    }

    public double getError() {
        double error = 0.0;

        if (getPolynomial() == null) {
            doApproximations();
        }

        for (PointXY p : getPoints()) {
            error += Math.pow(p.getY() - getPolynomial().evaluate(p.getX()), 2);
        }

        return error;
    }
}

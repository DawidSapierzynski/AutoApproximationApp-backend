package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import Jama.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.TrigonometricPolynomial;

import java.util.ArrayList;
import java.util.List;

public class TrigonometricApproximation extends Approximation {
    private Logger logger = LoggerFactory.getLogger(TrigonometricApproximation.class);

    private LinearDomainMapping linearDomainMapping;

    public TrigonometricApproximation(List<PointXY> points, int degree) {
        super(points, degree);
    }

    @Override
    public Polynomial doApproximations() {
        Matrix matrixX, matrixY, matrixA;
        List<PointXY> mapPoints = getPoints();

        if (checkDomainPoints()) {
            linearDomainMapping = new LinearDomainMapping(getPoints());
            linearDomainMapping.convert();
            mapPoints = linearDomainMapping.getNewPoints();
        }

        matrixX = setMatrixBaseFunction(mapPoints.stream().mapToDouble(PointXY::getX).toArray(), getDegree());
        matrixY = setMatrixY(mapPoints.stream().mapToDouble(PointXY::getY).toArray());

        matrixA = ((matrixX.transpose().times(matrixX)).inverse()).times(matrixX.transpose().times(matrixY));

        setPolynomial(new TrigonometricPolynomial(mapMatrixAToList(matrixA)));

        return getPolynomial();
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

    private Matrix setMatrixBaseFunction(double[] pointsX, int degree) {
        int size = pointsX.length;
        double[][] matrix;

        if (size % 2 == 0) {
            matrix = new double[size][2 * degree];
            for (int i = 0; i < size; i++) {
                double x = pointsX[i];
                matrixInitiation(degree, matrix, i, x);
                matrix[i][2 * degree - 1] = 0.5 * Math.cos(Math.toRadians(degree * x));
            }
        } else {
            matrix = new double[size][2 * degree + 1];
            for (int i = 0; i < size; i++) {
                double x = pointsX[i];
                matrixInitiation(degree, matrix, i, x);
                matrix[i][2 * degree - 1] = Math.cos(Math.toRadians(degree * x));
                matrix[i][2 * degree] = Math.sin(Math.toRadians(degree * x));
            }
        }
        return new Matrix(matrix);
    }

    private void matrixInitiation(int degree, double[][] matrix, int i, double x) {
        matrix[i][0] = 0.5;
        for (int j = 1; j < degree; j++) {
            matrix[i][2 * j - 1] = Math.cos(Math.toRadians(j * x));
            matrix[i][2 * j] = Math.sin(Math.toRadians(j * x));
        }
    }

    private boolean checkDomainPoints() {
        return !(getPoints().get(0).getX() >= 0.0 && getPoints().get(getPoints().size() - 1).getX() <= 2 * Math.PI);
    }

    public LinearDomainMapping getLinearDomainMapping() {
        return linearDomainMapping;
    }
}

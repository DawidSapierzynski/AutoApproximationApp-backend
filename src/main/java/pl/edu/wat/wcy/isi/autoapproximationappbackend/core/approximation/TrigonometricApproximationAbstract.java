package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import Jama.Matrix;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class TrigonometricApproximationAbstract extends Approximation {
    private LinearDomainMapping linearDomainMapping;

    public TrigonometricApproximationAbstract(List<PointXY> points, int degree) {
        super(points, degree);
    }

    public TrigonometricApproximationAbstract(List<PointXY> points) {
        super(points);
    }

    @Override
    public List<PointXY> getApproximationsPoints(int approximationsPointsSize) {
        List<PointXY> approximationsPoints = new ArrayList<>();
        double x0 = getPoints().get(0).getX();
        double xn = getPoints().get(getSize() - 1).getX();
        TrigonometricPolynomial trigonometricPolynomial = (TrigonometricPolynomial) getPolynomial();

        double step = (xn - x0) / approximationsPointsSize;

        for (double i = x0; i <= xn + step; i += step) {
            approximationsPoints.add(new PointXY(i, trigonometricPolynomial.evaluate(i, linearDomainMapping != null ? linearDomainMapping.getLinearFunction() : null)));
        }

        return approximationsPoints;
    }

    @Override
    public double calculateError() {
        TrigonometricPolynomial trigonometricPolynomial = (TrigonometricPolynomial) getPolynomial();

        if (trigonometricPolynomial == null) {
            doApproximations();
        }

        this.absoluteError = getPoints().stream()
                .mapToDouble(p -> Math.pow(p.getY() - Objects.requireNonNull(trigonometricPolynomial).evaluate(p.getX(), linearDomainMapping != null ? linearDomainMapping.getLinearFunction() : null), 2))
                .sum();

        return this.absoluteError;
    }

    protected List<Double> mapMatrixAToList(Matrix matrixA) {
        List<Double> result = new ArrayList<>();
        for (double[] d : matrixA.getArray()) {
            result.add(d[0]);
        }
        return result;
    }

    protected Matrix setMatrixY(double[] pointsY) {
        double[][] y = new double[pointsY.length][1];

        for (int i = 0; i < pointsY.length; i++) {
            y[i][0] = pointsY[i];
        }

        return new Matrix(y);
    }

    protected Matrix setMatrixBaseFunction(double[] pointsX, int degree) {
        int size = pointsX.length;
        Matrix matrix;

        if (size % 2 == 0) {
            matrix = getOddMatrix(pointsX, degree, size);
        } else {
            matrix = getEvenMatrix(pointsX, degree, size);
        }
        return matrix;
    }

    private Matrix getOddMatrix(double[] pointsX, int degree, int size) {
        double[][] matrix;
        matrix = new double[size][2 * degree];
        for (int i = 0; i < size; i++) {
            double x = pointsX[i];
            matrixInitiation(degree, matrix, i, x);
            matrix[i][2 * degree - 1] = 0.5 * Math.cos(degree * x);
        }
        return new Matrix(matrix);
    }

    protected Matrix getEvenMatrix(double[] pointsX, int degree, int size) {
        double[][] matrix;
        matrix = new double[size][2 * degree + 1];
        for (int i = 0; i < size; i++) {
            double x = pointsX[i];
            matrixInitiation(degree, matrix, i, x);
            matrix[i][2 * degree - 1] = Math.cos(degree * x);
            matrix[i][2 * degree] = Math.sin(degree * x);
        }
        return new Matrix(matrix);
    }

    protected void matrixInitiation(int degree, double[][] matrix, int i, double x) {
        matrix[i][0] = 0.5;
        for (int j = 1; j < degree; j++) {
            matrix[i][2 * j - 1] = Math.cos((j * x));
            matrix[i][2 * j] = Math.sin((j * x));
        }
    }

    protected boolean checkDomainPoints() {
        return !(getPoints().get(0).getX() >= 0.0 && getPoints().get(getPoints().size() - 1).getX() <= 2 * Math.PI);
    }

    protected List<PointXY> mapPoints() {
        if (checkDomainPoints()) {
            setLinearDomainMapping(new LinearDomainMapping(getPoints()));
            LinearDomainMapping linearDomainMapping = getLinearDomainMapping();
            linearDomainMapping.convert();
            return linearDomainMapping.getNewPoints();
        }

        return getPoints();
    }

    public LinearDomainMapping getLinearDomainMapping() {
        return linearDomainMapping;
    }

    public void setLinearDomainMapping(LinearDomainMapping linearDomainMapping) {
        this.linearDomainMapping = linearDomainMapping;
    }
}

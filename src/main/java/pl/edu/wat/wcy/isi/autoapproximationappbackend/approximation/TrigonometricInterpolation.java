package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import Jama.Matrix;
import Jama.QRDecomposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomials.TrigonometricPolynomial;

import java.util.ArrayList;
import java.util.List;

public class TrigonometricInterpolation extends Approximation {
    private Logger logger = LoggerFactory.getLogger(TrigonometricInterpolation.class);

    public TrigonometricInterpolation(List<PointXY> points) {
        super(points);
        setDegree(chooseTrigonometricDegree(points.size()));
    }

    @Override
    public List<MathematicalFunction> doApproximations() {
        Matrix matrixX, matrixY, matrixA;
        List<PointXY> mapPoints = getPoints();
        TrigonometricPolynomial trigonometricPolynomial;
        List<Double> c = new ArrayList<>();
        int size;

        if (checkDomainPoints()) {
            setLinearDomainMapping(new LinearDomainMapping(mapPoints));
            LinearDomainMapping linearDomainMapping = getLinearDomainMapping();
            linearDomainMapping.convert();
            mapPoints = linearDomainMapping.getNewPoints();
        }

        size = mapPoints.size();

        for (int i = 0; i < getDegree(); i++) {
            c.add(getAi(i, size, mapPoints));
            c.add(getBi(i, size, mapPoints));
        }

        if (getDegree() % 2 == 0) {
            c.add(getAi(getDegree(), size, mapPoints));
        } else {
            c.add(getAi(getDegree(), size, mapPoints));
            c.add(getBi(getDegree(), size, mapPoints));
        }

//        matrixX = setMatrixBaseFunction(mapPoints.stream().mapToDouble(PointXY::getX).toArray(), getDegree());
//        matrixY = setMatrixY(mapPoints.stream().mapToDouble(PointXY::getY).toArray());
//
//        QRDecomposition qrDecomposition = new QRDecomposition(matrixX);
//        matrixA = qrDecomposition.solve(matrixY);

//        LUDecomposition luDecomposition = new LUDecomposition(matrixX);
//        matrixA = luDecomposition.solve(matrixY);

        trigonometricPolynomial = new TrigonometricPolynomial(c);
//        trigonometricPolynomial = new TrigonometricPolynomial(mapMatrixAToList(matrixA));
        setMathematicalFunctions(List.of(new MathematicalFunction(trigonometricPolynomial, new DomainFunction(false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false))));

        logger.info("{}", trigonometricPolynomial);

        return getMathematicalFunctions();
    }

    private Double getAi(int i, int size, List<PointXY> pointXYs) {
        double d = pointXYs.stream().mapToDouble(p -> p.getY() * Math.cos(i * p.getX())).sum();
        return 2 * d / size;
    }

    private Double getBi(int i, int size, List<PointXY> pointXYs) {
        double d = pointXYs.stream().mapToDouble(p -> p.getY() * Math.cos(i * p.getX())).sum();
        return 2 * d / size;
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

    public static int chooseTrigonometricDegree(int size) {
        if (size % 2 == 0) {
            return size / 2;
        } else {
            return (size - 1) / 2;
        }
    }

    private Matrix setMatrixBaseFunction(double[] pointsX, int degree) {
        int size = pointsX.length;
        double[][] matrix = new double[size][size];

        if (size % 2 == 0) {
            for (int i = 0; i < size; i++) {
                double x = pointsX[i];
                matrixInitiation(degree, matrix, i, x);
                matrix[i][2 * degree - 1] = 0.5 * Math.cos(Math.toRadians(degree * x));
            }
        } else {
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
}

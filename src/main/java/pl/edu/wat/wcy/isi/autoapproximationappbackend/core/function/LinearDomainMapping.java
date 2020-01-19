package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function;

import Jama.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

public class LinearDomainMapping {
    private static final Logger logger = LoggerFactory.getLogger(LinearDomainMapping.class);

    private final List<PointXY> oldPoints;
    private List<PointXY> newPoints;
    private AlgebraicPolynomial linearFunction;

    public LinearDomainMapping(List<PointXY> oldPoints) {
        this.oldPoints = oldPoints;
    }

    public void convert() {
        double x0 = oldPoints.get(0).getX();
        double xn = oldPoints.get(oldPoints.size() - 1).getX();

        this.linearFunction = findLinearFunction(x0, xn);
        newPoints = new ArrayList<>();

        for (PointXY p : oldPoints) {
            newPoints.add(new PointXY(linearFunction.evaluate(p.getX()), p.getY()));
        }
    }

    public static AlgebraicPolynomial findLinearFunction(double x0, double xn) {
        AlgebraicPolynomial linearFunction;
        double[][] x, y;
        Matrix matrixX, matrixY, matrixA;

        x = new double[][]{{1, x0}, {1, xn}};
        y = new double[][]{{0}, {6.28}};

        matrixX = new Matrix(x);
        logger.debug("Matrix X:\n{}", matrixX);

        matrixY = new Matrix(y);
        logger.debug("Matrix Y:\n{}", matrixY);

        matrixA = (((matrixX.transpose().times(matrixX)).inverse()).times(matrixX.transpose())).times(matrixY);
        logger.debug("Matrix A:\n{}", matrixA);

        linearFunction = new AlgebraicPolynomial((matrixA.transpose().getArray())[0]);
        logger.debug("Linear function: {}", linearFunction);

        return linearFunction;
    }

    public List<PointXY> getOldPoints() {
        return oldPoints;
    }

    public List<PointXY> getNewPoints() {
        return newPoints;
    }

    public AlgebraicPolynomial getLinearFunction() {
        return linearFunction;
    }
}

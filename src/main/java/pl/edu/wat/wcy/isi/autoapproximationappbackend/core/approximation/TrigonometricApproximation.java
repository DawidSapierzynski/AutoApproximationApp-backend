package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import Jama.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.Arrays;
import java.util.List;

public class TrigonometricApproximation extends TrigonometricApproximationAbstract {
    private final Logger logger = LoggerFactory.getLogger(TrigonometricApproximation.class);

    public TrigonometricApproximation(List<PointXY> points, int degree) {
        super(points, degree);
    }

    @Override
    public List<MathematicalFunction> doApproximations() {
        Matrix matrixX, matrixY, matrixA;
        TrigonometricPolynomial trigonometricPolynomial;
        List<PointXY> mapPoints = mapPoints();

        matrixX = setMatrixBaseFunction(mapPoints.stream().mapToDouble(PointXY::getX).toArray(), getDegree());
        logger.debug("Matrix X:\n {}", Arrays.toString(matrixX.getArray()));

        matrixY = setMatrixY(mapPoints.stream().mapToDouble(PointXY::getY).toArray());
        logger.debug("Matrix Y:\n {}", Arrays.toString(matrixY.getArray()));

        matrixA = matrixX.qr().solve(matrixY);
        logger.debug("Matrix A:\n {}", Arrays.toString(matrixA.getArray()));

        trigonometricPolynomial = new TrigonometricPolynomial(mapMatrixAToList(matrixA));

        setMathematicalFunctions(List.of(new MathematicalFunction(trigonometricPolynomial, new DomainFunction(false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false))));

        logger.info("Absolute error Trigonometric Approximation = {}", calculateError());

        return getMathematicalFunctions();
    }

    @Override
    protected Matrix setMatrixBaseFunction(double[] pointsX, int degree) {
        int size = pointsX.length;

        return getEvenMatrix(pointsX, degree, size);
    }
}

package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class NewtonInterpolation extends Approximation {
    private final Logger logger = LoggerFactory.getLogger(NewtonInterpolation.class);

    private List<Double> interpolationsCoefficients;

    public NewtonInterpolation(List<PointXY> points) {
        super(points);
    }

    @Override
    public List<MathematicalFunction> doApproximations() {
        Double[][] matrixDifferenceQuotients = new Double[getSize()][getSize()];

        createMatrixDifferenceQuotients(matrixDifferenceQuotients, getPoints());
        interpolationsCoefficients = List.of(matrixDifferenceQuotients[0]);
        setMathematicalFunctions(List.of(new MathematicalFunction(getAlgebraicPolynomialForm(), new DomainFunction(false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false))));

        return getMathematicalFunctions();
    }

    private void createMatrixDifferenceQuotients(Double[][] matrixDifferenceQuotients, List<PointXY> points) throws ArithmeticException {
        initiationMatrixDifferenceQuotients(matrixDifferenceQuotients, points);
        int size = points.size();
        double differenceX;
        PointXY pointF, pointL;

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                pointF = points.get(j);
                pointL = points.get(j + i);
                differenceX = pointL.getX() - pointF.getX();

                if (differenceX == 0.0) {
                    logger.error("The difference in the X coordinates of points (denominator) equals 0.");
                    throw new ArithmeticException("Denominator is 0.");
                } else {
                    matrixDifferenceQuotients[j][i] = (matrixDifferenceQuotients[j + 1][i - 1] - matrixDifferenceQuotients[j][i - 1]) / differenceX;
                }
            }
        }
    }

    private void initiationMatrixDifferenceQuotients(Double[][] matrixDifferenceQuotients, List<PointXY> points) {
        for (int i = 0; i < points.size(); i++) {
            matrixDifferenceQuotients[i][0] = points.get(i).getY();
        }
    }

    public AlgebraicPolynomial getAlgebraicPolynomialForm() {
        AlgebraicPolynomial result = new AlgebraicPolynomial(List.of(0.0));
        AlgebraicPolynomial baseFunctionI = new AlgebraicPolynomial(List.of(1.0));
        for (int i = 0; i < getPoints().size(); i++) {
            baseFunctionI = getNextBaseFunctionI(baseFunctionI, i);
            result = result.plus(baseFunctionI.times(interpolationsCoefficients.get(i)));
        }
        return result;
    }

    private AlgebraicPolynomial getNextBaseFunctionI(AlgebraicPolynomial baseFunctionI, int i) {
        if (i > 1) {
            return baseFunctionI.times(new AlgebraicPolynomial(List.of(-getPoints().get(i - 1).getX(), 1.0)));
        } else if (i == 1) {
            return new AlgebraicPolynomial(List.of(-getPoints().get(i - 1).getX(), 1.0));
        } else {
            return new AlgebraicPolynomial(List.of(1.0));
        }
    }

    public List<Double> getInterpolationsCoefficients() {
        return interpolationsCoefficients;
    }
}

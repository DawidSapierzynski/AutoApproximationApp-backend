package pl.edu.wat.wcy.isi.autoapproximationappbackend.function;

import Jama.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

public class LinearDomainMapping {
    private Logger logger = LoggerFactory.getLogger(LinearDomainMapping.class);

    private List<PointXY> oldPoints;
    private List<PointXY> newPoints;
    private AlgebraicPolynomial linearFunction;

    public LinearDomainMapping(List<PointXY> oldPoints) {
        this.oldPoints = oldPoints;
    }

    public void convert() {
        findLinearFunction();
        newPoints = new ArrayList<>();

        for (PointXY p : oldPoints) {
            newPoints.add(new PointXY(linearFunction.evaluate(p.getX()), p.getY()));
        }
    }

    private void findLinearFunction() {
        double x0, xn;
        double[][] x, y, a;
        Matrix matrixX, matrixY, matrixA;

        x0 = oldPoints.get(0).getX();
        xn = oldPoints.get(oldPoints.size() - 1).getX();
        x = new double[][]{{1, x0}, {1, xn}};
        y = new double[][]{{0}, {2 * Math.PI}};

        matrixX = new Matrix(x);
        matrixY = new Matrix(y);

        matrixA = (((matrixX.transpose().times(matrixX)).inverse()).times(matrixX.transpose())).times(matrixY);

        linearFunction = new AlgebraicPolynomial((matrixA.transpose().getArray())[0]);
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

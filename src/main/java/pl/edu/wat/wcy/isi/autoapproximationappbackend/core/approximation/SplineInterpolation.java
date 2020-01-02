package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SplineInterpolation extends Approximation {
    private double[] z;

    public SplineInterpolation(List<PointXY> points) {
        super(points, 3);
    }

    @Override
    public List<MathematicalFunction> doApproximations() {
        double[] h = new double[getSize() - 1];
        double[] u = new double[getSize() - 1];
        double[] b = new double[getSize() - 1];
        double[] v = new double[getSize() - 1];

        setHiBi(h, b);
        setUiVi(h, u, b, v);

        this.z = calculateZ(v, h, u);

        setMathematicalFunctions(calculateFunctions(h));

        return getMathematicalFunctions();
    }

    private List<MathematicalFunction> calculateFunctions(double[] h) {
        List<MathematicalFunction> f = new ArrayList<>();
        AlgebraicPolynomial algebraicPolynomial, p, p1;

        for (int i = 0; i < getSize() - 1; i++) {
            p = AlgebraicPolynomial.getLinearFunction(1, -getPoints().get(i).getX());
            p1 = AlgebraicPolynomial.getLinearFunction(-1, getPoints().get(i + 1).getX());

            algebraicPolynomial = AlgebraicPolynomial.pow(p1.copy(), 3).times(c1(i, h))
                    .plus(AlgebraicPolynomial.pow(p.copy(), 3).times(c2(i, h)), AlgebraicPolynomial.class)
                    .plus(p.copy().times(c3(i, h)), AlgebraicPolynomial.class)
                    .plus(p1.copy().times(c4(i, h)), AlgebraicPolynomial.class);
            f.add(new MathematicalFunction(algebraicPolynomial, new DomainFunction(true, getPoints().get(i).getX(), getPoints().get(i + 1).getX(), false)));
        }

        return f;
    }

    private double c4(int i, double[] h) {
        double y = getPoints().get(i).getY();
        return (y / h[i]) - ((z[i] * h[i]) / 6);
    }

    private double c3(int i, double[] h) {
        double y1 = getPoints().get(i + 1).getY();
        return (y1 / h[i]) - ((z[i + 1] * h[i]) / 6);
    }

    private double c2(int i, double[] h) {
        return z[i + 1] / (6 * h[i]);
    }

    private double c1(int i, double[] h) {
        return z[i] / (6 * h[i]);
    }

    private double[] calculateZ(double[] v, double[] h, double[] u) {
        double[] z = new double[getSize()];
        z[getSize() - 1] = 0;

        for (int i = getSize() - 2; i > 0; i--) {
            z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
        }

        z[0] = 0;
        return z;
    }

    private void setUiVi(double[] h, double[] u, double[] b, double[] v) {
        u[0] = 0.0;
        v[0] = 0.0;
        u[1] = 2 * (h[0] + h[1]);
        v[1] = b[1] - b[0];

        for (int i = 2; i < getSize() - 1; i++) {
            u[i] = 2 * (h[i - 1] + h[i]) - h[i - 1] * h[i - 1] / u[i - 1];
            v[i] = b[i] - b[i - 1] - h[i - 1] * v[i - 1] / u[i - 1];
        }
    }

    private void setHiBi(double[] hi, double[] bi) {
        for (int i = 0; i < getSize() - 1; i++) {
            hi[i] = getPoints().get(i + 1).getX() - getPoints().get(i).getX();
            bi[i] = 6 * (getPoints().get(i + 1).getY() - getPoints().get(i).getY()) / hi[i];
        }
    }

    public double[] getZ() {
        return z;
    }

    @Override
    public List<PointXY> getApproximationsPoints(int approximationsPointsSize) {
        List<PointXY> approximationsPoints = new ArrayList<>();
        MathematicalFunction mathematicalFunction = null;
        double x0, x1, step;

        for (int i = 0; i < getMathematicalFunctions().size(); i++) {
            mathematicalFunction = getMathematicalFunctions().get(i);
            x0 = getPoints().get(i).getX();
            x1 = getPoints().get(i + 1).getX();

            step = (x1 - x0) / 20;

            for (double j = x0; j < x1; j += step) {
                approximationsPoints.add(new PointXY(j, mathematicalFunction.getPolynomial().evaluate(j)));
            }
        }
        approximationsPoints.add(new PointXY(getPoints().get(getSize() - 1).getX(), Objects.requireNonNull(mathematicalFunction).getPolynomial().evaluate(getPoints().get(getSize() - 1).getX())));

        return approximationsPoints;
    }

    @Override
    public double calculateError() {
        throw new NullPointerException();
    }

    @Override
    public Polynomial getPolynomial() {
        throw new NullPointerException();
    }
}

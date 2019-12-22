package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

public abstract class Approximation {
    private List<MathematicalFunction> mathematicalFunctions;
    private final List<PointXY> points;
    private LinearDomainMapping linearDomainMapping;
    private int degree;
    private final int size;

    public Approximation(List<PointXY> points, int degree) {
        this.points = points;
        this.size = points.size();
        this.degree = degree;
    }

    public Approximation(List<PointXY> points) {
        this.points = points;
        this.size = points.size();
        this.degree = points.size() - 1;
    }

    public abstract List<MathematicalFunction> doApproximations();

    public List<PointXY> getApproximationsPoints(int approximationsPointsSize) {
        List<PointXY> approximationsPoints = new ArrayList<>();
        double x0 = points.get(0).getX();
        double xn = points.get(size - 1).getX();

        double step = (xn - x0) / approximationsPointsSize;

        for (double i = x0; i <= xn + step; i += step) {
            approximationsPoints.add(new PointXY(i, getPolynomial().evaluate(i, linearDomainMapping)));
        }

        return approximationsPoints;
    }

    public double getError() {
        double error;

        if (getPolynomial() == null) {
            doApproximations();
        }

        error = getPoints().stream()
                .mapToDouble(p -> Math.pow(p.getY() - getPolynomial().evaluate(p.getX(), linearDomainMapping), 2))
                .sum();

        return error;
    }

    public Polynomial getPolynomial() {
        return mathematicalFunctions.get(0).getPolynomial();
    }

    public List<PointXY> getPoints() {
        return points;
    }

    public int getSize() {
        return size;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public List<MathematicalFunction> getMathematicalFunctions() {
        return mathematicalFunctions;
    }

    public void setMathematicalFunctions(List<MathematicalFunction> mathematicalFunctions) {
        this.mathematicalFunctions = mathematicalFunctions;
    }

    public LinearDomainMapping getLinearDomainMapping() {
        return linearDomainMapping;
    }

    public void setLinearDomainMapping(LinearDomainMapping linearDomainMapping) {
        this.linearDomainMapping = linearDomainMapping;
    }
}

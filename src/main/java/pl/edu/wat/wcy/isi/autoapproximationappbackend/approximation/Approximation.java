package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

public abstract class Approximation {
    private List<MathematicalFunction> mathematicalFunctions;
    private List<PointXY> points;
    private LinearDomainMapping linearDomainMapping;
    private int degree;
    private int size;

    public Approximation(List<PointXY> points, int degree) {
        this.points = points;
        this.size = points.size();
        this.degree = degree;
    }

    public Approximation(List<PointXY> points) {
        this.points = points;
        this.size = points.size();
        this.degree = this.size - 1;
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
        double error = 0.0;

        if (getPolynomial() == null) {
            doApproximations();
        }

        for (PointXY p : getPoints()) {
            error += Math.pow(p.getY() - getPolynomial().evaluate(p.getX(), linearDomainMapping), 2);
        }

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

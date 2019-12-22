package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

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
            approximationsPoints.add(new PointXY(i, trigonometricPolynomial.evaluate(i, linearDomainMapping.getLinearFunction())));
        }

        return approximationsPoints;
    }

    @Override
    public double getError() {
        double error;
        TrigonometricPolynomial trigonometricPolynomial = (TrigonometricPolynomial) getPolynomial();

        if (trigonometricPolynomial == null) {
            doApproximations();
        }

        error = getPoints().stream()
                .mapToDouble(p -> Math.pow(p.getY() - Objects.requireNonNull(trigonometricPolynomial).evaluate(p.getX(), linearDomainMapping.getLinearFunction()), 2))
                .sum();

        return error;
    }

    public LinearDomainMapping getLinearDomainMapping() {
        return linearDomainMapping;
    }

    public void setLinearDomainMapping(LinearDomainMapping linearDomainMapping) {
        this.linearDomainMapping = linearDomainMapping;
    }
}

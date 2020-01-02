package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

public class TrigonometricApproximation extends TrigonometricApproximationAbstract {
    private final Logger logger = LoggerFactory.getLogger(TrigonometricApproximation.class);

    public TrigonometricApproximation(List<PointXY> points, int degree) {
        super(points, degree);
    }

    @Override
    public List<MathematicalFunction> doApproximations() {
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

        c.add(getAi(0, size, mapPoints));
        for (int i = 1; i < getDegree(); i++) {
            c.add(getAi(i, size, mapPoints));
            c.add(getBi(i, size, mapPoints));
        }

        if (getDegree() % 2 == 0) {
            c.add(getAi(getDegree(), size, mapPoints));
        } else {
            c.add(getAi(getDegree(), size, mapPoints));
            c.add(getBi(getDegree(), size, mapPoints));
        }

        trigonometricPolynomial = new TrigonometricPolynomial(c);

        setMathematicalFunctions(List.of(new MathematicalFunction(trigonometricPolynomial, new DomainFunction(false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false))));

        logger.info("Absolute error Trigonometric Approximation = {}", calculateError());

        return getMathematicalFunctions();
    }

    private Double getAi(int i, int size, List<PointXY> pointXYs) {
        double d = pointXYs.stream()
                .mapToDouble(p -> p.getY() * Math.cos(i * p.getX()))
                .sum();
        return 2 * d / size;
    }

    private Double getBi(int i, int size, List<PointXY> pointXYs) {
        double d = pointXYs.stream()
                .mapToDouble(p -> p.getY() * Math.sin(i * p.getX()))
                .sum();
        return 2 * d / size;
    }

    private boolean checkDomainPoints() {
        return !(getPoints().get(0).getX() >= 0.0 && getPoints().get(getPoints().size() - 1).getX() <= 2 * Math.PI);
    }

}

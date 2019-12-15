package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.interpolation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrigonometricInterpolationTest {
    private Logger logger = LoggerFactory.getLogger(TrigonometricInterpolationTest.class);
    private static final double DELTA = 4e-12;
    private static final int SIZE = 500;
    private static final double ADD = 0.125;

    private List<PointXY> evenPoints = new ArrayList<>();
    private List<PointXY> oddPoints = new ArrayList<>();
    private TrigonometricInterpolation evenTrigonometricInterpolation;
    private TrigonometricInterpolation oddTrigonometricInterpolation;

    double[] xs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2};
    double[] ys = {1, 3, -2, -1};

    @BeforeEach
    void setUp() {
        double x;

        for (int i = 0; i < 4; i++) {
            evenPoints.add(new PointXY(xs[i], ys[i]));
        }

        evenTrigonometricInterpolation = new TrigonometricInterpolation(evenPoints);

        x = 0.0;
        for (int i = 0; i <= SIZE; i++) {
            x += ADD;
            oddPoints.add(new PointXY(x, getFunctionValue(x)));
        }

        oddTrigonometricInterpolation = new TrigonometricInterpolation(oddPoints);
    }

    @AfterEach
    void tearDown() {
        evenPoints = null;
        oddPoints = null;
        evenTrigonometricInterpolation = null;
        oddTrigonometricInterpolation = null;
    }

    @Test
    void doInterpolationsEven() {
        Polynomial resultPolynomial;
        LinearDomainMapping linearDomainMapping;
        double r, cr;

        resultPolynomial = evenTrigonometricInterpolation.doApproximations().get(0).getPolynomial();
        linearDomainMapping = evenTrigonometricInterpolation.getLinearDomainMapping();

        for (PointXY p : evenPoints) {
            cr = p.getY();
            r = resultPolynomial.evaluate(p.getX(), linearDomainMapping);

            assertEquals(cr, r, DELTA);
        }

    }

    @Test
    void doInterpolationsOddSize() {
        Polynomial resultPolynomial;
        LinearDomainMapping linearDomainMapping;
        double r, cr;

        resultPolynomial = oddTrigonometricInterpolation.doApproximations().get(0).getPolynomial();
        linearDomainMapping = oddTrigonometricInterpolation.getLinearDomainMapping();

        for (PointXY p : oddPoints) {
            cr = p.getY();
            r = resultPolynomial.evaluate(p.getX(), linearDomainMapping);

            assertEquals(cr, r, DELTA);
        }

        double x = 5.253256;
        assertEquals(getFunctionValue(x), resultPolynomial.evaluate(x, linearDomainMapping), DELTA);
    }

    private double getFunctionValue(double x) {
        // 4.82*sin(x) + 1.36*cos(x+1)
        return 4.82 * Math.sin(Math.toRadians(x)) + 1.36 * Math.cos(Math.toRadians(x + 1));
    }
}
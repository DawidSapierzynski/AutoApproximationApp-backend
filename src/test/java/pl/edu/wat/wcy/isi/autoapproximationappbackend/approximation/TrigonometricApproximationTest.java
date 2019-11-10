package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.TrigonometricPolynomial;

import java.util.ArrayList;
import java.util.List;

class TrigonometricApproximationTest {
    private Logger logger = LoggerFactory.getLogger(TrigonometricApproximationTest.class);

    private List<PointXY> points = new ArrayList<>();
    private TrigonometricApproximation trigonometricInterpolation;
    private TrigonometricPolynomial function;

    @BeforeEach
    void setUp() {
        double[] xs = {0.03, 0.21, 1.34, 1.54, 2.3, 2.92, 3.36, 3.86, 4.29, 4.78, 5.1, 5.23, 5.54, 6.2};
        function = new TrigonometricPolynomial(List.of(4.82, 1.36, 3.2, 8.5, 10d, 1d, 2.2, 1.24));

        for (double x : xs) {
            points.add(new PointXY(x, getFunctionValue(x)));
        }
        trigonometricInterpolation = new TrigonometricApproximation(points, 4);
    }

    @AfterEach
    void tearDown() {
        points = null;
        trigonometricInterpolation = null;
    }

    @Test
    void doApproximations() {
        Polynomial resultPolynomial;
        LinearDomainMapping linearDomainMapping;
        double r, cr;

        resultPolynomial = trigonometricInterpolation.doApproximations();
        linearDomainMapping = trigonometricInterpolation.getLinearDomainMapping();

        logger.info("Result: {}", resultPolynomial);
        logger.info("Function: {}", function);

        for (PointXY p : points) {
            cr = p.getY();
            r = resultPolynomial.evaluate(p.getX(), linearDomainMapping);

            logger.info("Y={} ; YPolynomial={} ; d={}", cr, r, Math.abs(cr - r));
        }
    }

    private double getFunctionValue(double x) {
        return function.evaluate(x);
    }
}
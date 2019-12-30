package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.interpolation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.LinearDomainMapping;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrigonometricInterpolationTest {
    private static final double DELTA = 4e-12;

    private List<PointXY> evenPoints = new ArrayList<>();
    private TrigonometricInterpolation evenTrigonometricInterpolation;

    final double[] xs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2};
    final double[] ys = {1, 3, -2, -1};

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 4; i++) {
            evenPoints.add(new PointXY(xs[i], ys[i]));
        }

        evenTrigonometricInterpolation = new TrigonometricInterpolation(evenPoints);
    }

    @AfterEach
    void tearDown() {
        evenPoints = null;
        evenTrigonometricInterpolation = null;
    }

    @Test
    void doInterpolationsEven() {
        TrigonometricPolynomial resultPolynomial;
        LinearDomainMapping linearDomainMapping;
        double r, cr;

        resultPolynomial = (TrigonometricPolynomial) evenTrigonometricInterpolation.doApproximations().get(0).getPolynomial();
        linearDomainMapping = evenTrigonometricInterpolation.getLinearDomainMapping();

        for (PointXY p : evenPoints) {
            cr = p.getY();
            r = resultPolynomial.evaluate(p.getX(), linearDomainMapping!=null ? linearDomainMapping.getLinearFunction() : null);

            assertEquals(cr, r, DELTA);
        }
    }
}
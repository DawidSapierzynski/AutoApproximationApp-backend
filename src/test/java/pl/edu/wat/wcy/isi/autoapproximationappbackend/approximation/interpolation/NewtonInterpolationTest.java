package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.interpolation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.NewtonInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomials.Polynomial;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewtonInterpolationTest {
    private static final double DELTA = 1e-14;

    private List<PointXY> points = new ArrayList<>();
    private NewtonInterpolation interpolation;

    @BeforeEach
    void setUp() {
        points.add(new PointXY(8.0, 3.0));
        points.add(new PointXY(17.0, 21.0));
        points.add(new PointXY(28.0, 10.0));
        interpolation = new NewtonInterpolation(points);
    }

    @AfterEach
    void tearDown() {
        points = null;
        interpolation = null;
    }

    @Test
    void newtonInterpolationFormula() {
        Polynomial resultPolynomial, correctResultPolynomial;
        List<Double> resultCoefficients, correctResultCoefficients;
        correctResultPolynomial = new AlgebraicPolynomial(List.of(-33.4, 5.75, -0.15));
        correctResultCoefficients = List.of(3.0, 2.0, -0.15);

        resultPolynomial = interpolation.doApproximations().get(0).getPolynomial();
        resultCoefficients = interpolation.getInterpolationsCoefficients();

        assertEquals(correctResultPolynomial, resultPolynomial);
        assertEquals(correctResultCoefficients, resultCoefficients);
    }

    @Test
    void newtonInterpolationFormulaValue() {
        Polynomial resultPolynomial;
        double r, cr;

        resultPolynomial = interpolation.doApproximations().get(0).getPolynomial();
        for (PointXY p : points) {
            cr = p.getY();
            r = resultPolynomial.evaluate(p.getX());

            assertEquals(cr, r, DELTA);
        }
    }

    @Test
    void getAlgebraicPolynomialForm() {
        AlgebraicPolynomial correctResult = new AlgebraicPolynomial(List.of(-33.4, 5.75, -0.15));
        AlgebraicPolynomial result;

        interpolation.doApproximations();
        result = interpolation.getAlgebraicPolynomialForm();

        assertEquals(correctResult, result);
    }
}
package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.interpolation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.SplineInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.MathematicalFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomials.AlgebraicPolynomial;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SplineInterpolationTest {
    private static final double DELTA = 1e-14;

    private SplineInterpolation splineInterpolation;
    private List<PointXY> points;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
        points.add(new PointXY(1, 1));
        points.add(new PointXY(2, 3));
        points.add(new PointXY(3, 2));
        points.add(new PointXY(4, 4));
        points.add(new PointXY(5, 1));

        splineInterpolation = new SplineInterpolation(points);
    }

    @AfterEach
    void tearDown() {
        points = null;
    }

    @Test
    void doApproximations() {
        double[] correctZ = new double[]{0, -93.0 / 14.0, 60.0 / 7.0, -135.0 / 14.0, 0};
        double[] resultZ;
        List<AlgebraicPolynomial> correctPolynomial = List.of(
                new AlgebraicPolynomial(List.of(-0.9999999999999998, -0.2142857142857142, 3.321428571428571, -1.107142857142857)),
                new AlgebraicPolynomial(List.of(-13d, 18.5, -6.75, 0.75)));
        List<MathematicalFunction> resultMathematicalFunctions;

        resultMathematicalFunctions = splineInterpolation.doApproximations();
        resultZ = splineInterpolation.getZ();

        assertArrayEquals(correctZ, resultZ, DELTA);

        for (int i = 0; i < resultMathematicalFunctions.size(); i++) {
            assertEquals(correctPolynomial.get(i), resultMathematicalFunctions.get(i).getPolynomial());
        }
    }
}
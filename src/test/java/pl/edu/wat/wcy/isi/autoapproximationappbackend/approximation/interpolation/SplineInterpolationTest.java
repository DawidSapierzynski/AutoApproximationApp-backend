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
                new AlgebraicPolynomial(List.of(-1d, -0.2142857142857144, 3.321428571428571, -1.107142857142857)),
                new AlgebraicPolynomial(List.of(-30.142857142857146, 43.49999999999999, -18.535714285714285, 2.5357142857142856)),
                new AlgebraicPolynomial(List.of(120.28571428571428, -106.92857142857142, 31.607142857142854, -3.0357142857142856)),
                new AlgebraicPolynomial(List.of(-176.85714285714283, 115.92857142857142, -24.107142857142854, 1.607142857142857)));
        List<MathematicalFunction> resultMathematicalFunctions;

        resultMathematicalFunctions = splineInterpolation.doApproximations();
        resultZ = splineInterpolation.getZ();

        assertArrayEquals(correctZ, resultZ, DELTA);

        for (int i = 0; i < resultMathematicalFunctions.size(); i++) {
            assertEquals(correctPolynomial.get(i), resultMathematicalFunctions.get(i).getPolynomial());
        }
    }
}
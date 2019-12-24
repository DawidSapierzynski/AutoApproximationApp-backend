package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ApproximationDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.MathematicalFunctionMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class ApproximationCalculate implements Runnable {
    private static final int NUMBER_SAMPLES = 20;

    private final ChosenMethodDTO chosenMethodDTO;
    private final List<PointXY> points;
    private final ApproximationDTO approximationDTO;
    private final MathematicalFunctionMapper mathematicalFunctionMapper;

    public ApproximationCalculate(ChosenMethodDTO chosenMethodDTO, List<PointXY> points, ApproximationDTO approximationDTO, MathematicalFunctionMapper mathematicalFunctionMapper) {
        this.chosenMethodDTO = chosenMethodDTO;
        this.points = points;
        this.approximationDTO = approximationDTO;
        this.mathematicalFunctionMapper = mathematicalFunctionMapper;
    }

    @Override
    public void run() {
        Approximation approximation = null;
        int degree = chosenMethodDTO.getDegree();

        switch (chosenMethodDTO.getMethod()) {
            case SPLINEINTERPOLATION:
                approximation = new SplineInterpolation(points);
                break;
            case NEWTONINTERPOLATION:
                approximation = new NewtonInterpolation(points);
                break;
            case TRIGONOMETRICINTERPOLATION:
                approximation = new TrigonometricInterpolation(points);
                break;
            case POLYNOMIALAPPROXIMATION:
                approximation = new PolynomialApproximation(points, degree);
                break;
            case TRIGONOMETRICAPPROXIMATION:
                approximation = new TrigonometricApproximation(points, degree);
                break;
        }

        approximationDTO.setMathematicalFunctionDTOs(mathematicalFunctionMapper.mapToMathematicalFunctionDTOs(approximation.doApproximations()));
        approximationDTO.setPoints(approximation.getApproximationsPoints(NUMBER_SAMPLES * points.size()));
    }
}

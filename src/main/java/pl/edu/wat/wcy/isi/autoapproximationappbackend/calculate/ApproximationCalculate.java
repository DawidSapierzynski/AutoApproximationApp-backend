package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ApproximationDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.MathematicalFunctionMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class ApproximationCalculate implements Runnable {
    private ChosenMethodDTO chosenMethodDTO;
    private List<PointXY> points;
    private ApproximationDTO approximationDTO;
    private MathematicalFunctionMapper mathematicalFunctionMapper;

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
        approximationDTO.setPoints(approximation.getApproximationsPoints(20 * points.size()));
    }
}

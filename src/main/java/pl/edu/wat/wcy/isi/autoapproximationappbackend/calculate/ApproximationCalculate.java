package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.Approximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.NewtonInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.SplineInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ApproximationDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class ApproximationCalculate implements Runnable {
    private ChosenMethodDTO chosenMethodDTO;
    private List<PointXY> points;
    private ApproximationDTO approximationDTO;

    public ApproximationCalculate(ChosenMethodDTO chosenMethodDTO, List<PointXY> points, ApproximationDTO approximationDTO) {
        this.chosenMethodDTO = chosenMethodDTO;
        this.points = points;
        this.approximationDTO = approximationDTO;
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

        approximationDTO.setMathematicalFunctions(approximation.doApproximations());
        approximationDTO.setPoints(approximation.getApproximationsPoints(20 * points.size()));
    }
}

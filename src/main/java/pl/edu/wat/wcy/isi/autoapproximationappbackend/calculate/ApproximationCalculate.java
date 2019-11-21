package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.Approximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.NewtonInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.SplineInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ApproximationForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ChosenMethod;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

public class ApproximationCalculate implements Runnable {
    private ChosenMethod chosenMethod;
    private List<PointXY> points;
    private ApproximationForm approximationForm;

    public ApproximationCalculate(ChosenMethod chosenMethod, List<PointXY> points, ApproximationForm approximationForm) {
        this.chosenMethod = chosenMethod;
        this.points = points;
        this.approximationForm = approximationForm;
    }

    @Override
    public void run() {
        Approximation approximation = null;
        int degree = chosenMethod.getDegree();

        switch (chosenMethod.getMethod()) {
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

        approximationForm.setMathematicalFunctions(approximation.doApproximations());
        approximationForm.setPoints(approximation.getApproximationsPoints(20 * points.size()));
    }
}

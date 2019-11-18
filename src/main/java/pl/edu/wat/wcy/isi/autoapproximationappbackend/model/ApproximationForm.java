package pl.edu.wat.wcy.isi.autoapproximationappbackend.model;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.MathematicalFunction;

import java.util.List;

public class ApproximationForm {
    private List<MathematicalFunction> mathematicalFunctions;
    private List<PointXY> points;

    public ApproximationForm(List<MathematicalFunction> mathematicalFunctions, List<PointXY> points) {
        this.mathematicalFunctions = mathematicalFunctions;
        this.points = points;
    }

    public ApproximationForm() {
    }

    public List<MathematicalFunction> getMathematicalFunctions() {
        return mathematicalFunctions;
    }

    public void setMathematicalFunctions(List<MathematicalFunction> mathematicalFunctions) {
        this.mathematicalFunctions = mathematicalFunctions;
    }

    public List<PointXY> getPoints() {
        return points;
    }

    public void setPoints(List<PointXY> points) {
        this.points = points;
    }
}

package pl.edu.wat.wcy.isi.autoapproximationappbackend.model;

import java.util.List;

public class ApproximationDataForm {
    private ChosenMethod chosenMethod;
    private List<PointXY> points;

    public ApproximationDataForm(ChosenMethod chosenMethod, List<PointXY> points) {
        this.chosenMethod = chosenMethod;
        this.points = points;
    }

    public ChosenMethod getChosenMethod() {
        return chosenMethod;
    }

    public void setChosenMethod(ChosenMethod chosenMethod) {
        this.chosenMethod = chosenMethod;
    }

    public List<PointXY> getPoints() {
        return points;
    }

    public void setPoints(List<PointXY> points) {
        this.points = points;
    }
}

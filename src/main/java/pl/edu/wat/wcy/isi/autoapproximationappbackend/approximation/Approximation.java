package pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.Polynomial;

import java.util.List;

public abstract class Approximation {
    private Polynomial polynomial;
    private List<PointXY> points;
    private int degree;
    private int size;

    public Approximation(List<PointXY> points, int degree) {
        this.points = points;
        this.size = points.size();
        this.degree = degree;
    }

    public Approximation(List<PointXY> points) {
        this.points = points;
        this.size = points.size();
        this.degree = this.size - 1;
    }

    public abstract Polynomial doApproximations();

    public List<PointXY> getPoints() {
        return points;
    }

    public int getSize() {
        return size;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Polynomial polynomial) {
        this.polynomial = polynomial;
    }
}

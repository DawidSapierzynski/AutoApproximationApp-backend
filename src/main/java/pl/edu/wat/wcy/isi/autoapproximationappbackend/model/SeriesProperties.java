package pl.edu.wat.wcy.isi.autoapproximationappbackend.model;

import java.util.List;

public class SeriesProperties {
    private int size;
    private double fastVariation;
    private double variance;
    private double standardDeviation;
    private int precision;
    private List<PointXY> points;

    public SeriesProperties() {
    }

    public int getSize() {
        return size;
    }

    public synchronized void setSize(int size) {
        this.size = size;
    }

    public double getFastVariation() {
        return fastVariation;
    }

    public synchronized void setFastVariation(double fastVariation) {
        this.fastVariation = fastVariation;
    }

    public double getVariance() {
        return variance;
    }

    public synchronized void setVariance(double variance) {
        this.variance = variance;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public synchronized void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public int getPrecision() {
        return precision;
    }

    public synchronized void setPrecision(int precision) {
        this.precision = precision;
    }

    public List<PointXY> getPoints() {
        return points;
    }

    public synchronized void setPoints(List<PointXY> points) {
        this.points = points;
        this.size = points.size();
    }
}

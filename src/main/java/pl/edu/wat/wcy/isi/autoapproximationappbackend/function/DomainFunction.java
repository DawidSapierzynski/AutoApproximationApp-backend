package pl.edu.wat.wcy.isi.autoapproximationappbackend.function;

public class DomainFunction {
    private boolean leftClosedInterval;
    private boolean rightClosedInterval;
    private double beginningInterval;
    private double endInterval;

    public DomainFunction(boolean leftClosedInterval, double beginningInterval, double endInterval, boolean rightClosedInterval) {
        this.leftClosedInterval = leftClosedInterval;
        this.rightClosedInterval = rightClosedInterval;
        this.beginningInterval = beginningInterval;
        this.endInterval = endInterval;
    }

    public boolean isLeftClosedInterval() {
        return leftClosedInterval;
    }

    public boolean isRightClosedInterval() {
        return rightClosedInterval;
    }

    public double getBeginningInterval() {
        return beginningInterval;
    }

    public double getEndInterval() {
        return endInterval;
    }

    @Override
    public String toString() {
        return (leftClosedInterval ? "<" : "(") + beginningInterval + ";" + endInterval + (rightClosedInterval ? ">" : ")");
    }
}

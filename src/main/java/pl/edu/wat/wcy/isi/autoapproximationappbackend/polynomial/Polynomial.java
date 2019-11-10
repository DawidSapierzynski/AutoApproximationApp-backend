package pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.LinearDomainMapping;

import java.util.List;

public abstract class Polynomial {

    /**
     * coefficients of polynomial
     */
    private List<Double> coefficients;

    /**
     * degree of polynomial
     */
    private int deg;

    public Polynomial(List<Double> coefficients) {
        this.coefficients = coefficients;
        this.reduce();
    }

    public static double getValueMonomial(double pointsX, int j) {
        return Math.pow(pointsX, j);
    }


    private void reduce() {
        for (int i = coefficients.size() - 1; i > 0; i--) {
            if (coefficients.get(i) != 0) {
                this.deg = i;
                return;
            } else {
                coefficients.remove(i);
            }
        }
    }

    public abstract double evaluate(double x);

    public double evaluate(double x, LinearDomainMapping linearDomainMapping) {
        if (linearDomainMapping != null) {
            double mappX, y;

            mappX = linearDomainMapping.getLinearFunction().evaluate(x);
            y = evaluate(mappX);

            return y;
        } else {
            return evaluate(x);
        }
    }

    public static boolean isEmpty(Polynomial polynomial) {
        return polynomial == null || polynomial.getCoefficients() == null;
    }

    public static boolean isNotEmpty(Polynomial polynomial) {
        return !isEmpty(polynomial);
    }

    public List<Double> getCoefficients() {
        return coefficients;
    }

    public int getDeg() {
        return deg;
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
        if (coefficients != null) {
            this.deg = coefficients.size() - 1;
        }
    }
}

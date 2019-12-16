package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.LinearDomainMapping;

import java.util.List;

public abstract class Polynomial {

    /**
     * coefficients of polynomial
     */
    private List<Double> coefficients;

    /**
     * degree of polynomial
     */
    private int degree;

    public Polynomial(List<Double> coefficients) {
        this.coefficients = coefficients;
        this.reduce();
    }

    private void reduce() {
        for (int i = coefficients.size() - 1; i > 0; i--) {
            if (coefficients.get(i) != 0) {
                this.degree = i;
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

    public int getDegree() {
        return degree;
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
        if (coefficients != null) {
            this.degree = coefficients.size() - 1;
        }
    }
}

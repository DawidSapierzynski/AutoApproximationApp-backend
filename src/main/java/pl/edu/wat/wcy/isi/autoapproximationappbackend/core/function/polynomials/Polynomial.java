package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials;

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

    public static boolean isEmpty(Polynomial polynomial) {
        return polynomial == null || polynomial.getCoefficients() == null;
    }

    public static boolean isNotEmpty(Polynomial polynomial) {
        return !isEmpty(polynomial);
    }

    public int getDegree() {
        return degree;
    }

    protected void setDegree(int degree) {
        this.degree = degree;
    }

    public List<Double> getCoefficients() {
        return coefficients;
    }

}

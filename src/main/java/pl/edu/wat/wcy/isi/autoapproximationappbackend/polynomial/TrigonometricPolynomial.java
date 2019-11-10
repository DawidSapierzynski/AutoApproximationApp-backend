package pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TrigonometricPolynomial extends Polynomial {
    private Logger logger = LoggerFactory.getLogger(TrigonometricPolynomial.class);

    public TrigonometricPolynomial(List<Double> coefficients) {
        super(coefficients);
    }

    public TrigonometricPolynomial plus(TrigonometricPolynomial polynomial) {
        List<Double> result;
        List<Double> coefficients = this.getCoefficients();

        if (Polynomial.isNotEmpty(polynomial) && coefficients != null) {
            int minSize = Math.min(this.getDeg(), polynomial.getDeg());
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) + polynomial.getCoefficients().get(i));
            }
            if (polynomial.getDeg() > minSize) {
                for (int i = minSize + 1; i <= polynomial.getDeg(); i++) {
                    result.add(polynomial.getCoefficients().get(i));
                }
            } else {
                for (int i = minSize + 1; i <= this.getDeg(); i++) {
                    result.add(this.getCoefficients().get(i));
                }
            }

            return new TrigonometricPolynomial(result);
        } else {
            logger.error("Polynomial or coefficients is empty.");
            return null;
        }
    }

    public TrigonometricPolynomial minus(TrigonometricPolynomial polynomial) {
        List<Double> result;
        List<Double> coefficients = this.getCoefficients();

        if (Polynomial.isNotEmpty(polynomial) && coefficients != null) {
            int minSize = Math.min(this.getDeg(), polynomial.getDeg());
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) - polynomial.getCoefficients().get(i));
            }

            if (polynomial.getDeg() > minSize) {
                for (int i = minSize + 1; i <= polynomial.getDeg(); i++) {
                    result.add(-polynomial.getCoefficients().get(i));
                }
            } else {
                for (int i = minSize + 1; i <= this.getDeg(); i++) {
                    result.add(this.getCoefficients().get(i));
                }
            }

            return new TrigonometricPolynomial(result);
        } else {
            logger.error("Polynomial or coefficients is empty.");
            return null;
        }
    }

    @Override
    public double evaluate(double x) {
        double result = 0.0;
        List<Double> coefficients = this.getCoefficients();

        if (!coefficients.isEmpty()) {
            result += 0.5 * coefficients.get(0);
            for (int i = 1; i < coefficients.size() / 2.0; i++) {
                result += coefficients.get(2 * i - 1) * Math.cos(Math.toRadians(i * x));
                result += coefficients.get(2 * i) * Math.sin(Math.toRadians(i * x));
            }
            if (coefficients.size() % 2 == 0) {
                int sizeCoefficient = coefficients.size();
                result += 0.5 * coefficients.get(sizeCoefficient - 1) * Math.cos(Math.toRadians((sizeCoefficient / 2.0) * x));
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TrigonometricPolynomial) {
            return obj == this || (this.getDeg() == ((TrigonometricPolynomial) obj).getDeg() && this.getCoefficients().equals(((TrigonometricPolynomial) obj).getCoefficients()));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        List<Double> coefficients = this.getCoefficients();
        StringBuilder stringBuilder = new StringBuilder("TrigonometricPolynomial: ");

        if (!coefficients.isEmpty()) {
            stringBuilder.append(coefficients.get(0)).append("*").append(0.5);
            for (int i = 1; i < coefficients.size() / 2.0; i++) {
                stringBuilder.append(" + ").append(coefficients.get(2 * i - 1)).append("*cos(").append(i).append("x").append(")");
                stringBuilder.append(" + ").append(coefficients.get(2 * i)).append("*sin(").append(i).append("x").append(")");
            }
            if (coefficients.size() % 2 == 0) {
                int sizeCoefficient = coefficients.size();
                stringBuilder.append(" + ").append(coefficients.get(sizeCoefficient - 1)).append("*cos(").append(sizeCoefficient / 2).append("x").append(")");
            }
            return stringBuilder.toString();
        }
        return "TrigonometricPolynomial is empty";
    }
}

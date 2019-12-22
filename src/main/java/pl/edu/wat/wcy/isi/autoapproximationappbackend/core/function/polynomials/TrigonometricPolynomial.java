package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TrigonometricPolynomial extends Polynomial {
    private final Logger logger = LoggerFactory.getLogger(TrigonometricPolynomial.class);

    public TrigonometricPolynomial(List<Double> coefficients) {
        super(coefficients);
        setDegree(chooseTrigonometricDegree(coefficients.size()));
    }

    public TrigonometricPolynomial plus(TrigonometricPolynomial polynomial) {
        List<Double> result;
        List<Double> coefficients = this.getCoefficients();
        int sizeThis = this.getCoefficients().size() - 1;
        int sizeThat = polynomial.getCoefficients().size() - 1;

        if (isNotEmpty(polynomial) && coefficients != null) {
            int minSize = Math.min(sizeThis, sizeThat);
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) + polynomial.getCoefficients().get(i));
            }
            if (polynomial.getCoefficients().size() - 1 > minSize) {
                for (int i = minSize + 1; i <= sizeThat; i++) {
                    result.add(polynomial.getCoefficients().get(i));
                }
            } else {
                for (int i = minSize + 1; i <= sizeThis; i++) {
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
        int sizeThis = this.getCoefficients().size() - 1;
        int sizeThat = polynomial.getCoefficients().size() - 1;

        if (isNotEmpty(polynomial) && coefficients != null) {
            int minSize = Math.min(sizeThis, sizeThat);
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) - polynomial.getCoefficients().get(i));
            }

            if (sizeThat > minSize) {
                for (int i = minSize + 1; i <= sizeThat; i++) {
                    result.add(-polynomial.getCoefficients().get(i));
                }
            } else {
                for (int i = minSize + 1; i <= sizeThis; i++) {
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
                result += coefficients.get(2 * i - 1) * Math.cos(i * x);
                result += coefficients.get(2 * i) * Math.sin(i * x);
            }
            if (coefficients.size() % 2 == 0) {
                int sizeCoefficient = coefficients.size();
                result += 0.5 * coefficients.get(sizeCoefficient - 1) * Math.cos((sizeCoefficient / 2.0) * x);
            }
        }
        return result;
    }

    public double evaluate(double x, AlgebraicPolynomial linearFunction) {
        //TODO
        //Do potymalizacji
        if (linearFunction != null) {
            double mappX, y;

            mappX = linearFunction.evaluate(x);
            y = evaluate(mappX);

            return y;
        } else {
            return evaluate(x);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TrigonometricPolynomial) {
            return obj == this || (this.getDegree() == ((TrigonometricPolynomial) obj).getDegree() && this.getCoefficients().equals(((TrigonometricPolynomial) obj).getCoefficients()));
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
                stringBuilder.append(" + ").append(coefficients.get(sizeCoefficient - 1) / 2).append("*cos(").append(sizeCoefficient / 2).append("x").append(")");
            }
            return stringBuilder.toString();
        }
        return "TrigonometricPolynomial is empty";
    }

    public static int chooseTrigonometricDegree(int size) {
        if (size % 2 == 0) {
            return size / 2;
        } else {
            return (size - 1) / 2;
        }
    }
}

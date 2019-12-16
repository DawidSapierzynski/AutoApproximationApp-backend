package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlgebraicPolynomial extends Polynomial {
    private final Logger logger = LoggerFactory.getLogger(AlgebraicPolynomial.class);

    public AlgebraicPolynomial(List<Double> coefficients) {
        super(coefficients);
    }

    public AlgebraicPolynomial(double[] coefficients) {
        super(mapCoefficients(coefficients));
    }

    public static List<Double> mapCoefficients(double[] coefficients) {
        return Arrays.stream(coefficients).boxed().collect(Collectors.toList());
    }

    public AlgebraicPolynomial plus(AlgebraicPolynomial polynomial) {
        List<Double> result;
        List<Double> coefficients = this.getCoefficients();

        if (Polynomial.isNotEmpty(polynomial) && coefficients != null) {
            AlgebraicPolynomial add = polynomial.getDegree() > getDegree() ? polynomial : this;
            int minSize = Math.min(this.getDegree(), polynomial.getDegree());
            int maxSize = Math.max(this.getDegree(), polynomial.getDegree());
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) + polynomial.getCoefficients().get(i));
            }
            for (int i = minSize + 1; i <= maxSize; i++) {
                result.add(add.getCoefficients().get(i));
            }

            return new AlgebraicPolynomial(result);
        } else {
            logger.error("Polynomial or coefficients is empty.");
            return null;
        }
    }

    public AlgebraicPolynomial plus(double value) {
        List<Double> result = new ArrayList<>();
        List<Double> coefficients = getCoefficients();

        result.add(coefficients.get(0) + value);

        for (int i = 1; i <= getDegree(); i++) {
            result.add(coefficients.get(i));
        }

        return new AlgebraicPolynomial(result);
    }

    public AlgebraicPolynomial minus(AlgebraicPolynomial polynomial) {
        List<Double> result;
        List<Double> coefficients = this.getCoefficients();

        if (Polynomial.isNotEmpty(polynomial) && coefficients != null) {
            int minSize = Math.min(this.getDegree(), polynomial.getDegree());
            result = new ArrayList<>();

            for (int i = 0; i <= minSize; i++) {
                result.add(coefficients.get(i) - polynomial.getCoefficients().get(i));
            }

            if (polynomial.getDegree() > minSize) {
                for (int i = minSize + 1; i <= polynomial.getDegree(); i++) {
                    result.add(-polynomial.getCoefficients().get(i));
                }
            } else {
                for (int i = minSize + 1; i <= this.getDegree(); i++) {
                    result.add(this.getCoefficients().get(i));
                }
            }

            return new AlgebraicPolynomial(result);
        } else {
            logger.error("Polynomial or coefficients is empty.");
            return null;
        }
    }

    public AlgebraicPolynomial minus(double value) {
        return plus(-value);
    }

    public AlgebraicPolynomial times(AlgebraicPolynomial polynomial) {
        List<Double> result = new ArrayList<>();
        List<Double> thisCoefficients = this.getCoefficients();

        if (Polynomial.isNotEmpty(polynomial) && thisCoefficients != null) {
            List<Double> thatCoefficients = polynomial.getCoefficients();

            for (int i = 0; i <= this.getDegree(); i++) {
                for (int j = 0; j <= polynomial.getDegree(); j++) {
                    if (result.size() <= i + j) {
                        result.add(thisCoefficients.get(i) * thatCoefficients.get(j));
                    } else {
                        result.set(i + j, result.get(i + j) + thisCoefficients.get(i) * thatCoefficients.get(j));
                    }
                }
            }

            return new AlgebraicPolynomial(result);
        } else {
            logger.error("Polynomial or coefficients is empty.");
            return null;
        }
    }

    public AlgebraicPolynomial times(double value) {
        List<Double> result = new ArrayList<>();
        List<Double> thisCoefficients = this.getCoefficients();

        for (Double d : thisCoefficients) {
            result.add(d * value);
        }

        return new AlgebraicPolynomial(result);
    }

    @Override
    public double evaluate(double x) {
        double result = 0.0;
        List<Double> coefficients = this.getCoefficients();
        for (int i = 0; i < coefficients.size(); i++) {
            result += coefficients.get(i) * Math.pow(x, i);
        }
        return result;
    }

    public double inverseLinearFunction(double y) {
        if (getCoefficients().size() == 2) {
            double x;
            x = (y - getCoefficients().get(0)) / getCoefficients().get(1);

            return x;
        } else {
            throw new RuntimeException("This is not linear function.");
        }
    }

    public static AlgebraicPolynomial getLinearFunction(double a, double b) {
        return new AlgebraicPolynomial(List.of(b, a));
    }

    public static double getValueMonomial(double pointsX, int j) {
        return Math.pow(pointsX, j);
    }

    public AlgebraicPolynomial copy() {
        return new AlgebraicPolynomial(List.copyOf(this.getCoefficients()));
    }

    public static AlgebraicPolynomial pow(AlgebraicPolynomial algebraicPolynomial, int degree) {
        if (degree == 0) {
            return new AlgebraicPolynomial(List.of(1d));
        } else {
            AlgebraicPolynomial result = algebraicPolynomial.copy();
            for (int i = 1; i < degree; i++) {
                result = result.times(algebraicPolynomial);
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlgebraicPolynomial) {
            return obj == this || (this.getDegree() == ((AlgebraicPolynomial) obj).getDegree() && this.getCoefficients().equals(((AlgebraicPolynomial) obj).getCoefficients()));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Double> coefficients = getCoefficients();

        for (int i = getDegree(); i >= 0; i--) {
            stringBuilder.append(coefficients.get(i)).append("*x^").append(i);
            if (i > 0) {
                stringBuilder.append(" + ");
            }
        }

        return "AlgebraicPolynomial: " + stringBuilder.toString();
    }
}

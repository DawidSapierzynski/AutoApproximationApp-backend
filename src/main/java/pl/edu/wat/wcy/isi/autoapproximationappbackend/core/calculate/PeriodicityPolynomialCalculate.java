package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import static java.lang.Math.*;

public class PeriodicityPolynomialCalculate implements Runnable {
    private static final int MIN_DEGREE = 4;
    private final int degree;
    private final Logger logger = LoggerFactory.getLogger(PeriodicityPolynomialCalculate.class);
    private final DataSeriesFileEntity dataSeriesFile;

    public PeriodicityPolynomialCalculate(DataSeriesFileEntity dataSeriesFile) {
        this.dataSeriesFile = dataSeriesFile;
        this.degree = calculateDegree(dataSeriesFile.getSize());
    }

    @Override
    public void run() {
        double errorPolynomial;

        logger.debug("PeriodicityPolynomialCalculate degree: {}", this.degree);
        PolynomialApproximation polynomialApproximation = new PolynomialApproximation(dataSeriesFile.getPoints(), degree);

        polynomialApproximation.doApproximations();

        errorPolynomial = polynomialApproximation.calculateError();
        logger.debug("Calculated errorPolynomial: {}", errorPolynomial);

        dataSeriesFile.setErrorPolynomial(errorPolynomial);
        logger.info("Set errorPolynomial: {}", errorPolynomial);
    }

    public static int calculateDegree(int size) {
        int d = (int) ceil(log(pow(size, 3)));
        int maxDegree = size - 1;

        if (d < MIN_DEGREE) {
            d = MIN_DEGREE;
        } else if (d > maxDegree) {
            d = maxDegree;
        }

        return d;
    }
}

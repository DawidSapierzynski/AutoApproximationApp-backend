package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import static java.lang.Math.*;

public class FastVariationPolynomialCalculate implements Runnable {
    private final int degree;
    private final Logger logger = LoggerFactory.getLogger(FastVariationPolynomialCalculate.class);
    private final DataSeriesFileEntity dataSeriesFile;

    public FastVariationPolynomialCalculate(DataSeriesFileEntity dataSeriesFile) {
        this.dataSeriesFile = dataSeriesFile;
        this.degree = calculateDegree(dataSeriesFile.getSize());
    }

    @Override
    public void run() {
        double fastVariationPolynomial;

        logger.debug("FastVariationPolynomialCalculate degree: {}", this.degree);
        PolynomialApproximation polynomialApproximation = new PolynomialApproximation(dataSeriesFile.getPoints(), degree);

        polynomialApproximation.doApproximations();

        fastVariationPolynomial = polynomialApproximation.getError();
        logger.debug("Calculated fastVariationPolynomial: {}", fastVariationPolynomial);

        dataSeriesFile.setFastVariationPolynomial(fastVariationPolynomial);
        logger.info("Set fastVariationPolynomial: {}", fastVariationPolynomial);
    }

    public static int calculateDegree(int size) {
        int d = (int) ceil(log(pow(size, 3)));
        int maxDegree = size - 1;

        if (d < 4) {
            d = 4;
        } else if (d > maxDegree) {
            d = maxDegree;
        }

        return d;
    }
}

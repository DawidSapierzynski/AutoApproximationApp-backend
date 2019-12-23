package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

public class FastVariationPolynomialCalculate implements Runnable {
    private int degree;
    private final Logger logger = LoggerFactory.getLogger(FastVariationPolynomialCalculate.class);
    private final SeriesPropertiesEntity seriesProperties;

    public FastVariationPolynomialCalculate(SeriesPropertiesEntity seriesProperties) {
        this.seriesProperties = seriesProperties;
        this.degree = calculateDegree(seriesProperties.getSize());
    }

    @Override
    public void run() {
        double fastVariationPolynomial;

        logger.debug("FastVariationPolynomialCalculate degree: {}", this.degree);
        PolynomialApproximation polynomialApproximation = new PolynomialApproximation(seriesProperties.getPoints(), degree);

        polynomialApproximation.doApproximations();

        fastVariationPolynomial = polynomialApproximation.getError();
        logger.debug("Calculated fastVariationPolynomial: {}", fastVariationPolynomial);

        seriesProperties.setFastVariationPolynomial(fastVariationPolynomial);
        logger.info("Set fastVariationPolynomial: {}", fastVariationPolynomial);
    }

    public static int calculateDegree(int size) {
        int d = (int) Math.ceil(Math.log(size));
        if (d < 4) {
            d = 4;
        }

        return d;
    }
}

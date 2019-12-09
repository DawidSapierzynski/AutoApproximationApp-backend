package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

public class FastVariationPolynomialCalculate implements Runnable {
    private static final int DEGREE = 4;
    private Logger logger = LoggerFactory.getLogger(FastVariationPolynomialCalculate.class);
    private SeriesPropertiesEntity seriesProperties;

    public FastVariationPolynomialCalculate(SeriesPropertiesEntity seriesProperties) {
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        double fastVariationPolynomial;
        PolynomialApproximation polynomialApproximation = new PolynomialApproximation(seriesProperties.getPoints(), DEGREE);

        polynomialApproximation.doApproximations();

        fastVariationPolynomial = polynomialApproximation.getError();
        logger.debug("Calculated fastVariationPolynomial: {}", fastVariationPolynomial);

        seriesProperties.setFastVariationPolynomial(fastVariationPolynomial);
        logger.info("Set fastVariationPolynomial: {}", fastVariationPolynomial);
    }
}

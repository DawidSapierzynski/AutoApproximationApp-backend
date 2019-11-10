package pl.edu.wat.wcy.isi.autoapproximationappbackend.propertiesCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.PolynomialApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

public class FastVariationCalculate implements Runnable {
    private Logger logger = LoggerFactory.getLogger(FastVariationCalculate.class);
    private SeriesProperties seriesProperties;

    public FastVariationCalculate(SeriesProperties seriesProperties) {
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        double fastVariation;
        PolynomialApproximation polynomialApproximation = new PolynomialApproximation(seriesProperties.getPoints(), 5);

        fastVariation = polynomialApproximation.getError();
        logger.debug("Calculated fastVariation: {}", fastVariation);

        seriesProperties.setFastVariation(fastVariation);
        logger.info("Set fastVariation: {}", fastVariation);
    }

}

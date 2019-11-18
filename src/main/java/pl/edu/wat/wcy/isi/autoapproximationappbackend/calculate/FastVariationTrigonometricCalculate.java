package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

public class FastVariationTrigonometricCalculate implements Runnable {
    private Logger logger = LoggerFactory.getLogger(FastVariationTrigonometricCalculate.class);
    private SeriesProperties seriesProperties;

    public FastVariationTrigonometricCalculate(SeriesProperties seriesProperties) {
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        double fastVariationTrigonometric;
        TrigonometricApproximation trigonometricApproximation = new TrigonometricApproximation(seriesProperties.getPoints(), 2);

        trigonometricApproximation.doApproximations();

        fastVariationTrigonometric = trigonometricApproximation.getError();
        logger.debug("Calculated fastVariationTrigonometric: {}", fastVariationTrigonometric);

        seriesProperties.setFastVariationTrigonometric(fastVariationTrigonometric);
        logger.info("Set fastVariationTrigonometric: {}", fastVariationTrigonometric);
    }
}

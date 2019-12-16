package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

public class FastVariationTrigonometricCalculate implements Runnable {
    private static final int DEGREE = 2;
    private final Logger logger = LoggerFactory.getLogger(FastVariationTrigonometricCalculate.class);
    private final SeriesPropertiesEntity seriesProperties;

    public FastVariationTrigonometricCalculate(SeriesPropertiesEntity seriesProperties) {
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        double fastVariationTrigonometric;
        TrigonometricApproximation trigonometricApproximation = new TrigonometricApproximation(seriesProperties.getPoints(), DEGREE);

        trigonometricApproximation.doApproximations();

        fastVariationTrigonometric = trigonometricApproximation.getError();
        logger.debug("Calculated fastVariationTrigonometric: {}", fastVariationTrigonometric);

        seriesProperties.setFastVariationTrigonometric(fastVariationTrigonometric);
        logger.info("Set fastVariationTrigonometric: {}", fastVariationTrigonometric);
    }
}

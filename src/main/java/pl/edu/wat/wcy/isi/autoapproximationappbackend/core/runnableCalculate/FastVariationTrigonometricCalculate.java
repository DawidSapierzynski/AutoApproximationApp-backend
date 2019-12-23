package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

public class FastVariationTrigonometricCalculate implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(FastVariationTrigonometricCalculate.class);
    private final SeriesPropertiesEntity seriesProperties;
    private int degree;

    public FastVariationTrigonometricCalculate(SeriesPropertiesEntity seriesProperties) {
        this.seriesProperties = seriesProperties;
        this.degree = calculateDegree(seriesProperties.getSize());
    }

    @Override
    public void run() {
        double fastVariationTrigonometric;

        logger.debug("FastVariationTrigonometricCalculate degree: {}", this.degree);
        TrigonometricApproximation trigonometricApproximation = new TrigonometricApproximation(seriesProperties.getPoints(), this.degree);

        trigonometricApproximation.doApproximations();

        fastVariationTrigonometric = trigonometricApproximation.getError();
        logger.debug("Calculated fastVariationTrigonometric: {}", fastVariationTrigonometric);

        seriesProperties.setFastVariationTrigonometric(fastVariationTrigonometric);
        logger.info("Set fastVariationTrigonometric: {}", fastVariationTrigonometric);
    }

    public static int calculateDegree(int size) {
        int d = (int) Math.ceil(Math.log(size) / 2);
        if (d < 2) {
            d = 2;
        }

        return d;
    }
}

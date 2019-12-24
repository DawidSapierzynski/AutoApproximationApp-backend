package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

import static java.lang.Math.*;

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
        int d = (int) ceil(log(pow(size, 3)) / 2);
        int maxDegree = TrigonometricPolynomial.chooseTrigonometricDegree(size);

        if (d < 2) {
            d = 2;
        } else if (d > maxDegree) {
            d = maxDegree;
        }

        return d;
    }
}

package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricApproximation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import static java.lang.Math.*;

public class PeriodicityTrigonometricCalculate implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicityTrigonometricCalculate.class);
    private final DataSeriesFileEntity dataSeriesFile;
    private final int degree;

    public PeriodicityTrigonometricCalculate(DataSeriesFileEntity dataSeriesFile) {
        this.dataSeriesFile = dataSeriesFile;
        this.degree = calculateDegree(dataSeriesFile.getSize());
    }

    @Override
    public void run() {
        double errorTrigonometric;

        logger.debug("PeriodicityTrigonometricCalculate degree: {}", this.degree);
        TrigonometricApproximation trigonometricApproximation = new TrigonometricApproximation(dataSeriesFile.getPoints(), this.degree);

        trigonometricApproximation.doApproximations();

        errorTrigonometric = trigonometricApproximation.calculateError();
        logger.debug("Calculated periodicityTrigonometric: {}", errorTrigonometric);

        dataSeriesFile.setErrorTrigonometric(errorTrigonometric);
        logger.info("Set periodicityTrigonometric: {}", errorTrigonometric);
    }

    public static int calculateDegree(int size) {
        int d = (int) ceil(log(pow(size, 3)));
        int maxDegree = TrigonometricPolynomial.chooseTrigonometricMaxDegree(size);

        if (d < 2) {
            d = 2;
        } else if (d > maxDegree) {
            d = maxDegree;
        }

        return d;
    }
}

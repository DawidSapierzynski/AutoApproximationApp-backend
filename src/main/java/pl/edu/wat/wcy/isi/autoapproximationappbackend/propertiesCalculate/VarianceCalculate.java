package pl.edu.wat.wcy.isi.autoapproximationappbackend.propertiesCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

import java.util.List;

public class VarianceCalculate implements Runnable {
    private Logger logger = LoggerFactory.getLogger(VarianceCalculate.class);
    private SeriesProperties seriesProperties;

    public VarianceCalculate(SeriesProperties seriesProperties) {
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        List<PointXY> points = seriesProperties.getPoints();
        double average, variance;

        average = points.stream().mapToDouble(PointXY::getY).sum() / points.size();
        logger.debug("Calculated average: {}", average);

        variance = points.stream().mapToDouble((p) -> Math.pow(p.getY() - average, 2)).sum() / points.size();
        logger.debug("Calculated variance: {}", average);

        seriesProperties.setVariance(variance);
        logger.info("Set variance: {}", seriesProperties.getVariance());

        seriesProperties.setStandardDeviation(Math.sqrt(variance));
        logger.info("Set standard deviation: {}", seriesProperties.getStandardDeviation());
    }
}

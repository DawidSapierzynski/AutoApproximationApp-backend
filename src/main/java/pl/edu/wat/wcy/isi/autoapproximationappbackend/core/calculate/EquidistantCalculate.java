package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception.SizeException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import java.util.List;

public class EquidistantCalculate implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EquidistantCalculate.class);
    private static final double DELTA = 1e-14;

    private final DataSeriesFileEntity dataSeriesFile;

    public EquidistantCalculate(DataSeriesFileEntity dataSeriesFile) {
        this.dataSeriesFile = dataSeriesFile;
    }

    @Override
    public void run() {
        try {
            boolean equidistant = getEquidistant(dataSeriesFile.getPoints());

            dataSeriesFile.setEquidistant(equidistant ? (byte) 1 : (byte) 0);

        } catch (SizeException e) {
            logger.error("{}", e.getMessage());
        }
    }

    private boolean getEquidistant(List<PointXY> points) throws SizeException {
        if (points.size() < 2) {
            throw new SizeException("The number of points is less than 2.");
        }

        int numberEquidistant = 0;
        double difOne = points.get(1).getX() - points.get(0).getX();

        for (int i = 1; i < points.size() - 1; i++) {
            double dif = points.get(i + 1).getX() - points.get(i).getX();
            if (!(Math.abs(dif - difOne) <= DELTA)) {
                numberEquidistant++;
            }
        }

        return numberEquidistant <= dataSeriesFile.getArtefacts().size();
    }
}

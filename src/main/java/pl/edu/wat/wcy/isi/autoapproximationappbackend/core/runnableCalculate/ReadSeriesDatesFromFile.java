package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.runnableCalculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.FileStorageProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReadSeriesDatesFromFile implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(ReadSeriesDatesFromFile.class);

    private static final int MAX_MULTIPLE = 60;

    private final String seriesDatesPath;
    private final String seriesDatesName;
    private final SeriesPropertiesEntity seriesProperties;

    public ReadSeriesDatesFromFile(String seriesDatesName, SeriesPropertiesEntity seriesProperties, FileStorageProperties fileStorageProperties) {
        this.seriesDatesName = seriesDatesName;
        this.seriesProperties = seriesProperties;
        this.seriesDatesPath = fileStorageProperties.getUploadDir() + "\\" + seriesDatesName;
    }

    @Override
    public void run() {
        List<PointXY> points = getPoints(seriesDatesPath);
        List<PointXY> artefacts = getArtefact(points);

        this.seriesProperties.setArtefacts(artefacts);
        this.seriesProperties.setPoints(points);
        this.seriesProperties.setSize(points.size());


        logger.debug("The file was read correctly: {}", seriesDatesName);
    }

    private List<PointXY> getArtefact(List<PointXY> points) {
        List<PointXY> artefacts = new ArrayList<>();
        List<Double> quotientValue = new ArrayList<>();
        List<Integer> indexRemove = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            quotientValue.add(Math.abs(points.get(i + 1).getY() / points.get(i).getY()));
        }

        for (int i = 0; i < quotientValue.size() - 1; i++) {
            if (checkValue(quotientValue.get(i)) && checkValue(quotientValue.get(i + 1))) {
                artefacts.add(points.get(i + 1));
            }
        }

        points.removeAll(artefacts);

        return artefacts;
    }

    private boolean checkValue(double value) {
        return value <= 1d / MAX_MULTIPLE || value >= MAX_MULTIPLE;
    }

    private List<PointXY> getPoints(String seriesDatesPath) {
        List<PointXY> points = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(seriesDatesPath));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.startsWith("//"))
                    continue;

                String[] xy = line.split("[;,]");

                if (xy.length == 2) {
                    double x, y;

                    x = Double.parseDouble(xy[0].trim());
                    y = Double.parseDouble(xy[1].trim());

                    PointXY pointXY = new PointXY(x, y);
                    points.add(pointXY);
                    logger.debug("Add point: {}", pointXY);
                } else {
                    logger.error("Not prase line: {}", line);
                }
            }

            Collections.sort(points);
        } catch (FileNotFoundException | NumberFormatException e) {
            logger.error("{}", e.getMessage());
        }

        logger.info("Points have been loaded.");
        return points;
    }
}

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

    private static final int MAX_MULTIPLE = 20;
    private static final int CYCLE_SIZE = 10;
    private static final String REGEX_SPLIT = "[;,]";

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
        List<Double> differencesList = new ArrayList<>();
        double trimmedMean;

        for (int i = 0; i < points.size() - 1; i++) {
            differencesList.add(Math.abs(points.get(i + 1).getY() - points.get(i).getY()));
        }

        trimmedMean = getTrimmedMean(differencesList);
        this.logger.debug("Trimmed mean of differences: {}", trimmedMean);

        for (int i = 0; i < differencesList.size() - 1; i++) {
            if (checkValues(differencesList.get(i), differencesList.get(i + 1), trimmedMean * MAX_MULTIPLE)) {
                artefacts.add(points.get(i + 1));
            }
        }

        points.removeAll(artefacts);

        return artefacts;
    }

    private List<PointXY> getPoints(String seriesDatesPath) {
        List<PointXY> points = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(seriesDatesPath));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                line = line.trim();
                if (line.startsWith("//"))
                    continue;

                String[] xy = line.split(REGEX_SPLIT);

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

    private double getTrimmedMean(List<Double> valuesList) {
        List<Double> list = new ArrayList<>(List.copyOf(valuesList));

        list.sort(Double::compareTo);

        for (int i = 0; i < valuesList.size(); i += CYCLE_SIZE) {
            list.remove(list.size() - 1);
            list.remove(list.size() - 1);
        }

        return list.stream()
                .mapToDouble((v) -> v)
                .average()
                .orElse(0d);
    }

    private boolean checkValues(double value1, double value2, double limit) {
        return value1 > limit && value2 > limit;
    }
}

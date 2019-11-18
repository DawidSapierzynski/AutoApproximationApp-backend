package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ReadSeriesDatesFromFile implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ReadSeriesDatesFromFile.class);

    private File seriesDatesFile;
    private SeriesProperties seriesProperties;

    public ReadSeriesDatesFromFile(File seriesDatesFile, SeriesProperties seriesProperties) {
        this.seriesDatesFile = seriesDatesFile;
        this.seriesProperties = seriesProperties;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(seriesDatesFile);
            ArrayList<PointXY> points = new ArrayList<>();

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
            logger.info("Points have been sorted.");

            this.seriesProperties.setPoints(points);

        } catch (FileNotFoundException | NumberFormatException e) {
            logger.error("{}", e.getMessage());
        }

        logger.debug("The file was read correctly: {}", seriesDatesFile.getName());
    }
}

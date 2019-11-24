package pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.FileStorageProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ReadSeriesDatesFromFile implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ReadSeriesDatesFromFile.class);

    private String seriesDatesPath;
    private String seriesDatesName;
    private SeriesProperties seriesProperties;

    public ReadSeriesDatesFromFile(String seriesDatesName, SeriesProperties seriesProperties, FileStorageProperties fileStorageProperties) {
        this.seriesDatesName = seriesDatesName;
        this.seriesProperties = seriesProperties;
        this.seriesDatesPath = fileStorageProperties.getUploadDir() + "\\" + seriesDatesName + ".csv";
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(new File(seriesDatesPath));
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

        logger.debug("The file was read correctly: {}", seriesDatesName);
    }
}

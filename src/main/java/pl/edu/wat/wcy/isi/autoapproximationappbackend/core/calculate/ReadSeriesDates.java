package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.calculate;

import org.slf4j.Logger;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ReadSeriesDates {
    protected static final int MAX_MULTIPLE = 20;
    protected static final int CYCLE_SIZE = 10;
    protected static final String REGEX_SPLIT = "[;,]";

    protected final DataSeriesFileEntity dataSeriesFile;

    public ReadSeriesDates(DataSeriesFileEntity dataSeriesFile) {
        this.dataSeriesFile = dataSeriesFile;
    }

    protected static void parseLine(List<PointXY> points, String line, String[] split, Logger logger) throws NumberFormatException {
        line = line.trim();
        if (line.startsWith("//"))
            return;

        if (split.length == 2) {
            double x, y;

            x = Double.parseDouble(split[0].trim());
            y = Double.parseDouble(split[1].trim());

            PointXY pointXY = new PointXY(x, y);
            points.add(pointXY);
            logger.debug("Add point: {}", pointXY);
        } else {
            logger.error("Not prase line: {}", line);
        }
    }

    protected List<PointXY> getArtefact(List<PointXY> points, Logger logger) {
        List<PointXY> artefacts = new ArrayList<>();
        List<Double> differencesList = new ArrayList<>();
        double trimmedMean;

        for (int i = 0; i < points.size() - 1; i++) {
            differencesList.add(Math.abs(points.get(i + 1).getY() - points.get(i).getY()));
        }

        trimmedMean = getTrimmedMean(differencesList);
        logger.debug("Trimmed mean of differences: {}", trimmedMean);

        for (int i = 0; i < differencesList.size() - 1; i++) {
            if (checkValues(differencesList.get(i), differencesList.get(i + 1), trimmedMean * MAX_MULTIPLE)) {
                artefacts.add(points.get(i + 1));
            }
        }

        points.removeAll(artefacts);

        return artefacts;
    }

    protected double getTrimmedMean(List<Double> valuesList) {
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

    protected boolean checkValues(double value1, double value2, double limit) {
        return value1 > limit && value2 > limit;
    }
}

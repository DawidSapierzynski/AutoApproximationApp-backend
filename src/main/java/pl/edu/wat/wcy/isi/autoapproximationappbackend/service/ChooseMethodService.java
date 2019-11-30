package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ChosenMethod;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ChooseMethodService {
    private Logger logger = LoggerFactory.getLogger(ChooseMethodService.class);

    private ExecutorService threadPool;

    public ChooseMethodService(@Value("${number.threads}") int nThreads) {
        this.threadPool = Executors.newFixedThreadPool(nThreads);
    }

    public List<ChosenMethod> selectMethods(SeriesProperties seriesProperties) {
        List<ChosenMethod> chosenMethods = new ArrayList<>();

        switch (seriesProperties.getPrecision()) {
            case 1:
                selectToOne(chosenMethods, seriesProperties.isFastVariation());
                break;
            case 2:
                selectToTwo(chosenMethods, seriesProperties.isFastVariation(), seriesProperties.getSize());
                break;
            case 3:
                selectToThree(chosenMethods, seriesProperties.isFastVariation(), seriesProperties.getSize());
                break;
            case 4:
                selectToFour(chosenMethods, seriesProperties.isFastVariation(), seriesProperties.getSize());
                break;
            case 5:
                selectToFive(chosenMethods, seriesProperties.isFastVariation(), seriesProperties.getSize());
                break;
        }

        return chosenMethods;
    }

    private void selectToFive(List<ChosenMethod> chosenMethods, boolean isFastVariation, int size) {
        if (isFastVariation) {
            chosenMethods.add(new ChosenMethod(Method.TRIGONOMETRICINTERPOLATION, TrigonometricInterpolation.chooseTrigonometricDegree(size)));
        } else {
            chosenMethods.add(new ChosenMethod(Method.NEWTONINTERPOLATION, size - 1));
        }
        chosenMethods.add(new ChosenMethod(Method.SPLINEINTERPOLATION, 3));
    }

    private void selectToFour(List<ChosenMethod> chosenMethods, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size) + 2;
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethods.add(new ChosenMethod(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethods.add(new ChosenMethod(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToThree(List<ChosenMethod> chosenMethods, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size) + 1;
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethods.add(new ChosenMethod(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethods.add(new ChosenMethod(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToTwo(List<ChosenMethod> chosenMethods, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size);
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethods.add(new ChosenMethod(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethods.add(new ChosenMethod(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToOne(List<ChosenMethod> chosenMethods, boolean fastVariation) {
        if (fastVariation) {
            chosenMethods.add(new ChosenMethod(Method.TRIGONOMETRICAPPROXIMATION, 1));
        } else {
            chosenMethods.add(new ChosenMethod(Method.POLYNOMIALAPPROXIMATION, 1));
        }
    }

    private int getDefaultDegree(int size) {
        return (int) (Math.ceil(Math.log(size)));
    }
}

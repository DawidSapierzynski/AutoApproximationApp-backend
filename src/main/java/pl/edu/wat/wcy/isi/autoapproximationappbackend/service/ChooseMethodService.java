package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;

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

    public List<ChosenMethodDTO> selectMethods(SeriesPropertiesDTO seriesPropertiesDTO) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();

        switch (seriesPropertiesDTO.getPrecision()) {
            case 1:
                selectToOne(chosenMethodDTOS, seriesPropertiesDTO.isFastVariation());
                break;
            case 2:
                selectToTwo(chosenMethodDTOS, seriesPropertiesDTO.isFastVariation(), seriesPropertiesDTO.getSize());
                break;
            case 3:
                selectToThree(chosenMethodDTOS, seriesPropertiesDTO.isFastVariation(), seriesPropertiesDTO.getSize());
                break;
            case 4:
                selectToFour(chosenMethodDTOS, seriesPropertiesDTO.isFastVariation(), seriesPropertiesDTO.getSize());
                break;
            case 5:
                selectToFive(chosenMethodDTOS, seriesPropertiesDTO.isFastVariation(), seriesPropertiesDTO.getSize());
                break;
        }

        return chosenMethodDTOS;
    }

    private void selectToFive(List<ChosenMethodDTO> chosenMethodDTOS, boolean isFastVariation, int size) {
        if (isFastVariation) {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICINTERPOLATION, TrigonometricInterpolation.chooseTrigonometricDegree(size)));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.NEWTONINTERPOLATION, size - 1));
        }
        chosenMethodDTOS.add(new ChosenMethodDTO(Method.SPLINEINTERPOLATION, 3));
    }

    private void selectToFour(List<ChosenMethodDTO> chosenMethodDTOS, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size) + 2;
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToThree(List<ChosenMethodDTO> chosenMethodDTOS, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size) + 1;
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToTwo(List<ChosenMethodDTO> chosenMethodDTOS, boolean isFastVariation, int size) {
        int degree = getDefaultDegree(size);
        if (isFastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, degree));
        }
    }

    private void selectToOne(List<ChosenMethodDTO> chosenMethodDTOS, boolean fastVariation) {
        if (fastVariation) {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, 1));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, 1));
        }
    }

    private int getDefaultDegree(int size) {
        return (int) (Math.ceil(Math.log(size)));
    }
}

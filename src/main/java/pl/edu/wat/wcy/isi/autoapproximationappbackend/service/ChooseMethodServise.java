package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ChosenMethod;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ChooseMethodServise {
    private Logger logger = LoggerFactory.getLogger(ChooseMethodServise.class);

    private ExecutorService threadPool;

    public ChooseMethodServise(@Value("${number.threads}") int nThreads) {
        this.threadPool = Executors.newFixedThreadPool(nThreads);
    }

    public List<ChosenMethod> selectMethods(SeriesProperties seriesProperties) {
        List<ChosenMethod> chosenMethods = new ArrayList<>();
        logger.info("Działam");

        switch (seriesProperties.getPrecision()) {
            case 1:
                chosenMethods.add(new ChosenMethod(Method.POLYNOMIALAPPROXIMATION, 1));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        return chosenMethods;
    }
}

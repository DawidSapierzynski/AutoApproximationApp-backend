package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.FileForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.propertiesCalculate.FastVariationCalculate;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.propertiesCalculate.ReadSeriesDatesFromFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.propertiesCalculate.VarianceCalculate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SeriesPropertiesServise {
    private Logger logger = LoggerFactory.getLogger(SeriesPropertiesServise.class);

    private ExecutorService threadPool;

    public SeriesPropertiesServise(@Value("${number.threads}") int nThreads) {
        this.threadPool = Executors.newFixedThreadPool(nThreads);
    }

    public void readFileForm(FileForm fileForm, SeriesProperties seriesProperties) {
        List<Callable<Object>> callables = Collections.singletonList(Executors.callable(new ReadSeriesDatesFromFile(fileForm.getSeriesDatesFile(), seriesProperties)));
        try {
            List<Future<Object>> futures = this.threadPool.invokeAll(callables);
            logger.debug("ReadSeriesDatesFromFile - isDone: {}", futures.get(0).isDone());

            seriesProperties.setPrecision(fileForm.getPrecision());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }

    public void propertiesCalculate(SeriesProperties seriesProperties) {
        List<Callable<Object>> callables = Arrays.asList(Executors.callable(new VarianceCalculate(seriesProperties)), Executors.callable(new FastVariationCalculate(seriesProperties)));
        try {
            List<Future<Object>> futures = this.threadPool.invokeAll(callables);
            logger.debug("VarianceCalculate - isDone: {}", futures.get(0).isDone());
            logger.debug("FastVariationCalculate - isDone: {}", futures.get(1).isDone());

        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }
}

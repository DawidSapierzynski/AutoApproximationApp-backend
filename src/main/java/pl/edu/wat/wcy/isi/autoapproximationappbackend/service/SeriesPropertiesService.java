package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.FileStorageProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate.FastVariationPolynomialCalculate;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate.FastVariationTrigonometricCalculate;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate.ReadSeriesDatesFromFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.calculate.VarianceCalculate;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.SeriesPropertiesRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SeriesPropertiesService {
    private Logger logger = LoggerFactory.getLogger(SeriesPropertiesService.class);

    private ExecutorService threadPool;
    private FileStorageProperties fileStorageProperties;
    private SeriesPropertiesRepository seriesPropertiesRepository;

    public SeriesPropertiesService(@Value("${number.threads}") int nThreads, FileStorageProperties fileStorageProperties, SeriesPropertiesRepository seriesPropertiesRepository) {
        this.threadPool = Executors.newFixedThreadPool(nThreads);
        this.fileStorageProperties = fileStorageProperties;
        this.seriesPropertiesRepository = seriesPropertiesRepository;
    }

    public void readFile(Long dateSeriesFileId, SeriesPropertiesEntity seriesProperties) {
        List<Callable<Object>> callables = Collections.singletonList(Executors.callable(new ReadSeriesDatesFromFile(dateSeriesFileId.toString() + ".csv", seriesProperties, fileStorageProperties)));
        try {
            List<Future<Object>> futures = this.threadPool.invokeAll(callables);
            logger.debug("ReadSeriesDatesFromFile - isDone: {}", futures.get(0).isDone());

        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }

    public void propertiesCalculate(SeriesPropertiesEntity seriesProperties) {
        List<Callable<Object>> callables = Arrays.asList(Executors.callable(new VarianceCalculate(seriesProperties)),
                Executors.callable(new FastVariationPolynomialCalculate(seriesProperties)),
                Executors.callable(new FastVariationTrigonometricCalculate(seriesProperties)));
        try {
            List<Future<Object>> futures = this.threadPool.invokeAll(callables);
            logger.debug("VarianceCalculate - isDone: {}", futures.get(0).isDone());
            logger.debug("FastVariationPolynomialCalculate - isDone: {}", futures.get(1).isDone());
            logger.debug("FastVariationTrigonometricCalculate - isDone: {}", futures.get(2).isDone());

            //TODO
            seriesProperties.setFastVariation((byte) 1);
            logger.info("Set FastVariation: {}", seriesProperties.getFastVariation());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }

    public SeriesPropertiesEntity save(SeriesPropertiesEntity seriesPropertiesEntity) {
        return seriesPropertiesRepository.save(seriesPropertiesEntity);
    }

    public List<SeriesPropertiesEntity> findByUserAndDeleted(UserEntity userEntity, byte deleted) {
        return seriesPropertiesRepository.findByUserAndDeleted(userEntity, deleted);
    }

    public List<SeriesPropertiesEntity> findAll() {
        return seriesPropertiesRepository.findAll();
    }

    public Optional<SeriesPropertiesEntity> findById(long id) {
        return seriesPropertiesRepository.findById(id);
    }

    public Optional<SeriesPropertiesEntity> findByIdAndDeleted(long id, byte deleted) {
        return seriesPropertiesRepository.findBySeriesPropertiesIdAndDeleted(id, deleted);
    }

    public SeriesPropertiesEntity delete(SeriesPropertiesEntity seriesProperties) {
        seriesProperties.setDeleted((byte) 1);
        return save(seriesProperties);
    }

    public void delete(Collection<SeriesPropertiesEntity> seriesPropertiesList) {
        seriesPropertiesList.forEach(this::delete);
    }
}

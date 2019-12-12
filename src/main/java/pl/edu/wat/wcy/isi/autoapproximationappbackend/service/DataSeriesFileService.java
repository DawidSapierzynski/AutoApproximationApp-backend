package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.DataSeriesFileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DataSeriesFileService {
    private DataSeriesFileRepository dataSeriesFileRepository;
    private SeriesPropertiesService seriesPropertiesService;

    public DataSeriesFileService(DataSeriesFileRepository dataSeriesFileRepository, SeriesPropertiesService seriesPropertiesService) {
        this.dataSeriesFileRepository = dataSeriesFileRepository;
        this.seriesPropertiesService = seriesPropertiesService;
    }

    public Optional<DataSeriesFileEntity> findById(Long id) {
        return dataSeriesFileRepository.findById(id);
    }

    public DataSeriesFileEntity save(DataSeriesFileEntity dataSeriesFileEntity) {
        return dataSeriesFileRepository.save(dataSeriesFileEntity);
    }

    public void delete(DataSeriesFileEntity dataSeriesFileEntity) {
        dataSeriesFileEntity.setDeleted((byte) 1);
        seriesPropertiesService.delete(dataSeriesFileEntity.getSeriesProperties());
        save(dataSeriesFileEntity);
    }

    public List<DataSeriesFileEntity> findAll() {
        return dataSeriesFileRepository.findAll();
    }

    public List<DataSeriesFileEntity> findByUserAndDeleted(UserEntity userEntity, byte deleted) {
        return dataSeriesFileRepository.findByUserAndDeleted(userEntity, deleted);
    }
}

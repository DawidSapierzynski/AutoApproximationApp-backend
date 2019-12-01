package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.DataSeriesFileRepository;

@Service
public class DataSeriesFileService {
    private DataSeriesFileRepository dataSeriesFileRepository;

    public DataSeriesFileService(DataSeriesFileRepository dataSeriesFileRepository) {
        this.dataSeriesFileRepository = dataSeriesFileRepository;
    }

    public DataSeriesFileEntity findById(Long id) {
        return dataSeriesFileRepository.findById(id).orElseThrow();
    }

    public void save(DataSeriesFileEntity dataSeriesFileEntity) {
        dataSeriesFileRepository.save(dataSeriesFileEntity);
    }

    public void delete(DataSeriesFileEntity dataSeriesFileEntity) {
        dataSeriesFileEntity.setDeleted((byte) 1);
        save(dataSeriesFileEntity);
    }
}
